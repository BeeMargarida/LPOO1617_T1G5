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
	private static JTextField txtHeight;
	private static JTextField txtWidth;
	private static JFrame frame;
	private static JFrame gframe;
	private static JFrame bframe;
	private static JFrame mframe;
	private static JPanel buildframe, gameframe;
	private static char[][] userMap;
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

	boolean found = false;

	private boolean verifyPath(int x, int y, char target,int[][] aux, char[][] m){
		System.out.println(x);
		System.out.println(y);

		if(found)
			return true;

		if(aux[x+1][y] != 1 && m[x+1][y] != 'X' && m[x+1][y] != 'I'){
			System.out.println("a");
			if(m[x+1][y] == target){
				found = true;
				return true;
			}
			aux[x+1][y] = 1;
			verifyPath(x+1,y,target,aux,m);
		}

		if(found)
			return true;

		if(aux[x-1][y] != 1 && m[x-1][y] != 'X' && m[x-1][y] != 'I'){
			System.out.println("b");
			if(m[x-1][y] == target){
				found = true;
				return true;
			}
			aux[x-1][y] = 1;
			verifyPath(x-1,y,target,aux,m);
		}

		if(found)
			return true;

		if(aux[x][y+1] != 1 && m[x][y+1] != 'X' && m[x][y+1] != 'I'){
			System.out.println("c");
			if(m[x][y+1] == target){
				found = true;
				return true;
			}
			aux[x][y+1] = 1;
			verifyPath(x,y+1,target,aux,m);
		}

		if(found)
			return true;

		if(aux[x][y-1] != 1 && m[x][y-1] != 'X' && m[x][y-1] != 'I'){
			System.out.println("d");
			if(m[x][y-1] == target){
				found = true;
				return true;
			}
			aux[x][y-1] = 1;
			verifyPath(x,y-1,target,aux,m);
		}

		return false;
	}

	public static void main(String[] args){

		//MENUFRAME
		frame = new JFrame("Main Menu");
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
				gframe.getContentPane().removeAll();
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
				
				JPanel gameframe = new GameFrame(numEnemy);
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

		JButton btnCreateMap = new JButton("Create Map");
		btnCreateMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				mframe.setVisible(true);
			}
		});
		btnCreateMap.setBounds(50, 134, 89, 23);
		frame.getContentPane().add(btnCreateMap);

		frame.setVisible(true);

		//GAMEFRAME
		gframe = new JFrame("Game");
		gframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		gframe.setPreferredSize(new Dimension(500, 500)); 

		//BUILDFRAME
		bframe = new JFrame("Build");
		bframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		bframe.setPreferredSize(new Dimension(500,500));

		//EDITOR FRAME
		mframe = new JFrame("Editor");
		mframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mframe.setSize(new Dimension(500,500));
		mframe.getContentPane().setLayout(null);
		mframe.setVisible(false);

		txtHeight = new JTextField();
		txtHeight.setBounds(86, 11, 51, 20);
		mframe.getContentPane().add(txtHeight);
		txtHeight.setColumns(10);

		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHeight.setBounds(10, 14, 78, 17);
		mframe.getContentPane().add(lblHeight);

		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWidth.setBounds(10, 45, 78, 17);
		mframe.getContentPane().add(lblWidth);

		txtWidth = new JTextField();
		txtWidth.setColumns(10);
		txtWidth.setBounds(86, 44, 51, 20);
		mframe.getContentPane().add(txtWidth);

		JButton btnMake = new JButton("Make");
		btnMake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//if(gframe != null)
				gframe.setVisible(false);
				bframe.setVisible(true);
				//bframe.dispose();
				/*
				if(buildframe != null)
					bframe.getContentPane().remove(buildframe);
					*/
				bframe.getContentPane().removeAll();
				char[][] tmp = new char[Integer.parseInt(txtHeight.getText())][Integer.parseInt(txtWidth.getText())];
				userMap = tmp;
				buildframe = new BuildFrame(1,userMap);
				bframe.getContentPane().add(buildframe);
				bframe.pack();
				bframe.setVisible(true);
				buildframe.setFocusable(true);
				buildframe.requestFocusInWindow();  //to handle keyboard events	
			}
		});
		btnMake.setBounds(48, 121, 89, 23);
		mframe.getContentPane().add(btnMake);

		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userMap == null)
					return;
				//gframe.getContentPane().removeAll();
				for(int i = 0; i < userMap.length ;i++)
					System.out.println(userMap[i]);
				bframe.setVisible(false);
				gameframe = new GameFrame(Integer.parseInt(txtNumberOfOgres.getText()), userMap);
				gframe.remove(buildframe);
				gframe.getContentPane().add(gameframe);
				gframe.pack();
				gframe.setVisible(true);
				gameframe.setFocusable(true);
				gameframe.requestFocusInWindow();  //to handle keyboard events	

			}
		});
		btnPlay.setBounds(48, 165, 89, 23);
		mframe.getContentPane().add(btnPlay);

		JButton btnExit2 = new JButton("Exit");
		btnExit2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if(gframe != null)
				gframe.dispose();
				//if(bframe != null)
				bframe.dispose();
				mframe.setVisible(false);
				frame.setVisible(true);
			}
		});
		btnExit2.setBounds(48, 209, 89, 23);
		mframe.getContentPane().add(btnExit2);
	}
}
