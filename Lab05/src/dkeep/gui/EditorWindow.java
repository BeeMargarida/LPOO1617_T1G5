package dkeep.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class EditorWindow {
	public JFrame frame;
	public BuildFrame buildframe;
	
	public EditorWindow(){
		frame = new JFrame("Build");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500,500));
	}
}
