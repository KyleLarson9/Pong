package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.Game;

public class Ball extends Rectangle {

	private static Random rand = new Random();
	
	public int xVel = 1;
	public int yVel = 2;
	public int xDir, yDir;
				
	public static int randYStart = rand.nextInt(Game.GAME_HEIGHT - Game.BALL_DIAMETER);
	
	public Ball(Game game, int xPos, int BALL_WIDTH, int BALL_HEIGHT) {
		super(xPos, randYStart, BALL_WIDTH, BALL_HEIGHT);
		getDirection();
	}

	// public methods
	
	public void render(Graphics g) {		
		g.setColor(Color.white);
		g.fillOval(x, y, width, height);
	}
	 
	public void getDirection() {
		
		xDir = rand.nextInt(2);
		yDir = rand.nextInt(2);
		
		if(xDir == 0) {
			xDir--;
		} 
		
		setXDir(xDir * xVel);
	
		if(yDir == 0) {
			yDir--;
		}
		
		setYDir(yDir * yVel);
		
	}
	
	public void setXDir(int xDir) {
		xVel = xDir;
	}
	
	public void setYDir(int yDir) {
		yVel = yDir;
	}
	
	public void move() {
		x+=xVel;
		y+=yVel;
	}
	
}
