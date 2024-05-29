package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player extends Rectangle {
	
	private boolean moving;
	private boolean up, down;
	private int speed = 2;
	
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
	} 
	
	// public methods
	
	public void render(Graphics g) {
		g.setColor(Color.magenta);
		g.fillRect(x, y, width, height);
	}
	
	public void move() {
		updatePosition();
	}	
	
	private void updatePosition() {
		
		moving = false;
		
		if(up && !down) {
			y -= speed;
			moving = true;
		} else if(down && !up) {
			y += speed;
			moving = true;
		}
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				up = true;
				break;
			case KeyEvent.VK_S:
				down = true;
				break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				up = false;
				break;
			case KeyEvent.VK_S:
				down = false;
				break;
		}
	}
}
