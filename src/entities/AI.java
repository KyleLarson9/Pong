package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class AI extends Rectangle {

	private TrajectoryLine trajectoryLine;
	private Ball ball;
	private ArrayList<Point> axisPoints; 
	
	private int speed = 2;
	private int currentTargetIndex = 0;
	
	public AI(Ball ball, TrajectoryLine trajectoryLine, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.trajectoryLine = trajectoryLine;
		this.ball = ball;
		this.axisPoints = new ArrayList<>();
	
	}
	
	public void render(Graphics g) {
		g.setColor(Color.magenta);
		g.fillRect(x, y, width, height);
	}
	
	public void move() {
	    getAxisPoints();
	    
	    
	    if (axisPoints.isEmpty() || currentTargetIndex >= axisPoints.size()) {
	        return;
	    }

	    Point target = axisPoints.get(currentTargetIndex);
	    int paddleCenterY = y + (height / 2);

	    if(paddleCenterY > target.y) {
	    	y-=speed;
	    } else if(paddleCenterY < target.y) {
	    	y+=speed;
	    }
	    
	    // this won't work because if it is close to the wall it won't get the center
	    // current index++ when the ball hits the paddle!
	    
	    if(ball.intersects(this)) {
	    	currentTargetIndex++;
	    }
	    

	}

	
	private void getAxisPoints() {
		for(int i = 0; i < trajectoryLine.trajectoryPoints.size(); i++) {
			if(trajectoryLine.trajectoryPoints.get(i).x == 970 - 8) {
				axisPoints.add(trajectoryLine.trajectoryPoints.get(i));
			}
		}
	}
	
}
