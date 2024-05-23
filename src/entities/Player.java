package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import main.Game;

// for the player, loop through the arraylist finding the points on the paddle axis and going to that first one
// wait until the ball hits, then move to the next one
// for a more advanced one, make it aware of the surrounding points 


// create a view of just the points on the axis of the paddle

public class Player extends Rectangle {
	
	private TrajectoryLine trajectoryLine;
	
	private ArrayList<Point> axisPoints;
	
	public Player(TrajectoryLine trajectoryLine, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.trajectoryLine = trajectoryLine;
		
		axisPoints = new ArrayList<>();
		
		System.out.println(trajectoryLine.trajectoryPoints);
		System.out.println(trajectoryLine.trajectoryPoints.size());
		
		System.out.println("Points on axis: ");
		getAxisPoints();
		
		System.out.println(axisPoints);
	} 
	
	// public methods
	
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(x, y, width, height);
	}
	
	public void move() {
		
		// loop through the axisPoints array
		// move the center of the paddle to that y coordinate
		// move to the next point
		
	}
	
	// private methods
	
	private void getAxisPoints() {
				
		ArrayList<Point> trajectoryPoints = trajectoryLine.trajectoryPoints;
		
		for(int i = 0; i < trajectoryPoints.size(); i++) {
			
			if(trajectoryPoints.get(i).x == 970) {
				axisPoints.add(trajectoryPoints.get(i));
			}
			
		}
		
	}
	
}
