package entities;

public class Pipe extends Entity {

	private int pipeSpeed = 1;
	
	public Pipe(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}
	
	public void tick() {
		setX(getX() - pipeSpeed);
	}

}
