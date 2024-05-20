package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.Game;

public class TrajectoryLine {
    
	private Ball ball;
	private Color color;
	
	private int xVel, yVel;

	private int yFinal = Game.GAME_HEIGHT;
	private int yInitial;
	
	private ArrayList<Point> trajectoryPoints; 
	private ArrayList<Color> trajectoryColors;
	public boolean hitsPaddle;
	
	public TrajectoryLine(Ball ball) {
		this.ball = ball; // comment what this does
		this.yInitial = Ball.randStartPos;
		
		trajectoryPoints = new ArrayList<>();
		trajectoryColors = new ArrayList<>();
	}
	
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
        		
		initialTrajectoryLine(xVel, yVel);
		
		while(count < 2) {
			
			reverseYVel();
			
			Point lastPoint = trajectoryPoints.get(trajectoryPoints.size() - 1); // gets the last point
			
			nextY = (lastPoint.y == Game.GAME_HEIGHT) ? 0 : Game.GAME_HEIGHT; // alternate y coordinate
			
			// calculate x coordinate
			t = (nextY - lastPoint.y) / yVel;   
	        nextX = lastPoint.x + xVel * t;
	        	        
	        // check if it intersects paddle:
	        if(nextX >= 970) {
	    
	        	// calculate coordinate on paddle axis using 
	        	nextX = 970; 
	        	t = (nextX - lastPoint.x) / xVel;
	        	nextY = lastPoint.y + yVel * t;
	        	
	        	addPoint(nextX, nextY);	
	    	    reverseXVel(); 
	    	    pointAfterPaddle(nextX, nextY, lastPoint);
	    	   
	    	    count++;
	    	   
	        } else if(nextX <= 30) {
	    	   
	        	// calculate coordinate on paddle axis using 
	    	    nextX = 30;
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
		
		//System.out.println(trajectoryPoints);
	}
	
	public void drawTrajectory(Graphics g) {
		
		// maybe make it a known amount of colors, make a predefined array of colors instead of random and use that?
		
		Color newColor;
		newColor = Color.red;
		
		for(int i = 0; i < trajectoryPoints.size() - 1; i++) {	
			Point initialPoint = trajectoryPoints.get(i);
			Point finalPoint = trajectoryPoints.get(i + 1);		
			
			if(finalPoint.x == 970 || finalPoint.x == 30) {
				newColor = trajectoryColors.get(i % trajectoryColors.size());
			}
			
			// check if the x coordinate is 970 or 30, then change the color
			
			g.drawLine(initialPoint.x, initialPoint.y, finalPoint.x, finalPoint.y);
	        g.setColor(newColor);

		}
	}
	
	private void addPoint(int x, int y) {
		trajectoryPoints.add(new Point(x, y));
        
	}
	
	private void pointAfterPaddle(int xOnPaddle, int yOnPaddle, Point lastPoint) {
		
		int yAfterPaddleBounce = (lastPoint.y == Game.GAME_HEIGHT) ? 0 : Game.GAME_HEIGHT;
	    int t = (yAfterPaddleBounce - yOnPaddle) / yVel;
	    int xAfterPaddleBounce = xOnPaddle + xVel * t;
	    trajectoryPoints.add(new Point(xAfterPaddleBounce, yAfterPaddleBounce));
        
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
	
	
	

	
	
	private void initialTrajectoryLine(int xVel, int yVel) {		
//		int xInitial = Game.GAME_WIDTH / 2;
//		
//		int t = (yFinal - yInitial) / yVel;
//		int xFinal = xInitial + xVel * t;
		
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
	
}














