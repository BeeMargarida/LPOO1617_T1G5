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
	private int x = 50, y = 0, tileheight = 30, tilewidth = 30;
	private Logic logic;
	private Map map;
	private Game game;
	public int level;
	public int mapHeight, mapWidth;
	public char[][] m; 

	public GameFrame(Game game, boolean custom) {
		if(custom){
			makeCustomMapGame();
			this.game = GameGui.game;
		}
		else
			this.game = game;
		level = this.game.getLevel();
		m = this.game.getBoard();
		
		mapHeight = m.length;
		mapWidth = m[0].length;
		
		addKeyListener(this); 
	}
	
	
	private void makeCustomMapGame(){
		int[] pos = new int[2];
		for(int i = 0; i < GameGui.userMap.getHeight(); i++){
			for(int j = 0; j < GameGui.userMap.getWidth(); j++){
				if(GameGui.userMap.getMap()[i][j] == 'k'){
					pos[0] = i;
					pos[1] = j;
				}
			}
		}
		char[][] tmp = new char[GameGui.userMap.getHeight()][GameGui.userMap.getWidth()];
		for(int i = 0; i < GameGui.userMap.getHeight(); i++){
			tmp[i] = GameGui.userMap.getMap()[i].clone();
		}
		map = new Map(tmp, pos);
		int[] heropos = map.getHeroPos();
		int[] enemyOptions = new int[] {0,Integer.parseInt(GameGui.mainmenu.txtNumberOfOgres.getText())};
		logic = new KeepLogic(map, heropos, true, enemyOptions[1]);
		GameGui.game = new Game(map, logic, enemyOptions);
		GameGui.game.setLevel(1);
	}

	private void printTile(Graphics g, char tile, int dx, int dy){
		if(tile == 'X')
			g.drawImage(GameGui.wall, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'H' || tile == 'A')
			g.drawImage(GameGui.hero, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'H' || tile == 'K')
			g.drawImage(GameGui.herok, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'G')
			g.drawImage(GameGui.rguard, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'D')
			g.drawImage(GameGui.dguard, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'g')
			g.drawImage(GameGui.sdguard, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'U')
			g.drawImage(GameGui.sguard, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'O')
			g.drawImage(GameGui.ogre, dx, dy, tilewidth, tileheight, this);
		else if(tile == '8')
			g.drawImage(GameGui.sogre, dx, dy, tilewidth, tileheight, this);
		else if(tile == '*')
			g.drawImage(GameGui.weapon, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'k'){
			if(level == 0)
				g.drawImage(GameGui.lever, dx, dy, tilewidth, tileheight, this);
			else if(level == 1)
				g.drawImage(GameGui.key, dx, dy, tilewidth, tileheight, this);
		}
		else if(tile == '$')
			g.drawImage(GameGui.overkey, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'I')
			g.drawImage(GameGui.door, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'S')
			g.drawImage(GameGui.opendoor, dx, dy, tilewidth, tileheight, this);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(GameGui.background, x, y, mapWidth*tilewidth, mapHeight*tileheight, this);
		for(int i = 0; i < m.length; i++){
			int dy = y + i*tileheight;
			for(int j = 0; j < m[i].length; j++){
				int dx = x + j*tilewidth;
				printTile(g, m[i][j], dx, dy);
			}
		}

		if(game.isGameOver())
			g.drawImage(GameGui.gameover, x+(mapWidth*tilewidth-200)/2, y+mapHeight*tileheight+30, 200, 80, this);
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
		level = game.getLevel();
	} 

	public void keyReleased(KeyEvent e) {} 
	public void keyTyped(KeyEvent e) {} 
}
