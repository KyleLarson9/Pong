package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

// for the player, loop through the arraylist finding the points on the paddle axis and going to that first one
// wait until the ball hits, then move to the next one
// for a more advanced one, make it aware of the surrounding points 


// create a view of just the points on the axis of the paddle

public class Player extends Rectangle {
	
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
	} 
	
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(x, y, width, height);
	}
	
}
