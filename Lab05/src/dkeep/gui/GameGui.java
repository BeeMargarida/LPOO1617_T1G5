package dkeep.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dkeep.logic.*;

import javax.imageio.ImageIO;

public class GameGui {
	public static BufferedImage btile, wall, hero, herok, rguard, dguard, sdguard, sguard, ogre, sogre, weapon, lever, key, overkey, door, opendoor, background, gameover;
	public static MenuWindow mainmenu;
	public static EditorMenuWindow editor;
	public static GameWindow gframe;
	public static EditorWindow bframe;
	public static Map userMap;
	public static Game game;
	private static boolean found = false;

	public GameGui(){
		loadImages();
		mainmenu = new MenuWindow();
		editor = new EditorMenuWindow();
		gframe = new GameWindow();
		bframe = new EditorWindow();
	}

	private void loadImages(){
		try{
			btile = ImageIO.read(new File("imgs/Tile.png"));
			wall = ImageIO.read(new File("imgs/Wall.png"));
			hero = ImageIO.read(new File("imgs/Link.png"));
			herok = ImageIO.read(new File("imgs/LinkK.png"));
			rguard = ImageIO.read(new File("imgs/Guard.png"));
			dguard = ImageIO.read(new File("imgs/DGuard.png"));
			sdguard = ImageIO.read(new File("imgs/SDGuard.png"));
			sguard = ImageIO.read(new File("imgs/SGuard.png"));
			ogre =  ImageIO.read(new File("imgs/Ogre.png"));
			sogre =  ImageIO.read(new File("imgs/SOgre.png"));
			weapon = ImageIO.read(new File("imgs/Weapon.png"));
			lever = ImageIO.read(new File("imgs/LeverO.png"));
			key = ImageIO.read(new File("imgs/Key.png"));
			overkey = ImageIO.read(new File("imgs/OverKey.png"));
			door = ImageIO.read(new File("imgs/Door.png"));
			opendoor = ImageIO.read(new File("imgs/ODoor.png"));
			background = ImageIO.read(new File("imgs/BG.png"));
			gameover = ImageIO.read(new File("imgs/Game_Over.png"));
		}
		catch(IOException e){
			System.out.println("Error loading images");
			System.exit(1);
		}
	}

	public static boolean saveGame(File f){
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

	public static boolean loadGame(File f){

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

	public static boolean saveMap(File f){
		try{
			FileOutputStream fout = new FileOutputStream(f.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeChar('M');
			out.writeObject(userMap);
			out.writeObject(mainmenu.txtNumberOfOgres.getText());
			out.close();
		}
		catch(IOException ex){

		}

		return true;
	}

	public static boolean loadMap(File f){

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
			mainmenu.txtNumberOfOgres.setText(number);
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

	public static boolean verifyBorders(){
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
	
	private static boolean verifyDoor(){
		for(int i = 0; i < userMap.getMap().length; i++){
			for(int j = 0; j < userMap.getMap()[i].length;j++){
				if(userMap.getMap()[i][j] == 'I')
					return true;
			}
		}
		return false;
	}
	
	private static boolean verifyHero(){
		boolean hero = false;
		for(int i = 0; i < userMap.getMap().length; i++){
			for(int j = 0; j < userMap.getMap()[i].length;j++){
				if(userMap.getMap()[i][j] == 'H'){
					if(hero){
						editor.inf.setText("Too many heros, must be only one"); return false;}
					else hero = true;
				}
			}
		}
		return hero;
	}
	
	private static boolean verifyOgre(){
		boolean ogre = false;
		for(int i = 0; i < userMap.getMap().length; i++){
			for(int j = 0; j < userMap.getMap()[i].length;j++){
				if(userMap.getMap()[i][j] == 'O'){
					if(ogre){
						editor.inf.setText("Too many ogres, must be only one"); return false;}
					else ogre = true;
				}
			}
		}
		return ogre;
	}
	
	private static boolean verifyKey(){
		boolean key = false;
		for(int i = 0; i < userMap.getMap().length; i++){
			for(int j = 0; j < userMap.getMap()[i].length;j++){
				if(userMap.getMap()[i][j] == 'k'){
					if(key){
						editor.inf.setText("Too many keys, must be only one"); return false;}
					else key = true;
				}
			}
		}
		return key;
	}
	
	private static boolean verifyAllElements(){
		editor.inf.setText("");
		boolean door, hero,  ogre, key;
		door = verifyDoor(); hero = verifyHero(); ogre = verifyOgre(); key = verifyKey();
		if(editor.inf.getText().contains("Too")) return false;
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
			editor.inf.setText(elements);
			return false;
		}
	}
	
	private static boolean verifyPath(int[] coord, char target, int[][] aux, char[][] m){
		int x = coord[0], y = coord[1];
		if(found)
			return true;
		if(aux[x+1][y] != 1 && m[x+1][y] != 'X'){
			if(m[x+1][y] == target){ found = true; return true; }
			else if(m[x+1][y] != 'I'){
				aux[x+1][y] = 1;
				verifyPath(new int[] {x+1,y},target,aux,m);
			}
		}
		if(found)
			return true;
		if(aux[x-1][y] != 1 && m[x-1][y] != 'X'){
			if(m[x-1][y] == target){ found = true; return true; }
			else if(m[x-1][y] != 'I'){
				aux[x-1][y] = 1;
				verifyPath(new int[] {x-1,y},target,aux,m);
			}
		}
		if(found)
			return true;
		if(aux[x][y+1] != 1 && m[x][y+1] != 'X'){
			if(m[x][y+1] == target){ found = true; return true;	}
			else if(m[x][y+1] != 'I'){
				aux[x][y+1] = 1;
				verifyPath(new int[] {x,y+1},target,aux,m);
			}
		}
		if(found)
			return true;
		if(aux[x][y-1] != 1 && m[x][y-1] != 'X'){
			if(m[x][y-1] == target){ found = true; return true; }
			else if(m[x][y-1] != 'I'){
				aux[x][y-1] = 1;
				verifyPath(new int[] {x,y-1},target,aux,m);
			}
		}
		return false;
	}
	
	public static boolean verifyMap(){
		if(!verifyBorders()){
			editor.inf.setText("Incorrect borders");
			return false;
		}
		if(!verifyAllElements())
			return false;

		int[] pos = userMap.getHeroPos();
		if(!verifyPath(pos,'k',new int[userMap.getMap().length][userMap.getMap()[0].length],userMap.getMap())){
			editor.inf.setText("No path to reach key");
			return false;
		}
		found = false;
		if(!verifyPath(pos,'I',new int[userMap.getMap().length][userMap.getMap()[0].length],userMap.getMap())){
			editor.inf.setText("No path to reach door");
			return false;
		}
		found = false;
		return true;
	}

}
