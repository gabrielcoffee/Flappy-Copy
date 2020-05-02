package entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Entity {
	
	private double x;
	private double y;
	private int width;
	private int height;
	
	public Entity(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
	}
	
	public boolean isColliding(Entity e1, Entity e2) {
		Rectangle rec1 = new Rectangle((int)e1.getX(), (int)e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle rec2 = new Rectangle((int)e2.getX(), (int)e2.getY(), e2.getWidth(), e2.getHeight());
		return (rec1.intersects(rec2));
	}

	
	/* GETTERS */
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	/* SETTERS */
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}

}
