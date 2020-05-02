package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import main.Sound;

public class Player extends Entity{

	public  int hearts = 3;
	
	public  int score = 0;
	public static int scoreMax = 0;
	private int scoreCounter = -32;
	private int scoreTime = 90;
	
	private boolean canTakeDamage    = true;
	private int invincibilityCounter = 0;
	private int invincibilityTime    = 90;

	private int hitFlashCounter = 0;
	private int hitFlashTime	= 2;
	
	public  boolean jump = false;
	private double  jumpHeight = 3.8;
	
	private double  gravity = 0.2; 
	private double  vSpeed  = -jumpHeight/2;
	
	private BufferedImage[] playerSprite;
	 
	/*
	 * 
	 */

	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		// Sets player sprite (2 sprites)
		playerSprite = new BufferedImage[2];
		playerSprite[0] = Game.spritesheet.getSprite(0,  0, 16, 16);
		playerSprite[1] = Game.spritesheet.getSprite(16, 0, 16, 16);
	}
	
	public void tick() {
		// increase player score
		scoreCounter++;
		if (scoreCounter >= scoreTime) {
			scoreCounter = 0;
			if (canTakeDamage) {
				score++;
				Sound.score.play();
			}
		}
		// execute player jump
		if (jump) {
			Sound.jump.play();
			jump = false;
			vSpeed = -jumpHeight;
		}
		
		// Lock the player position inside the screen
		if (Game.player.getY() < -8) {
			Game.player.setY(-8);
			vSpeed = 0;
		}
		if (Game.player.getY() > Game.screenHeight - 10) {
			vSpeed = -jumpHeight;
		}
		
		// Adds gravity to the player vertical speed
		vSpeed += gravity;
		
		// Set vertical speed to player Y value
		setY((getY() + vSpeed));
		
		// Counter for invincibility time 
		if (!canTakeDamage) {
			invincibilityCounter++;
			if (invincibilityCounter >= invincibilityTime) {
				invincibilityCounter = 0;
				canTakeDamage = true;
			}
		}
		
		// Collision with pipes
		for (int i = 0; i < Game.entities.size(); i++) {
			
			Entity e = Game.entities.get(i);
			
			if (e != this) {
				if ((isColliding(this, e)) && (canTakeDamage)) {
					canTakeDamage = false;
					hearts--;
					
					// Play damage sound
					Sound.damage.play();
				}
			}
		}
		
		// Game over if no hearts
		if (hearts <= 0) {
			
			// Set new score record
			if (score > scoreMax) {
				scoreMax = score;
			}
			Sound.death.play();
			Game.gameState = "GAMEOVER";
		}
		
	}
	
	public void render(Graphics g) {
		
		// If not taking damage render normally render the sprite
		if (canTakeDamage) {
			if (vSpeed < 0) {
				g.drawImage(playerSprite[0], (int)getX(), (int)getY(), null);
			} else {
				g.drawImage(playerSprite[1], (int)getX(), (int)getY(), null);
			}
			
		} else {	
		// If taking damage render with hitflash
			hitFlashCounter++;
			if (hitFlashCounter >= hitFlashTime) {
				hitFlashCounter = 0;

				if (vSpeed < 0){
					g.drawImage(playerSprite[0], (int)getX(), (int)getY(), null);
				} else {
					g.drawImage(playerSprite[1], (int)getX(), (int)getY(), null);
				}
			} 
		}
		
		//Hit box test
		if (Game.checkHitBox) {
			g.setColor(Color.blue);
			g.drawRect((int)getX(), (int)getY(), getWidth(), getHeight());	
		}
	}
}






















