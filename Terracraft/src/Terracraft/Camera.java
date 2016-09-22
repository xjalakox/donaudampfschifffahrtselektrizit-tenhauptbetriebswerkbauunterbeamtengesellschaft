package terracraft;

import entity.Entity;

public class Camera {

	public int x, y;

	public void tick(Entity player) {
		setX(-player.getX() + Game.getFrameWidth()/2);
		setY(-player.getY() + 435);
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
