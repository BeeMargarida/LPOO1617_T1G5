package dkeep.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameWindow {
	public JFrame frame;
	public GameFrame gameframe;
	public JButton btnUp, btnLeft, btnRight, btnDown;
	
	public GameWindow(){
		frame = new JFrame("Game");
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				GameGui.editor.btnSave.setEnabled(false);
				frame.setVisible(false);
				if(GameGui.editor.frame.isVisible())
					GameGui.bframe.frame.setVisible(true);
			}
		});
		//frame.setPreferredSize(new Dimension(500, 500));
		frame.setMinimumSize(new Dimension(700,600));
		frame.getContentPane().setLayout(null);
		addElements();
	}
	
	private void addElements(){
		addUpButton();
		addLeftButton();
		addRightButton();
		addDownButton();
	}
	
	
	private void addUpButton(){
		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGameFrame('w');
			}
		});
		btnUp.setBounds(542, 256, 89, 23);
		btnUp.setVisible(true);
		frame.getContentPane().add(btnUp);
	}
	
	private void addLeftButton(){
		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGameFrame('a');
			}
		});
		btnLeft.setBounds(499, 290, 89, 23);
		frame.getContentPane().add(btnLeft);
	}
	
	private void addRightButton(){
		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGameFrame('d');
			}
		});
		btnRight.setBounds(591, 290, 89, 23);
		frame.getContentPane().add(btnRight);
	}
	
	private void addDownButton(){
		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGameFrame('s');
			}
		});
		btnDown.setBounds(542, 324, 89, 23);
		frame.getContentPane().add(btnDown);
	}
	
	private void updateGameFrame(char dir){
		if(GameGui.game.isGameOver())
			return;
		GameGui.game.update(dir); 
		gameframe.repaint();
		gameframe.m = GameGui.game.getBoard();
		gameframe.mapHeight = gameframe.m.length;
		gameframe.mapWidth = gameframe.m[0].length;
		gameframe.level = GameGui.game.getLevel();
		gameframe.requestFocusInWindow();
	}
}
