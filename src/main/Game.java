package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import entities.Ball;
import entities.Player;
import entities.TrajectoryLine;

public class Game implements Runnable {

	private GameFrame frame;
	private GamePanel panel;
	
	public static Player player;
	public static Player player2;
	private Ball ball;
	public TrajectoryLine trajectoryLine;
	
	private Thread gameThread;
	private boolean running = true;
	 
	private final int FPS = 120;
	private final int UPS = 1;
	
	
	private final static int TILES_DEFAULT_SIZE = 20;
	private final static float SCALE = 1.0f;
	private final static int TILES_IN_WIDTH = 50;
	private final static int TILES_IN_HEIGHT = 27;
	private final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public final static int BALL_DIAMETER = (int) (16 * SCALE);
	
	public Game() {
		
		initializeClasses();		
				
		panel = new GamePanel(this);
		frame = new GameFrame(panel);
		panel.setFocusable(true);
		panel.requestFocus();
		startGameLoop();
				
	}
	
	// public methods
	
	public void render(Graphics g) {
		grid(g);
		ball.render(g);
		trajectoryLine.render(g);
		player.render(g);

	}
	
	// private methods
	
	private void move() {
		ball.move();		
	}
	
	private void initializeClasses() {
		ball = new Ball(this, (GAME_WIDTH / 2) - (BALL_DIAMETER/2), BALL_DIAMETER, BALL_DIAMETER);	
		trajectoryLine = new TrajectoryLine(ball);
		player = new Player(trajectoryLine, 970, 100, 20, 120);

	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	private void checkCollision() {
		
		if(ball.y <= 0) {
			ball.setYDir(-ball.yVel);
		}
		if(ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
			ball.setYDir(-ball.yVel);
		}
		
		if(ball.intersects(player)) {
			ball.setXDir(-ball.xVel);
		}
		
		if(ball.x > GAME_WIDTH || ball.x < 0) {
			initializeClasses();
		
		}
		
	}
	
	private void grid(Graphics g) {
		
//		for(int i = 0; i < GAME_WIDTH; i++) {
//			for(int j = 0; j < GAME_HEIGHT; j++) {
//				int x = i * TILES_SIZE;
//				int y = j * TILES_SIZE;
//				if((i + j) % 2 ==0) {
//					g.drawLine(0, y, GAME_WIDTH, y);
//				} else {
//					g.drawLine(x, 0, x, GAME_HEIGHT);
//				}
//			}
//		}
		
	}
	
	// override methoods
	
	@Override
	public void run() {
		double timePerFrame = 1_000_000_000.0 / FPS; // how long each from will last, 1 second
		double timePerUpdate = 1_000_000_000.0 / UPS;
		
		long previousTime = System.nanoTime();
			
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(running) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				move();		
		    	
				checkCollision();
				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				panel.repaint();
				frames++;
				deltaF--;
			}
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				frames = 0;
				updates = 0;
			}

		}
	}
	
}

