package dkeep.gui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class GameWindow {
	public JFrame frame;
	public GameFrame gameframe;
	
	public GameWindow(){
		frame = new JFrame("Game");
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				GameGui.editor.btnSave.setEnabled(false);
				frame.setVisible(false);
				//if(GameGui.bframe.frame.isVisible())
					GameGui.bframe.frame.setVisible(true);
			}
		});
		frame.setPreferredSize(new Dimension(500, 500)); 
	}
}
