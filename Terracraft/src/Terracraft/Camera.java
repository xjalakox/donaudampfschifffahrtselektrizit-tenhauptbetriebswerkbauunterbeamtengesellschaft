package Terracraft;

import Entity.Entity;

public class Camera {

	public int x, y;

	public void tick(Entity player) {
		setX(-player.getX() + Game.getFrameBreite()/2);
		setY(-player.getY() + Game.getFrameHöhe()/2);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
