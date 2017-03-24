package dkeep.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class BuildFrame extends JPanel implements MouseListener, MouseMotionListener{
	private int x = 50, y = 0, tileheight = 30, tilewidth = 30;
	private char[] tiles = {'X', 'I', 'k', 'O', 'H', ' '};
	private char selected = ' ';
	private int bx = 0, by = 0;
	private int mapHeight, mapWidth;
	private char[][] m; 
	private boolean drag = false;
	
	public BuildFrame(int OgreNumber, char[][] userMap, boolean make){
		m = userMap;
		if(make){
			for(int i = 0; i < userMap.length; i++){
				for(int j = 0; j < userMap[i].length;  j++){
					if(i == 0 || i == userMap.length-1)
						m[i][j] = 'X';
					else{
						if(j == 0 || j == userMap[i].length-1)
							m[i][j] = 'X';
						else
							m[i][j] = ' ';
					}

				}
			}
		}
		mapHeight = userMap.length;
		mapWidth = userMap[0].length;
		selected = ' ';
		by = (mapHeight+1)*tileheight;

		addMouseListener(this);
		addMouseMotionListener(this);
		setLayout(null);	
	}

	private void printTile(Graphics g, char tile, int dx, int dy){
		if(tile == 'X')
			g.drawImage(GameGui.wall, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'H' || tile == 'A')
			g.drawImage(GameGui.hero, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'H' || tile == 'K')
			g.drawImage(GameGui.herok, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'O')
			g.drawImage(GameGui.ogre, dx, dy, tilewidth, tileheight, this);
		else if(tile == '8')
			g.drawImage(GameGui.sogre, dx, dy, tilewidth, tileheight, this);
		else if(tile == '*')
			g.drawImage(GameGui.weapon, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'k')
			g.drawImage(GameGui.key, dx, dy, tilewidth, tileheight, this);
		else if(tile == '$')
			g.drawImage(GameGui.overkey, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'I')
			g.drawImage(GameGui.door, dx, dy, tilewidth, tileheight, this);
		else if(tile == 'S')
			g.drawImage(GameGui.opendoor, dx, dy, tilewidth, tileheight, this);
		else if(tile == ' ')
			g.drawImage(GameGui.btile, dx, dy, tilewidth, tileheight, this);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < m.length; i++){
			int dy = y + i*tileheight;
			for(int j = 0; j < m[i].length; j++){
				int dx = x + j*tilewidth;
				printTile(g, m[i][j], dx, dy);
			}
		}
		g.drawString("Tiles:", 0, by+20);

		for(int i = 0; i < tiles.length; i++){
			int dx = bx+50+i*2*tilewidth;

			printTile(g, tiles[i], dx, by);
		}

		g.drawString("Selected:", bx+50+tiles.length*2*tilewidth+20, by+20);
		printTile(g, selected, bx+50+tiles.length*2*tilewidth+100, by);
	}

	public void mousePressed(MouseEvent e) {
		drag = true;

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
	
	public void mouseReleased(MouseEvent e) {
		drag = false;
	}
	
	public void mouseDragged(MouseEvent e) {
		if(drag){
			int dx = e.getX()-x;
			int dy = e.getY()-y;
			if(dx >= 0 && dx <= mapWidth*tilewidth && dy >= 0 && dy <= mapHeight*tileheight)
				m[dy/tileheight][dx/tilewidth] = selected;
			repaint();
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {} 
	public void mouseExited(MouseEvent e) {} 
	public void mouseMoved(MouseEvent e) { }
}
