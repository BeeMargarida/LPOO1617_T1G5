package dkeep.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import dkeep.logic.DungeonLogic;
import dkeep.logic.DungeonMap;
import dkeep.logic.Game;
import dkeep.logic.Logic;
import dkeep.logic.Map;

public class MenuWindow {
	public static JFrame frame;
	public static JTextField txtNumberOfOgres;
	public static JComboBox comboBox;
	public static JButton  btnNewGame, btnCreateMap, btnLoad, btnSave, btnExit;
	public static JLabel label, lblNewLabel, lblGuardPersonality;
	
	public MenuWindow(){
		frame = new JFrame("Main Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,250);
		frame.getContentPane().setLayout(null);
		addElements();
		frame.setVisible(true);
	}
	
	private void addElements(){
		addNumberOgresLabel();
		addNumberOgresTxtBox();
		addGuardPersonalityLabel();
		addGuardPersonalityComboBox();
		addNewGameButton();
		addCreateMapButton();
		addLoadButton();
		addSaveButton();
		addExitButton();
	}
	
	private void addNumberOgresLabel(){
		lblNewLabel = new JLabel("Number of Ogres:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 142, 23);
		frame.getContentPane().add(lblNewLabel);
	}
	
	private void addNumberOgresTxtBox(){
		txtNumberOfOgres = new JTextField();
		txtNumberOfOgres.setBounds(135, 13, 30, 23);
		frame.getContentPane().add(txtNumberOfOgres);
		txtNumberOfOgres.setColumns(10);
	}
	
	private void addGuardPersonalityLabel(){
		lblGuardPersonality = new JLabel("Guard Personality:");
		lblGuardPersonality.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGuardPersonality.setBounds(10, 56, 142, 17);
		frame.getContentPane().add(lblGuardPersonality);
	}
	
	private void addGuardPersonalityComboBox(){
		String[] options = {"Rookie","Drunken","Suspicious"};
		comboBox = new JComboBox(options);
		comboBox.setMaximumRowCount(3);
		comboBox.setSelectedIndex(0);
		comboBox.setToolTipText("");
		comboBox.setBounds(135, 56, 74, 20);
		frame.getContentPane().add(comboBox);
	}
	
	private void addNewGameButton(){
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Verify options
				GameGui.gframe.frame.getContentPane().removeAll();
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
				Map map = new DungeonMap();
				int[] heropos = map.getHeroPos();
				Logic logic = new DungeonLogic(map, heropos, numEnemy[0]);
				GameGui.game = new Game(map,logic,numEnemy);
				
				JPanel gameframe = new GameFrame(GameGui.game);
				GameGui.gframe.frame.getContentPane().add(gameframe);
				GameGui.gframe.frame.pack(); 
				GameGui.gframe.frame.setVisible(true);
				gameframe.setFocusable(true);
				gameframe.requestFocusInWindow();  //to handle keyboard events
			}
		});
		btnNewGame.setBounds(50, 100, 89, 23);
		frame.getContentPane().add(btnNewGame);
	}
	
	private void addCreateMapButton(){
		btnCreateMap = new JButton("Create Map");
		btnCreateMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				GameGui.gframe.frame.setVisible(false);
				GameGui.editor.frame.setVisible(true);
			}
		});
		btnCreateMap.setBounds(50, 134, 89, 23);
		frame.getContentPane().add(btnCreateMap);
	}
	
	private void addLoadButton(){
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frametmp = new JFrame("");
				frametmp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frametmp.setPreferredSize(new Dimension(500,500));
				frametmp.pack();
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("SER files", "ser");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(frametmp);
				File file = fc.getSelectedFile();
				
				if(file == null)
					return;
				
				if(!file.exists()){
					try{
						file.createNewFile();
					}
					
					catch(IOException ex){
						System.out.println("Can´t create file");
						return;
					}
				}
				if(!GameGui.loadGame(file)){
					System.out.println("Invalid Save File");
					return;
				}
				GameGui.gframe.frame.getContentPane().removeAll();
				GameGui.bframe.frame.setVisible(false);
				GameGui.gframe.gameframe = new GameFrame(GameGui.game);
				GameGui.gframe.frame.getContentPane().add(GameGui.gframe.gameframe);
				GameGui.gframe.frame.pack();
				GameGui.gframe.frame.setVisible(true);
				GameGui.gframe.gameframe.setFocusable(true);
				GameGui.gframe.gameframe.requestFocusInWindow();  //to handle keyboard events	
			}
		});
		btnLoad.setBounds(150, 134, 89, 23);
		frame.getContentPane().add(btnLoad);
	}
	
	private void addSaveButton(){
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frametmp = new JFrame("");
				frametmp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frametmp.setPreferredSize(new Dimension(500,500));
				frametmp.pack();
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("SER files", "ser");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showSaveDialog(frametmp);
				File file = fc.getSelectedFile();
				
				if(file == null)
					return;

				if(!file.exists()){
					try{
						file.createNewFile();
					}
					
					catch(IOException ex){
						System.out.println("Can´t create file");
						return;
					}
				}
				
				GameGui.saveGame(file);
			}
		});
		btnSave.setBounds(150, 170, 89, 23);
		frame.getContentPane().add(btnSave);
	}
	
	private void addExitButton(){
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(50, 170, 89, 23);
		frame.getContentPane().add(btnExit);
	}
}
