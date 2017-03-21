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
import java.awt.Dimension;

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
import javax.swing.JPanel;

public class Frame {
	
	private static JTextField txtNumberOfOgres;
	private static JFrame gframe;
	/*
	private static JButton btnUp, btnDown, btnLeft, btnRight;
	//private static JTextArea textArea;
	private static JLabel lblYouCanStart;
	private static Game g;
	private static Map map;
	private static Logic logic;
	private static String InGameText = "You can play now.";
	private static String GameOverText = "Game Over Press the New Game button to play again";
	*/

	public static void main(String[] args){
		
		//MENUFRAME
		JFrame frame = new JFrame("Main Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,250);
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
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Verify options
				if(txtNumberOfOgres.getText().equals("")){
					//lblYouCanStart.setText("Must Input Number of Ogres");
					return;
				}
				int OgreNumber = Integer.parseInt(txtNumberOfOgres.getText());
				if(OgreNumber < 1 || OgreNumber > 5 ){
					//lblYouCanStart.setText("Invalid Number of Ogres");
					return;
				}
				int[] numEnemy = {comboBox.getSelectedIndex()+1, OgreNumber};
				
				//JPanel gameframe = new GameFrame(numEnemy);
				JPanel gameframe = new GameFrame(1,10,10);
				gframe.getContentPane().add(gameframe);
				gframe.pack(); 
				gframe.setVisible(true);
				gameframe.setFocusable(true);
				gameframe.requestFocusInWindow();  //to handle keyboard events
			}
		});
		btnNewGame.setBounds(50, 100, 89, 23);
		frame.getContentPane().add(btnNewGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(50, 170, 89, 23);
		frame.getContentPane().add(btnExit);
		
		frame.setVisible(true);

		//GAMEFRAME
		gframe = new JFrame("Game");
		gframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gframe.setPreferredSize(new Dimension(500, 500)); 
	}
}
