package main;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import entities.AI;
import entities.Ball;
import entities.Player;
import entities.TrajectoryLine;

public class Game implements Runnable {

	private GameFrame frame;
	private GamePanel panel;
	
	public static Player player;
	private AI ai;
	private Ball ball;
	public TrajectoryLine trajectoryLine;
	
	private Thread gameThread;
	private boolean running = true;
	 
	private final int FPS = 120;
	private final int UPS = 60;
	
	
	private final static int TILES_DEFAULT_SIZE = 20;
	private final static float SCALE = 1.0f;
	private final static int TILES_IN_WIDTH = 50;
	private final static int TILES_IN_HEIGHT = 27;
	private final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public final static int BALL_DIAMETER = (int) (16 * SCALE);
	
	private final int PLAYER_HEIGHT = 120;
	private final int PLAYER_WIDTH = 20;
	public Game() {
		
		initializeClasses();		
				
		panel = new GamePanel(this);
		frame = new GameFrame(panel);
		panel.setFocusable(true);
		panel.requestFocus();
		panel.addKeyListener(new AL());
		startGameLoop();
				
	}
	
	// public methods
	
	public void render(Graphics g) {
		grid(g);
		player.render(g);
		ball.render(g);
		trajectoryLine.render(g);
		ai.render(g);
	}
	
	// private methods
	
	private void move() {
		player.move();
		ball.move();
		ai.move();
	}
	
	private void initializeClasses() {
		ball = new Ball(this, (GAME_WIDTH / 2) - (BALL_DIAMETER/2), BALL_DIAMETER, BALL_DIAMETER);	
		trajectoryLine = new TrajectoryLine(ball);
		ai = new AI(trajectoryLine, 970, GAME_HEIGHT/2, PLAYER_WIDTH, PLAYER_HEIGHT);
		player = new Player(10, GAME_HEIGHT/2, PLAYER_WIDTH, PLAYER_HEIGHT);
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
		
		if(ball.intersects(ai)) {
			ball.setXDir(-ball.xVel);
		}
		
		if(ball.x > GAME_WIDTH || ball.x < 0) {
			initializeClasses();
		
		}
		
		if(player.y >= GAME_HEIGHT - PLAYER_HEIGHT)
			player.y = GAME_HEIGHT - PLAYER_HEIGHT;
		if(player.y <= 0)
			player.y = 0;
		
		if(ai.y >= GAME_HEIGHT - PLAYER_HEIGHT)
			ai.y = GAME_HEIGHT - PLAYER_HEIGHT;
		if(ai.y <= 0)
			ai.y = 0;	}
	
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
	
	public class AL extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
	        
		}
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		
		}
	}
}

