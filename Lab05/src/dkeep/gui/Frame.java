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
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.*;
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
	private static JLabel inf;
	private static JButton btnSave2;
	private static Game game;
	private static Map userMap;
	static boolean found = false;  //used for verifypath


	private static boolean loadMap(File f){
		
		try{
			FileInputStream fout = new FileInputStream(f.getAbsolutePath());
			ObjectInputStream out = new ObjectInputStream(fout);
			char type = (char) out.readChar();
			if(type == 'G'){
				out.close();
				return false;
			}
			userMap = (Map) out.readObject();
			String number = (String) out.readObject();
			out.close();
			txtNumberOfOgres.setText(number);
		}
		catch(IOException ex){
			System.out.println("Error loading map");
			return false;
		}
		
		catch(ClassNotFoundException c){
			System.out.println("Error loading map");
			return false;
		}
		
		return true;
		
	}
	
	private static boolean saveMap(File f){
		try{
			FileOutputStream fout = new FileOutputStream(f.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeChar('M');
			out.writeObject(userMap);
			out.writeObject(txtNumberOfOgres.getText());
			out.close();
		}
		catch(IOException ex){
			
		}
		
		return true;
	}
	
	
	private static boolean verifyBorders(){
		for(int i = 0; i < userMap.getMap().length; i++){
			for(int j = 0; j < userMap.getMap()[i].length;j++){
				if(i == 0 || i == userMap.getMap().length-1){
					if(userMap.getMap()[i][j] != 'I' && userMap.getMap()[i][j] != 'X')
						return false;
				}
				else if(j == 0 || j == userMap.getMap()[i].length-1){
					if(userMap.getMap()[i][j] != 'I' && userMap.getMap()[i][j] != 'X')
						return false;
				}
			}
		}
		return true;
	}


	private static boolean verifyAllElements(){
		boolean door = false , hero = false, ogre = false, key=false;
		for(int i = 0; i < userMap.getMap().length; i++){
			for(int j = 0; j < userMap.getMap()[i].length;j++){
				if(userMap.getMap()[i][j] == 'I')
					door = true;
				if(userMap.getMap()[i][j] == 'H'){
					if(hero){
						inf.setText("Too many heros, must be only one");
						return false;
					}
					else
						hero = true;
				}
				if(userMap.getMap()[i][j] == 'O'){
					if(ogre){
						inf.setText("Too many ogres, must be only one");
						return false;
					}
					else
						ogre = true;
				}
				if(userMap.getMap()[i][j] == 'k'){
					if(key){
						inf.setText("Too many keys, must be only one");
						return false;
					}
					else
						key = true;
				}
			}
		}
		if(door && hero && ogre && key)
			return true;
		else{
			String elements = new String("Missing element(s)");
			if(!door)
				elements += " door";
			if(!hero)
				elements += " hero";
			if(!ogre)
				elements += " ogre";
			if(!key)
				elements += " key";
			inf.setText(elements);
			return false;
		}
	}
	
	private static boolean verifyPath(int x, int y, char target,int[][] aux, char[][] m){
		/*
		System.out.println(x);
		System.out.println(y);
		 */

		if(found)
			return true;

		if(aux[x+1][y] != 1 && m[x+1][y] != 'X' && m[x+1][y] != 'I'){
			//System.out.println("a");
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
			//System.out.println("b");
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
			//System.out.println("c");
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
			//System.out.println("d");
			if(m[x][y-1] == target){
				found = true;
				return true;
			}
			aux[x][y-1] = 1;
			verifyPath(x,y-1,target,aux,m);
		}

		return false;
	}



	private static boolean verifyMap(){
		if(!verifyBorders()){
			inf.setText("Incorrect borders");
			return false;
		}

		System.out.println("1");

		if(!verifyAllElements())
			return false;
		System.out.println("2");

		int[] pos = userMap.getHeroPos();

		if(!verifyPath(pos[0],pos[1],'k',new int[userMap.getMap().length][userMap.getMap()[0].length],userMap.getMap())){
			inf.setText("No path to reach key");
			return false;
		}

		found = false;

		System.out.println("3");

		/*//Verifica a parede  
		if(!verifyPath(pos[0],pos[1],'I',new int[userMap.length][userMap[0].length],userMap))
			return false;

		found = false;

		System.out.println("4");
		 */

		return true;
	}
	
	private static boolean loadGame(File f){
		
		try{
			FileInputStream fout = new FileInputStream(f.getAbsolutePath());
			ObjectInputStream out = new ObjectInputStream(fout);
			char type = (char) out.readChar();
			if(type == 'M'){
				out.close();
				return false;
			}
			game = (Game) out.readObject();
			out.close();
		}
		catch(IOException ex){
			System.out.println("Error loading game");
			return false;
		}
		
		catch(ClassNotFoundException c){
			System.out.println("Error loading game");
			return false;
		}
		
		return true;
	}
	
	private static boolean saveGame(File f){
		try{
			FileOutputStream fout = new FileOutputStream(f.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeChar('G');
			out.writeObject(game);
			out.close();
		}
		catch(IOException ex){
			System.out.println("No save");
		}
		
		return true;
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
				Map map = new DungeonMap();
				int[] heropos = map.getHeroPos();
				Logic logic = new DungeonLogic(map, heropos, numEnemy[0]);
				game = new Game(map,logic,numEnemy);
				
				JPanel gameframe = new GameFrame(game);
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
				gframe.setVisible(false);
				mframe.setVisible(true);
			}
		});
		btnCreateMap.setBounds(50, 134, 89, 23);
		frame.getContentPane().add(btnCreateMap);
		
		
		JButton btnSave = new JButton("Save");
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
				
				saveGame(file);
			}
		});
		btnSave.setBounds(150, 170, 89, 23);
		frame.getContentPane().add(btnSave);
		
		
		//LOAD
		JButton btnLoad = new JButton("Load");
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
				if(!loadGame(file)){
					System.out.println("Invalid Save File");
					return;
				}
				gframe.getContentPane().removeAll();
				bframe.setVisible(false);
				gameframe = new GameFrame(game);
				gframe.getContentPane().add(gameframe);
				gframe.pack();
				gframe.setVisible(true);
				gameframe.setFocusable(true);
				gameframe.requestFocusInWindow();  //to handle keyboard events	
			}
		});
		btnLoad.setBounds(150, 134, 89, 23);
		frame.getContentPane().add(btnLoad);

		frame.setVisible(true);

		//GAMEFRAME
		gframe = new JFrame("Game");
		//gframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		gframe.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				btnSave2.setEnabled(false);
				gframe.setVisible(false);
				bframe.setVisible(true);
			}
		});
		gframe.setPreferredSize(new Dimension(500, 500)); 

		//BUILDFRAME
		bframe = new JFrame("Build");
		bframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		bframe.setPreferredSize(new Dimension(500,500));

		//EDITOR FRAME
		mframe = new JFrame("Editor");
		mframe.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				gframe.setVisible(false);
				bframe.setVisible(false);
				mframe.setVisible(false);
				frame.setVisible(true);
			}
		});
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
				btnSave2.setEnabled(false);
				inf.setText("You can make a map");
				gframe.setVisible(false);
				bframe.getContentPane().removeAll();
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
				userMap = new Map(map, pos);
				buildframe = new BuildFrame(1,userMap.getMap(), true);
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
				if(userMap == null){
					inf.setText("Invalid Map");
					return;
				}
				if(!verifyMap())
					return;
				btnSave2.setEnabled(true);
				inf.setText("Map is playable and savable");
				gframe.getContentPane().removeAll();
				bframe.setVisible(false);
				gameframe = new GameFrame(Integer.parseInt(txtNumberOfOgres.getText()), userMap.getMap());
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
				gframe.setVisible(false);
				bframe.setVisible(false);
				mframe.setVisible(false);
				frame.setVisible(true);
			}
		});
		btnExit2.setBounds(48, 209, 89, 23);
		mframe.getContentPane().add(btnExit2);

		btnSave2 = new JButton("Save");
		btnSave2.addActionListener(new ActionListener() {
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
				
				saveMap(file);
			}
		});
		btnSave2.setBounds(150, 209, 89, 23);
		btnSave2.setEnabled(false);
		mframe.getContentPane().add(btnSave2);
		
		
		//LOAD
		JButton btnLoad2 = new JButton("Load");
		btnLoad2.addActionListener(new ActionListener() {
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
				if(!loadMap(file)){
					System.out.println("Invalid Save File");
					return;
				}
				
				bframe.getContentPane().removeAll();
				buildframe = new BuildFrame(1,userMap.getMap(),false);
				bframe.getContentPane().add(buildframe);
				bframe.pack();
				bframe.setVisible(true);
				buildframe.setFocusable(true);
				buildframe.requestFocusInWindow();  //to handle keyboard events	
			}
		});
		btnLoad2.setBounds(150, 165, 89, 23);
		mframe.getContentPane().add(btnLoad2);

		
		
		//FOOTER
		inf = new JLabel("You can make a map");
		inf.setBounds(20, 400, 249, 14);
		mframe.getContentPane().add(inf);
	}
}
