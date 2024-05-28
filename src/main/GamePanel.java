package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private Game game;
	
	private Dimension size;
	public GamePanel(Game game) {
		this.game = game;
		
		setPanelSize();
		this.setBackground(Color.DARK_GRAY);
	} 
	
	private void setPanelSize()	{
		size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
	}
	
	public void paintComponent(Graphics g) { // explain this
		super.paintComponent(g);
		game.render(g);
	}
	
	public Game getGame() { // explain this
		return game;
	}
}
