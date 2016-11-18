package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import terracraft.Game;
import terracraft.Handler;
import terracraft.Id;
import tile.Door;
import tile.source.Tile;

public abstract class Entity {


	public int x, y, width, height, velX, velY, health;
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

	public Entity(int x, int y, int width, int height, Handler handler, Id id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		this.id = id;

	}

	public Rectangle getArea() {
		return new Rectangle(x - 128, y - 128, 96 * 3 + width, 96 * 3 + height);
	}

	public abstract void render(Graphics2D g);

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
		return width;
	}

	public void setBreite(int width) {
		this.width = width;
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
		if (gravity <= 17.0f) {
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
				if (!ti.getId().equals(Id.Tree)) {
					if (getBottom().intersects(ti.getTop())) {

						if (ti.getId().equals(Id.Door)) {
							if (!((Door) ti).isOpen()) {
								gravity = 0f;
								jumping = false;
								falling = false;
								y = ti.getY() - 90;
							}
						} else {

							gravity = 0f;
							jumping = false;
							falling = false;
							y = ti.getY() - 87;

						}
					}
				}
			}
		}
		setVelY((int) gravity);
	}

	public Rectangle getBounds() {

		return new Rectangle(getX(), getY(), width, height);

	}

	public Rectangle getBottom() {
		return new Rectangle(getX() + 6, getY() + height - 16, width - 10, 16);

	}

	public Rectangle getRight() {

		return new Rectangle(getX() + width - 5, getY() + 5, 5, height - 10);

	}

	public Rectangle getLeft() {

		return new Rectangle(getX(), getY() + 5, 5, height - 10);

	}

	public Rectangle getTop() {

		return new Rectangle(getX() + 5, getY(), width - 10, 16);

	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
