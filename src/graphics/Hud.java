package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import main.Game;

public class Hud {
	
	private BufferedImage[] playerHearts;
	
	public int selectedOption = 0;
	private String[] options  = { "easy", "normal", "hard" };
	
	private int restartFlashCounter = 30;
	private int restartFlashTime 	= 25;
	
	public Hud() {
		playerHearts = new BufferedImage[2];
		playerHearts[0] = Game.spritesheet.getSprite(48, 16, 11, 10); // Empty heart
		playerHearts[1] = Game.spritesheet.getSprite(64, 16, 11, 10); // Full heart
	}
	
	public void render(Graphics g) {
		
		if (Game.gameState == "PLAYING") {
			
			// Score HUD
			drawStringWithShadow("Score: " + Game.player.score, 16, 21, 12, g);
			
			// Black hearts
			for (int i = 0; i < 3; i++) {
				g.drawImage(playerHearts[0], 100 + (i * 15), 11, null);
			}
			// Red hearts
			for (int i = 0; i < Game.player.hearts; i++) {
				g.drawImage(playerHearts[1], 100 + (i * 15), 11, null);
			}
			
		} else if (Game.gameState == "GAMEOVER") {
			
			// Black background
			g.setColor(new Color(10, 10, 10, 100));
			g.fillRect(0, 0, Game.screenWidth, Game.screenHeight);
			
			// Game Over HUD
			drawStringWithShadow("GAMEOVER", 22, (Game.screenHeight/2)-20, 20, g);
			
			// Player's score
			drawStringWithShadow("Score: " + Game.player.score, 55, (Game.screenHeight - 35) , 10, g);
			
			// Player's record
			drawStringWithShadow("Record: " + Player.scoreMax, (Game.screenWidth/3), (Game.screenHeight - 20) , 10, g);
						
			// Restart 
			restartFlashCounter++;
			if (restartFlashCounter > restartFlashTime) {
				
				drawStringWithShadow("Press ENTER to restart", 16, (Game.screenHeight/2), 12, g);
				
				if (restartFlashCounter > restartFlashTime*3) {
					restartFlashCounter = 0;
				}
			}
			
		} else if (Game.gameState == "STARTMENU") {
			
			// Black background
			g.setColor(new Color(10, 10, 10, 100));
			g.fillRect(0, 0, Game.screenWidth, Game.screenHeight);
			
			// Title screen
			drawStringWithShadow("FLAPPY COPY", 11, Game.screenHeight/3, 20, g);
			
			// Select arrow
			drawStringWithShadow(">", 50, 90 + (selectedOption*18), 12, g);
			
			// Difficult options
			for (int i = 0; i < 3; i++) {
				drawStringWithShadow(options[i], 60, 90 + (i*18), 12, g);
			}
		}
	}
	
	public void drawStringWithShadow(String text, int x, int y, int fontSize, Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, fontSize));
		
		g.setColor(Color.black);
		g.drawString(text, x+1, y+1);
		
		g.setColor(Color.white);
		g.drawString(text, x, y);
	}
}









