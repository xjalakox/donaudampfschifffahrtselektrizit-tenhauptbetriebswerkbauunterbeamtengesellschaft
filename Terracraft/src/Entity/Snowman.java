package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import gfx.Sprite;
import terracraft.Game;
import terracraft.Handler;
import terracraft.Id;
import terracraft.Utils;
import tile.source.Tile;

public class Snowman extends Entity {

	private Sprite[] sprite = new Sprite[3];
	private int tick, pause, delay = 0, direction;
	private boolean standing;

	public Snowman(int x, int y, int breite, int height, Handler handler, Id id) {
		super(x, y, breite, height, handler, id);
		for (int i = 0; i < sprite.length; i++) {
			sprite[i] = new Sprite(Game.sheet, 3 + i, 2, 1, 2);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(x, y, breite, height);
		g.drawImage(sprite[frame].getBufferedImage(), x, y, breite, height, null);
	}

	public void tick() {
		if (standing) {
			pause++;
			if (pause == 10) {
				pause = 0;
				standing = false;
			}
		} else {
			x += velX;
			y += velY;
			move();
			frameticks();
			collision();
		}
	}

	private void collision(){
		if(y<Game.getFrameWidth()){
			setVelY(0);
		}
		for (Tile ti : handler.tile2) {

			if (getTop().intersects(ti.getBottom())) {
				setVelY(0);
				y = ti.getY() + 33;
				jumping = false;
				falling = true;
				gravity = 0;
			}
			if (getBottom().intersects(ti.getTop())) {
				setVelY(0);
				y = ti.getY() - 90;

			} else if (!jumping) {
				falling = true;
			}

			if (getLeft().intersects(ti.getRight())) {
				setVelX(0);
				jumping=true;
				falling = false;
				gravity=17f;
				setVelX(2);
				x = ti.getX() + 53;
				standing=true;
			}
			if (getRight().intersects(ti.getLeft())) {
				setVelX(0);
				jumping=true;
				falling = false;
				gravity=17f;
				setVelX(2);
				x = ti.getX() - 86;
				standing=true;
			}

		}
		
		if (jumping) {
			jumping(0.5f);
		}
		
		if(falling){
			falling();
			if(velY==1){
				tick=delay;
				move();
			}
		}
	}

	private void frameticks() {
		framedelay++;
		if (framedelay >= 13) {
			frame++;
			if (frame >= 3) {
				frame = 0;
			}
			framedelay = 0;
		}
	}

	private void move() {
		if (tick == delay) {
			delay = Utils.RandomInt(200, 600);
			direction = Utils.RandomInt(2);
			if (direction == 0) {
				setVelX(1);
			} else {
				setVelX(-1);
			}
			tick = 0;
		} else {
			tick++;
		}
	}

	public Rectangle getRight() {
		return new Rectangle(getX() + breite - 5, getY() + 5, 5+20, height - 10);
	}

	public Rectangle getLeft() {
		return new Rectangle(getX()-20, getY() + 5, 5, height - 10);
	}

}
