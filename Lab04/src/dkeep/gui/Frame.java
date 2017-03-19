package dkeep.gui;

import dkeep.logic.DungeonLogic;
import dkeep.logic.DungeonMap;
import dkeep.logic.Club;
import dkeep.logic.CrazyOgre;
import dkeep.logic.Game;
import dkeep.logic.Logic;
import dkeep.logic.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;

public class Frame {
	private static JTextField txtNumberOfOgres;
	private static JButton btnUp, btnDown, btnLeft, btnRight;
	private static JTextArea textArea;
	private static JLabel lblYouCanStart;
	private static Game g;
	private static Map map;
	private static Logic logic;
	private static String InGameText = "You can play now.";
	private static String GameOverText = "Game Over Press the New Game button to play again";

	public static void updateGameState(){
		textArea.setText(g.print());
		if(g.isGameOver()){
			textArea.setText(g.print() + "\n\n\nEnd of Game!");
			lblYouCanStart.setText(GameOverText);
			btnUp.setEnabled(false);
			btnRight.setEnabled(false);
			btnLeft.setEnabled(false);
			btnDown.setEnabled(false);
		}
	}

	public static void main(String[] args){

		//FRAME
		JFrame frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(700,500);
		frame.getContentPane().setLayout(null);

		//NUMBER OF OGRES
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 0, 0);
		frame.getContentPane().add(label);

		JLabel lblNewLabel = new JLabel("Number of Ogres:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 142, 23);
		frame.getContentPane().add(lblNewLabel);

		txtNumberOfOgres = new JTextField();
		txtNumberOfOgres.setBounds(135, 13, 30, 23);
		frame.getContentPane().add(txtNumberOfOgres);
		txtNumberOfOgres.setColumns(10);

		//GUARD PERSONALITY
		JLabel lblGuardPersonality = new JLabel("Guard Personality:");
		lblGuardPersonality.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGuardPersonality.setBounds(10, 56, 142, 17);
		frame.getContentPane().add(lblGuardPersonality);

		String[] options = {"Rookie","Drunken","Suspicious"};
		JComboBox comboBox = new JComboBox(options);
		comboBox.setMaximumRowCount(3);
		comboBox.setSelectedIndex(0);
		comboBox.setToolTipText("");
		comboBox.setBounds(135, 56, 74, 20);
		frame.getContentPane().add(comboBox);

		//PANEL
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		textArea.setBounds(10, 103, 426, 317);
		frame.getContentPane().add(textArea);
		
		//FOOTER
		lblYouCanStart = new JLabel("You can start a new game");
		lblYouCanStart.setBounds(20, 431, 249, 14);
		frame.getContentPane().add(lblYouCanStart);
		frame.setVisible(true);

		//UP
		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.update('w');
				updateGameState();
			}
		});
		btnUp.setEnabled(false);
		btnUp.setBounds(526, 238, 89, 23);
		frame.getContentPane().add(btnUp);

		//LEFT
		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.update('a');
				updateGameState();
			}
		});
		btnLeft.setEnabled(false);
		btnLeft.setBounds(462, 272, 89, 23);
		frame.getContentPane().add(btnLeft);

		//RIGHT
		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.update('d');
				updateGameState();
			}
		});
		btnRight.setEnabled(false);
		btnRight.setBounds(585, 272, 89, 23);
		frame.getContentPane().add(btnRight);

		//DOWN
		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.update('s');
				updateGameState();
			}
		});
		btnDown.setEnabled(false);
		btnDown.setBounds(526, 306, 89, 23);
		frame.getContentPane().add(btnDown);

		//NEW GAME
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map = new DungeonMap();
				int[] heropos = map.getHeroPos();
				System.out.println(heropos[0]);
				System.out.println(heropos[1]);
				if(txtNumberOfOgres.getText().equals("")){
					lblYouCanStart.setText("Must Input Number of Ogres");
					return;
				}
				int OgreNumber = Integer.parseInt(txtNumberOfOgres.getText());
				if(OgreNumber < 1 || OgreNumber > 5 ){
					 lblYouCanStart.setText("Invalid Number of Ogres");
					return;
				}
				int[] numEnemy = {comboBox.getSelectedIndex()+1, OgreNumber};
				logic = new DungeonLogic(map, heropos, numEnemy[0]);
				
				//int[] statusEnemy = new int[] {};
				
				//int i = comboBox.getSelectedIndex();
				//statusEnemy[0] = 0; //number of enemies
				//statusEnemy[1] = i; //behaviour of enemies
				
				/*
				String numOgres = txtNumberOfOgres.getText();
				int num = Integer.parseInt(numOgres);
				statusEnemy[1] = num;
				
				//				for(int i = 0; i < num; i++){
				//					int[] ogrePos = map.getOgrePos();
				//					logic.addEnemy(new CrazyOgre('O', ogrePos[0], ogrePos[1], new Club('*','$',2,4)));
				//				}
				 */
				g = new Game(map, logic,numEnemy);
				textArea.setText(g.print());
				
				lblYouCanStart.setText(InGameText); 
				btnUp.setEnabled(true);
				btnRight.setEnabled(true);
				btnLeft.setEnabled(true);
				btnDown.setEnabled(true);
			}
		});
		btnNewGame.setBounds(526, 155, 89, 23);
		frame.getContentPane().add(btnNewGame);

		//EXIT
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(526, 385, 89, 23);
		frame.getContentPane().add(btnExit);
	}
}
