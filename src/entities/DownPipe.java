package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class DownPipe extends Pipe{
	
	private BufferedImage[] upPipeSprites;

	public DownPipe(int x, int y, int width, int height) {
		super(x, y, width, height);
		upPipeSprites = new BufferedImage[2];
		upPipeSprites[0] = Game.spritesheet.getSprite(32, 0, 32, 16);
		upPipeSprites[1] = Game.spritesheet.getSprite(0, 16, 32, 16);
	}

	public void render(Graphics g) {
		g.drawImage(upPipeSprites[0], (int)getX(), (int)getY(), null);
		g.drawImage(upPipeSprites[1], (int)getX(), (int)getY()+16, getWidth(), getHeight(), null);

		if (getX() == -32) { Game.entities.remove(this); }
	}

}
