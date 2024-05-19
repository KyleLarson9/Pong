package main;

import java.awt.Graphics;

import entities.Ball;
import entities.Player;
import entities.TrajectoryLine;

public class Game implements Runnable {

	private GameFrame frame;
	private GamePanel panel;
	
	public static Player player;
	private Ball ball;
	private TrajectoryLine trajectoryLine;
	
	private Thread gameThread;
	private boolean running = true;
	 
	private final int FPS = 120;
	private final int UPS = 200;
	
	
	private final static int TILES_DEFAULT_SIZE = 20;
	private final static float SCALE = 1.0f;
	private final static int TILES_IN_WIDTH = 50;
	private final static int TILES_IN_HEIGHT = 27;
	private final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public final static int BALL_DIAMETER = (int) (20 * SCALE);
	
	public Game() {
		
		initializeClasses();
		//trajectoryLine.collisionPosition();
		//trajectoryLine.downRightCollision();
		trajectoryLine.calculateTrajectoryPoints();
		
		panel = new GamePanel(this);
		frame = new GameFrame(panel);
		panel.setFocusable(true);
		panel.requestFocus();
		startGameLoop();
		
	}
	
	private void initializeClasses() {
		ball = new Ball(this, (GAME_WIDTH / 2) - (BALL_DIAMETER/2), BALL_DIAMETER, BALL_DIAMETER);
		trajectoryLine = new TrajectoryLine(ball);
		player = new Player(970, 0, 20, 540);
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void render(Graphics g) {
		grid(g);
		ball.render(g);
		trajectoryLine.render(g);
		player.render(g);
	}
	
	public void move() {
		ball.move();
	}
	
	public void checkCollision() {
		
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
			trajectoryLine.calculateTrajectoryPoints();
		
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
				//System.out.println(frames + " FPS " + updates + "UPS");
				frames = 0;
				updates = 0;
			}

		}
	}
	
//	public Player getPlayer() {
//		return player; // describe what is happening 
//	}
}


















