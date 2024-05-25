package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.Game;

	//Just in case it becomes a problem later on, I don't think it should, it just effects the visuals I think, but if it does
	// call the calculateTrajectoryPoints in the game constructor. 
	// The problem I am refering to is that it draws a final line between the last point and initial starting point for some reason
	// If it turns into a problem and I can't fix it go to commit 7 randomized ball directions
	
	
	// Also, find a way to make it so it regenerats a new y coordinate without having to restart the game
		
public class TrajectoryLine {
    
	private Ball ball;
	private Color color;
	
	private int xVel, yVel;

	private int yInitial;
	
	public  ArrayList<Point> trajectoryPoints; 
	private ArrayList<Color> trajectoryColors;
	public boolean hitsPaddle;

	public TrajectoryLine(Ball ball) {
		this.ball = ball; 
		this.yInitial = Ball.randYStart + (Game.BALL_DIAMETER/2);
		
		trajectoryPoints = new ArrayList<>();
		trajectoryColors = new ArrayList<>();
		
		calculateTrajectoryPoints();

	}
	
	// public methods
	
	public void render(Graphics g) {
		
		drawTrajectory(g); 
		
	}
	
	// edge case, if it doesn't hit a floor/ceiling first
	
	public void calculateTrajectoryPoints() {
	
		int nextY = 0;
		int nextX = 0;
		int t;
		yVel = ball.yVel;
		xVel = ball.xVel;
		int count = 0;
        
		int paddle2Axis = 970;
		int paddle1Axis = 30;
		
		initialTrajectoryLine(xVel, yVel);
		
		while(count < 3) {
			
			reverseYVel();
			
			Point lastPoint = trajectoryPoints.get(trajectoryPoints.size() - 1); // gets the last point
			
			nextY = (lastPoint.y == Game.GAME_HEIGHT) ? 0 : Game.GAME_HEIGHT; // alternate y coordinate
			
			// calculate x coordinate
			t = (nextY - lastPoint.y) / yVel;   
	        nextX = lastPoint.x + xVel * t;
	        	        
	        // check if it intersects paddle:
	        if(nextX >= paddle2Axis) {
	    
	        	// calculate coordinate on paddle axis using 
	        	nextX = paddle2Axis; 
	        	t = (nextX - lastPoint.x) / xVel;
	        	nextY = lastPoint.y + yVel * t;
	        	
	        	addPoint(nextX, nextY);	
	    	    reverseXVel(); 
	    	    pointAfterPaddle(nextX, nextY, lastPoint);
	    	   
	    	    count++;
	    	   
	        } else if(nextX <= paddle1Axis) {
	    	   
	        	// calculate coordinate on paddle axis using 
	    	    nextX = paddle1Axis;
	    	    t = (nextX - lastPoint.x) / xVel;
	    	    nextY = lastPoint.y + yVel * t;
	    	    
	    	    addPoint(nextX, nextY);
	    	    reverseXVel(); 
	    	    pointAfterPaddle(nextX, nextY, lastPoint);
	    	   
	        } else {
	        	addPoint(nextX, nextY);
	        }
	      
	        color = getRandomColor();
	        trajectoryColors.add(color);
		}
		
	}
	
	// private methods
	
	private void initialTrajectoryLine(int xVel, int yVel) {		
		
		int xFinal = 0;
		int yFinal = 0;
		int xInitial = Game.GAME_WIDTH / 2;
		int t;
		
		if(yVel < 0 && xVel >0) { // up and to the right
			yFinal = 0;
			t = (yFinal - yInitial) / yVel;
			xFinal = xInitial + xVel * t;
		} else if(yVel < 0 && xVel < 0) { // up and to the left
			yFinal = 0;
			t = (yFinal - yInitial) / yVel;
			xFinal = xInitial + xVel * t;
		} else if(yVel > 0 && xVel > 0) { // down and to the right
			yFinal = Game.GAME_HEIGHT;
			t = (yFinal - yInitial) / yVel;
			xFinal = xInitial + xVel * t;
		} else if(yVel > 0 && xVel < 0) { // down and to the left
			yFinal = Game.GAME_HEIGHT;
			t = (yFinal - yInitial) / yVel;
			xFinal = xInitial + xVel * t;
		}
		
		trajectoryPoints.add(new Point(xInitial, yInitial));
		trajectoryPoints.add(new Point(xFinal, yFinal));
	}
	
	private void pointAfterPaddle(int xOnPaddle, int yOnPaddle, Point lastPoint) {
		
		int yAfterPaddleBounce = (lastPoint.y == Game.GAME_HEIGHT) ? 0 : Game.GAME_HEIGHT;
	    int t = (yAfterPaddleBounce - yOnPaddle) / yVel;
	    int xAfterPaddleBounce = xOnPaddle + xVel * t;
	    trajectoryPoints.add(new Point(xAfterPaddleBounce, yAfterPaddleBounce));
        
	}

	private void addPoint(int x, int y) {
		trajectoryPoints.add(new Point(x, y));
	}
	
	private void drawTrajectory(Graphics g) {
		
		Color newColor;
		newColor = Color.red;
		
		for(int i = 0; i < trajectoryPoints.size() - 1; i++) {	
			Point initialPoint = trajectoryPoints.get(i);
			Point finalPoint = trajectoryPoints.get(i + 1);		
			
			if(finalPoint.x == 970 || finalPoint.x == 30) {
				newColor = trajectoryColors.get(i % trajectoryColors.size());
			}

	        g.setColor(newColor);

			g.drawLine(initialPoint.x, initialPoint.y, finalPoint.x, finalPoint.y);

		}
	}
	
	private void reverseYVel() {
		yVel *= -1;
	}
	
	private void reverseXVel() {
		xVel *= -1;
	}

	private Color getRandomColor() {
		
		int r = (int) (Math.random() * 256);
		int g = (int) (Math.random() * 256);
		int b = (int) (Math.random() * 256);
		
		return new Color(r, g, b);
	}
}

