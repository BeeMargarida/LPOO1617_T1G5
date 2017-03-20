package dkeep.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import dkeep.logic.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;


public class GameFrame extends JPanel implements KeyListener {
	private BufferedImage wall, hero, herok, rguard, dguard, sguard, ogre, weapon, lever, key, door, opendoor, background, gameover;
	private int x = 0, y = 0, tileheight = 30, tilewidth = 30;
	private boolean make = false;
	private Logic logic;
	private Map map;
	private Game game;
	private int mapHeight, mapWidth;
	private char[][] m; 
	
	public GameFrame(int[] EnemyOptions) {
		map = new DungeonMap();
		int[] heropos = map.getHeroPos();
		logic = new DungeonLogic(map, heropos, EnemyOptions[0]);
		game = new Game(map, logic, EnemyOptions);
		m = game.getBoard();
		/*
		m = new char[][] {{'X','X','X','X','X'},
						  {'X',' ',' ','G','X'},
						  {'I',' ',' ',' ','X'},
						  {'I','k',' ',' ','X'},
						  {'X','X','X','X','X'}};

		boolean res = verifyPath(1,1,'k');
		System.out.println(res);
		*/
		mapHeight = m.length;
		mapWidth = m[0].length;
		
		try{
			wall = ImageIO.read(new File("imgs/Wall.png"));
			hero = ImageIO.read(new File("imgs/Link.png"));
			herok = ImageIO.read(new File("imgs/LinkK.png"));
			rguard = ImageIO.read(new File("imgs/Guard.png"));
			dguard = ImageIO.read(new File("imgs/DGuard.png"));
			sguard = ImageIO.read(new File("imgs/SGuard.png"));
			ogre =  ImageIO.read(new File("imgs/Ogre.png"));
			weapon = ImageIO.read(new File("imgs/Weapon.png"));
			lever = ImageIO.read(new File("imgs/LeverO.png"));
			key = ImageIO.read(new File("imgs/Key.png"));
			door = ImageIO.read(new File("imgs/Door.png"));
			opendoor = ImageIO.read(new File("imgs/ODoor.png"));
			background = ImageIO.read(new File("imgs/BG.png"));
			gameover = ImageIO.read(new File("imgs/Game_Over.png"));
		}
		catch(IOException e){
			System.out.println("Error loading images");
			System.exit(1);
		}
		
		addKeyListener(this); 
	}
	
	private boolean verifyPath(int x, int y, char target){   //A FAZER
		System.out.println(x);
		System.out.println(y);
		int[][] aux = new int[mapHeight][mapWidth];
		for(int i= 0; i < mapHeight; i++){
			for(int j = 0; j < mapWidth; j++){
				aux[i][j] = 0;
			}
		}
		
		if(m[x][y] == target)
			return true;
		
		if(m[x+1][y] != 'X' && m[x+1][y] != 'I')
			verifyPath(x+1, y, target);
		if(m[x-1][y] != 'X' && m[x-1][y] != 'I')
			verifyPath(x-1, y, target);
		if(m[x][y+1] != 'X' && m[x][y+1] != 'I')
			verifyPath(x, y+1, target);
		if(m[x][y-1] != 'X' && m[x][y-1] != 'I')
			verifyPath(x, y-1, target);
		
		return false;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, x, y, mapWidth*tilewidth, mapHeight*tileheight, this);
		for(int i = 0; i < m.length; i++){
			int dy = y + i*tileheight;
			for(int j = 0; j < m[i].length; j++){
				int dx = x + j*tilewidth;
				if(m[i][j] == 'X')
					g.drawImage(wall, dx, dy, tilewidth, tileheight, this);
				else if(m[i][j] == 'H' || m[i][j] == 'A')
					g.drawImage(hero, dx, dy, tilewidth, tileheight, this);
				else if(m[i][j] == 'H' || m[i][j] == 'K')
					g.drawImage(herok, dx, dy, tilewidth, tileheight, this);
				else if(m[i][j] == 'G')
					g.drawImage(rguard, dx, dy, tilewidth, tileheight, this);
				else if(m[i][j] == 'g' || m[i][j] == 'D')
					g.drawImage(dguard, dx, dy, tilewidth, tileheight, this);
				else if(m[i][j] == 'U')
					g.drawImage(sguard, dx, dy, tilewidth, tileheight, this);
				else if(m[i][j] == 'O')
					g.drawImage(ogre, dx, dy, tilewidth, tileheight, this);
				else if(m[i][j] == '*')
					g.drawImage(weapon, dx, dy, tilewidth, tileheight, this);
				else if(m[i][j] == 'k'){
					if(game.getLevel() == 0)
						g.drawImage(lever, dx, dy, tilewidth, tileheight, this);
					else if(game.getLevel() == 1)
						g.drawImage(key, dx, dy, tilewidth, tileheight, this);
				}
				else if(m[i][j] == 'I')
					g.drawImage(door, dx, dy, tilewidth, tileheight, this);
				else if(m[i][j] == 'S')
					g.drawImage(opendoor, dx, dy, tilewidth, tileheight, this);
			}
		}
		
		if(game.isGameOver())
			g.drawImage(gameover, x+(mapWidth*tilewidth-200)/2, y+mapHeight*tileheight+30, 200, 80, this);
	}
	
	
	public void keyPressed(KeyEvent e) {
		if(game.isGameOver())
			return;
		
		switch(e.getKeyCode()){ 
		case KeyEvent.VK_LEFT: game.update('a'); repaint(); break; 
		case KeyEvent.VK_RIGHT: game.update('d'); repaint(); break;  
		case KeyEvent.VK_UP: game.update('w'); repaint(); break; 
		case KeyEvent.VK_DOWN: game.update('s'); repaint(); break;
		}
		m = game.getBoard();
		mapHeight = m.length;
		mapWidth = m[0].length;
	} 
	
	public void keyReleased(KeyEvent e) {} 
	public void keyTyped(KeyEvent e) {} 
}
