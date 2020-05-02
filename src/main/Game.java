 package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entities.Entity;
import entities.PipeGenerator;
import entities.Player;
import graphics.Background;
import graphics.Hud;
import graphics.Spritesheet;

public class Game extends Canvas implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	private boolean isRunning = true;
	public static final int screenWidth  = 160;
	public static final int screenHeight = 192;
	public static final int scale  = 4;
	
	public BufferedImage image;
	public static Spritesheet spritesheet;
	public Background background;
	
	public PipeGenerator pipeGenerator;
	public static Hud hud;
	public static String gameState = "STARTMENU";
	public static boolean checkHitBox = false;
	
	public static List<Entity> entities = new ArrayList<Entity>();
	public static Player player;
	
	public Game() {
		initializeFrame();
		addKeyListener(this);
		image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
		spritesheet = new Spritesheet("/spritesheet1.png");
		hud = new Hud();
		pipeGenerator = new PipeGenerator();
		background = new Background();
		
		/* entities */
		player = new Player((screenWidth/3)-8, (screenHeight/2)-8, 16, 9);
		entities.add(player);
	}
	
	public void initializeFrame() {
		frame = new JFrame("Flappy Copy");
		frame.setPreferredSize(new Dimension(screenWidth*scale, screenHeight*scale));
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		this.requestFocus();
	}
	
	public synchronized void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
		
		if (gameState == "PLAYING") {
			pipeGenerator.tick();
			
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).tick();
			}
		}
		
	}
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(100, 170, 255));
		g.fillRect(0, 0, screenWidth*scale, screenHeight*scale);
		
		/*** RENDER GRAPHICS ***/
		
		// Render background
		background.render(g);
		// Render entities 
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g);
		}

		// Render Hud (the last to render)
		hud.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, screenWidth*scale, screenHeight*scale, null);
		bs.show();
	}
	
	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
		    lastTime = now;
		    while(delta >= 1) {
		    	tick();
				render();
		    	delta--;
		    }

		}
	}

	public void keyPressed(KeyEvent e) {
		// Player press space
		if (e.getKeyCode() == KeyEvent.VK_SPACE) { 
			if (gameState == "PLAYING") {
				Game.player.jump = true; 
			}
			// Start the game if in STARTMENU screen
			if (gameState == "STARTMENU") {
				gameState = "PLAYING";
			}
		}
		
		// Player press H
		if (e.getKeyCode() == KeyEvent.VK_H) { 
			if (checkHitBox)
				checkHitBox = false;
			else if (!checkHitBox) 
				checkHitBox = true;
		}
		
		// Player press Enter
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			// Start the game if in STARTMENU screen
			if (gameState == "STARTMENU") {
				gameState = "PLAYING";
			}
			
			// Restart the Game if in GAMEOVER screen
			if (gameState == "GAMEOVER") {
				
				gameState = "STARTMENU";
				
				entities.clear();
				pipeGenerator.generationCounter = 90;
				player = new Player((screenWidth/3)-8, (screenHeight/2)-8, 16, 9);
				entities.add(player);
			}
			

		}
		
		// Player press Up
		if (e.getKeyCode() == KeyEvent.VK_UP) { 
			if (gameState == "STARTMENU") {
				hud.selectedOption--;
				if (hud.selectedOption < 0) {
					hud.selectedOption = 2;
				}
				Sound.menu.play();
			}
		}
		// Player press Down
		if (e.getKeyCode() == KeyEvent.VK_DOWN) { 
			if (gameState == "STARTMENU") {
				hud.selectedOption++;
				if (hud.selectedOption > 2) {
					hud.selectedOption = 0;
				}
				Sound.menu.play();
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
		
	}
}
