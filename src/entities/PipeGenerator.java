package entities;

import java.util.Random;

import main.Game;

public class PipeGenerator {
	
	public  int generationCounter = 90;
	private int generationTime = 90;
	
	private int gapBetweenPipes = 62;
	
	private Random random = new Random();
	
	public void tick() {
		
		generationCounter++;
		
		if (generationCounter >= generationTime) {
			
			generationCounter = 0;
			
			/* 
			 * Generate random number for the pipes positions
			 * +16 its to show at least one tile on the upPipe
			 */
			
			int randomNum = random.nextInt(Game.screenHeight - 102) +16;
			
			/* GENERATE PIPES */
			UpPipe upPipe = new UpPipe(Game.screenWidth, 0, 32, randomNum);
			Game.entities.add(upPipe);

			DownPipe downPipe = new DownPipe(Game.screenWidth, randomNum + gapBetweenPipes - (Game.hud.selectedOption * 5), 32, Game.screenHeight - randomNum);
			Game.entities.add(downPipe);
		}
	}
}
