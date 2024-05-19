package main;

import java.awt.Color;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	public GameFrame(GamePanel panel) {
		
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Pong");
		//this.setResizable(false);
		this.setBackground(Color.DARK_GRAY);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		  
	}
	
}
