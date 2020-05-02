package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Background {
	
	private BufferedImage background;
	
	public Background() {
		background = Game.spritesheet.getSprite(0, 32, 61, 32);
	}
	
	public void render(Graphics g) {
		g.drawImage(background, 0, Game.screenHeight - 32, null);
		g.drawImage(background, 61, Game.screenHeight - 32, null);
		g.drawImage(background, 122, Game.screenHeight - 32, null);
	}

}
