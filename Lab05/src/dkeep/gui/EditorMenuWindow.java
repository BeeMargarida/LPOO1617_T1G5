package dkeep.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import dkeep.logic.Map;

public class EditorMenuWindow {
	public static JFrame frame;
	public static JLabel lblHeight, lblWidth, inf;
	public static JTextField txtHeight, txtWidth;
	public static JButton  btnMake, btnPlay, btnLoad, btnSave, btnExit;
	
	
	public EditorMenuWindow(){
		frame = new JFrame("Editor");
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				GameGui.gframe.frame.setVisible(false);
				GameGui.bframe.frame.setVisible(false);
				GameGui.editor.frame.setVisible(false);
				GameGui.mainmenu.frame.setVisible(true);
			}
		});
		frame.setSize(new Dimension(500,500));
		frame.getContentPane().setLayout(null);
		addElements();
		frame.setVisible(false);
	}
	
	public void addElements(){
		addHeigthLabel();
		addHeightTxtField();
		addWidthLabel();
		addWidthTxtField();
		addMakeButton();
		addPlayButton();
		addLoadButton();
		addSaveButton();
		addExitBUtton();
		addInfoLabel();
	}
	
	private void addHeigthLabel(){
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHeight.setBounds(10, 14, 78, 17);
		frame.getContentPane().add(lblHeight);
	}
	
	private void addHeightTxtField(){
		txtHeight = new JTextField();
		txtHeight.setBounds(86, 11, 51, 20);
		frame.getContentPane().add(txtHeight);
		txtHeight.setColumns(10);
	}
	
	private void addWidthLabel(){
		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWidth.setBounds(10, 45, 78, 17);
		frame.getContentPane().add(lblWidth);
	}
	
	private void addWidthTxtField(){
		txtWidth = new JTextField();
		txtWidth.setColumns(10);
		txtWidth.setBounds(86, 44, 51, 20);
		frame.getContentPane().add(txtWidth);
	}
	
	private void addMakeButton(){
		btnMake = new JButton("Make");
		btnMake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSave.setEnabled(false);
				inf.setText("You can make a map");
				GameGui.gframe.frame.setVisible(false);
				GameGui.bframe.frame.getContentPane().removeAll();
				char [][] map = new char[Integer.parseInt(txtHeight.getText())][Integer.parseInt(txtWidth.getText())];
				//URGENTE ALTERAR
				int[] pos = new int[2];
				for(int i = 0; i < map.length; i++){
					for(int j = 0; j < map[i].length; j++){
						if(map[i][j] == 'k'){
							pos[0] = i;
							pos[1] = j;
						}
					}
				}
				GameGui.userMap = new Map(map, pos);
				GameGui.bframe.buildframe = new BuildFrame(1,GameGui.userMap.getMap(), true);
				GameGui.bframe.frame.getContentPane().add(GameGui.bframe.buildframe);
				GameGui.bframe.frame.pack();
				GameGui.bframe.frame.setVisible(true);
				GameGui.bframe.buildframe.setFocusable(true);
				GameGui.bframe.buildframe.requestFocusInWindow();  //to handle keyboard events	
			}
		});
		btnMake.setBounds(48, 121, 89, 23);
		frame.getContentPane().add(btnMake);
	}
	
	private void addPlayButton(){
		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GameGui.userMap == null){
					inf.setText("Invalid Map");
					return;
				}
				if(!GameGui.verifyMap())
					return;
				btnSave.setEnabled(true);
				inf.setText("Map is playable and savable");
				GameGui.gframe.frame.getContentPane().removeAll();
				GameGui.bframe.frame.setVisible(false);
				GameGui.gframe.gameframe = new GameFrame(Integer.parseInt(GameGui.mainmenu.txtNumberOfOgres.getText()), GameGui.userMap.getMap());
				//GameGui.gframe.frame.remove(buildframe);
				GameGui.gframe.frame.getContentPane().add(GameGui.gframe.gameframe);
				GameGui.gframe.frame.pack();
				GameGui.gframe.frame.setVisible(true);
				GameGui.gframe.gameframe.setFocusable(true);
				GameGui.gframe.gameframe.requestFocusInWindow();  //to handle keyboard events	

			}
		});
		btnPlay.setBounds(48, 165, 89, 23);
		frame.getContentPane().add(btnPlay);
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
				if(!GameGui.loadMap(file)){
					System.out.println("Invalid Save File");
					return;
				}
				
				GameGui.bframe.frame.getContentPane().removeAll();
				GameGui.bframe.buildframe = new BuildFrame(1,GameGui.userMap.getMap(),false);
				GameGui.bframe.frame.getContentPane().add(GameGui.bframe.buildframe);
				GameGui.bframe.frame.pack();
				GameGui.bframe.frame.setVisible(true);
				GameGui.bframe.buildframe.setFocusable(true);
				GameGui.bframe.buildframe.requestFocusInWindow();  //to handle keyboard events	
			}
		});
		btnLoad.setBounds(150, 165, 89, 23);
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
				
				GameGui.saveMap(file);
			}
		});
		btnSave.setBounds(150, 209, 89, 23);
		btnSave.setEnabled(false);
		frame.getContentPane().add(btnSave);
	}
	
	private void addExitBUtton(){
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameGui.gframe.frame.setVisible(false);
				GameGui.bframe.frame.setVisible(false);
				frame.setVisible(false);
				GameGui.mainmenu.frame.setVisible(true);
			}
		});
		btnExit.setBounds(48, 209, 89, 23);
		frame.getContentPane().add(btnExit);
	}
	
	private void addInfoLabel(){
		inf = new JLabel("You can make a map");
		inf.setBounds(20, 400, 249, 14);
		frame.getContentPane().add(inf);
	}
}
