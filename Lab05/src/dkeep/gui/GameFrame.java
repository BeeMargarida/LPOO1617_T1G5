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


public class GameFrame extends JPanel implements MouseListener, KeyListener {
	private BufferedImage btile, wall, hero, herok, rguard, dguard, sguard, ogre, weapon, lever, key, door, opendoor, background, gameover;
	private int x = 50, y = 0, tileheight = 30, tilewidth = 30;
	private boolean make = false;
	private Logic logic;
	private Map map;
	private Game game;
	private int level = 0;
	private int mapHeight, mapWidth;
	private char[][] m; 
	boolean found = false;

	//EDITOR
	private char[] tiles = {'X', 'I', 'k', 'O', 'H', ' '};
	private char selected = ' ';
	private int bx = 0, by = 0;

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

			mapHeight = m.length;
            mapWidth = m[0].length;

            int[][] aux = new int[mapHeight][mapWidth];
            for(int i= 0; i < mapHeight; i++){
                for(int j = 0; j < mapWidth; j++){
                    aux[i][j] = 0;
                }
            }

		boolean res = verifyPath(1,1,'k');
		System.out.println(res);
		 */
		mapHeight = m.length;
		mapWidth = m[0].length;

		try{
			btile = ImageIO.read(new File("imgs/Tile.png"));
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

	public GameFrame(int OgreNumber, int height, int width){
		make = true;
		m = new char[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				if(i == 0 || i == height-1)
					m[i][j] = 'X';
				else{
					if(j == 0 || j == width-1)
						m[i][j] = 'X';
					else
						m[i][j] = ' ';
				}

			}
		}
		mapHeight = height;
		mapWidth = width;
		selected = ' ';
		by = (height+1)*tileheight;
		level = 1;

		try{
			btile = ImageIO.read(new File("imgs/Tile.png"));
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
		addMouseListener(this);
		addKeyListener(this); 
	}

	private boolean verifyPath(int x, int y, char target,int[][] aux){
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
			verifyPath(x+1,y,target,aux);
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
			verifyPath(x-1,y,target,aux);
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
			verifyPath(x,y+1,target,aux);
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
			verifyPath(x,y-1,target,aux);
		}

		return false;
	}

	private void printTile(Graphics g, char tile, int dx, int dy){
		if(tile == 'X')
			g.drawImage(wall, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'H' || tile == 'A')
			g.drawImage(hero, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'H' || tile == 'K')
			g.drawImage(herok, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'G')
			g.drawImage(rguard, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'g' || tile == 'D')
			g.drawImage(dguard, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'U')
			g.drawImage(sguard, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'O')
			g.drawImage(ogre, dx, dy, tilewidth, tileheight, this);
		else if(tile == '*')
			g.drawImage(weapon, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'k'){
			if(level == 0)
				g.drawImage(lever, dx, dy, tilewidth, tileheight, this);
			else if(level == 1)
				g.drawImage(key, dx, dy, tilewidth, tileheight, this);
		}
		else if(tile == 'I')
			g.drawImage(door, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'S')
			g.drawImage(opendoor, dx, dy, tilewidth, tileheight, this);
		else if(make && tile == ' ')
			g.drawImage(btile, dx, dy, tilewidth, tileheight, this);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!make)
			g.drawImage(background, x, y, mapWidth*tilewidth, mapHeight*tileheight, this);
		for(int i = 0; i < m.length; i++){
			int dy = y + i*tileheight;
			for(int j = 0; j < m[i].length; j++){
				int dx = x + j*tilewidth;
				printTile(g, m[i][j], dx, dy);
			}
		}

		if(!make){
			if(game.isGameOver())
				g.drawImage(gameover, x+(mapWidth*tilewidth-200)/2, y+mapHeight*tileheight+30, 200, 80, this);
		}

		if(make){
			g.drawString("Tiles:", 0, by+20);

			for(int i = 0; i < tiles.length; i++){
				int dx = bx+50+i*2*tilewidth;

				printTile(g, tiles[i], dx, by);
			}

			g.drawString("Selected:", bx+50+tiles.length*2*tilewidth+20, by+20);
			printTile(g, selected, bx+50+tiles.length*2*tilewidth+100, by);
		}
	}

	public void mouseClicked(MouseEvent e) {

		//verificar se foi no map
		int dx = e.getX()-x;
		int dy = e.getY()-y;
		if(dx >= 0 && dx <= mapWidth*tilewidth && dy >= 0 && dy <= mapHeight*tileheight)
			m[dy/tileheight][dx/tilewidth] = selected;

		//verificar se foi no editor
		dx = e.getX()-bx-50;
		dy = e.getY()-by;
		if(dx >= 0 && dx <= tiles.length*2*tilewidth && dy >= 0 && dy <= tileheight){
			if(dx/tilewidth%2 == 0)
				selected = tiles[(dx/tilewidth)/2];
		}

		repaint();
	}

	public void keyPressed(KeyEvent e) {
		if(make)
			return;

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
		level = game.getLevel();
	} 

	public void keyReleased(KeyEvent e) {} 
	public void keyTyped(KeyEvent e) {} 
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {} 
	public void mouseExited(MouseEvent e) {} 
}
