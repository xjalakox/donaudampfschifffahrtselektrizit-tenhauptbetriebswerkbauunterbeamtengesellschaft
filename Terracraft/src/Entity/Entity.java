package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import terracraft.Game;
import terracraft.Handler;
import terracraft.Id;
import tile.source.Tile;

public abstract class Entity {

	public int x, y, breite, height, velX, velY;
	public boolean removed, clicked, click;
	public Handler handler;
	public Id id;
	public int rotateAnglesLeft[] = { -45, -90, -150, -170 };
	public int rotateAnglesRight[] = { -45, 0, 60, 80 };
	public int frame, framedelay, frame2, framedelay2;
	public int moving = -1;
	public boolean jumping = false, falling = true;
	public boolean framereset = false;

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	public boolean isFramereset() {
		return framereset;
	}

	public void setFramereset(boolean framereset) {
		this.framereset = framereset;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public float gravity = 0f;

	public Entity(int x, int y, int breite, int height, Handler handler, Id id) {
		this.x = x;
		this.y = y;
		this.breite = breite;
		this.height = height;
		this.handler = handler;
		this.id = id;

	}
	
	public Rectangle getArea() {
		return new Rectangle(x - 128, y - 128, 96 * 3 + breite, 96 * 3 + height);
	}

	public abstract void render(Graphics g);

	public abstract void tick();

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
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

	public int getBreite() {
		return breite;
	}

	public void setBreite(int breite) {
		this.breite = breite;
	}

	public int getHeight() {
		return height;
	}

	public void setheight(int height) {
		this.height = height;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void jumping(float grav) {
		if(gravity<=17.0f){
		gravity -= grav;
		}
		setVelY((int) -gravity);
		if (gravity <= 0.0f) {
			falling = true;
			jumping = false;
		}
	}

	public void falling() {
		if (falling) {
			gravity += 0.5f;
			if (y > Game.getFrameWidth() - 200) {

				gravity = 0f;
				jumping = false;
				falling = false;

			}

			for (Tile ti : handler.tile2) {

				if (getBottom().intersects(ti.getTop())) {

					gravity = 0f;
					jumping = false;
					falling = false;

				}
			}

		}
		setVelY((int) gravity);
	}

	public Rectangle getBounds() {

		return new Rectangle(getX(), getY(), breite, height);

	}

	public Rectangle getBottom() {

		return new Rectangle(getX() + 6, getY() + height - 16, breite-10, 16);

	}

	public Rectangle getRight() {

		return new Rectangle(getX() + breite - 5, getY() + 5, 5, height - 10);

	}

	public Rectangle getLeft() {

		return new Rectangle(getX(), getY() + 5, 5, height - 10);

	}

	public Rectangle getTop() {

		return new Rectangle(getX() + 5, getY(), breite-10, 16);

	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
