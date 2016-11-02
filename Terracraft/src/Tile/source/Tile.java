package tile.source;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import gfx.Sprite;
import terracraft.Game;
import terracraft.Handler;
import terracraft.Id;
import terracraft.Utils;

public class Tile {

	public int x, y, width, height, velX, velY;
	Handler handler;
	Id id;
	public int damage;
	private Sprite currentSprite=new Sprite(Game.sheet, 7, 1, 1, 1);
	public boolean shouldRemove, blockOnTop = true, blockOnLeft = true, blockOnRight = true, blockOnBottom = true;
	private int mapX,mapY;

	public Tile(int x, int y, int Width, int height, Id id) {
		this.x = x;
		this.y = y;
		this.width = Width;
		this.height = height;
		this.id = id;
	}



	public void render(Graphics g) {

	}

	public Sprite getCurrentSprite() {
		return currentSprite;
	}



	public void setCurrentSprite(Sprite currentSprite) {
		this.currentSprite = currentSprite;
	}



	public void tick() {

	}

	public boolean shouldRemove() {
		return shouldRemove;
	}

	public void setAsRemoved() {
		shouldRemove = true;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int Width) {
		this.width = Width;
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

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle(x + 200, y - 430, 9, 10);
	}

	public Rectangle getRight() {
		return new Rectangle(x + width - 5, y + 5, 5, height - 10);
	}

	public Rectangle getLeft() {
		return new Rectangle(x, y + 5, 5, height - 10);
	}

	public Rectangle getBottom() {
		return new Rectangle(x, y + height - 8, width, 8);
	}

	public Rectangle getTop() {
		return new Rectangle(x + 2, y, width - 4, 8);
	}

	public Rectangle getTileBottom() {
		return new Rectangle(x, y + height, width, height);
	}

	public Rectangle getTileTop() {
		return new Rectangle(x, y - height, width, height);
	}

	public Rectangle getTileLeft() {
		return new Rectangle(x - width, y, width, height);
	}

	public Rectangle getTileRight() {
		return new Rectangle(x + width, y, width, height);
	}

	public int getDamage() {
		return damage;
	}

	public void setHealth(int damage) {
		this.damage = damage;
	}

	public void addDamage(int damage) {
		this.damage -= damage;
	}

	public void checkForBlockonTop() {
		blockOnTop = false;
		for (Tile ti : Game.handler.tile2) {
			if (getTileTop().intersects(ti.getBounds()) && ti.getId().equals(this.getId())) {
				blockOnTop = true;
			}

		}
	}

	public void checkForBlockonBottom() {
		blockOnBottom = false;
		for (Tile ti : Game.handler.tile2) {
			if (getTileBottom().intersects(ti.getBounds()) && ti.getId().equals(this.getId())) {
				blockOnBottom = true;
			}

		}
	}

	public void checkForBlockonRight() {
		blockOnRight = false;
		for (Tile ti : Game.handler.tile2) {
			if (getTileRight().intersects(ti.getBounds()) && ti.getId().equals(this.getId())) {
				blockOnRight = true;
			}

		}
	}

	public void checkForBlockonLeft() {
		blockOnLeft = false;
		for (Tile ti : Game.handler.tile2) {
			if (getTileLeft().intersects(ti.getBounds()) && ti.getId().equals(this.getId())) {
				blockOnLeft = true;
			}

		}
	}

	public void drawTile(Graphics g, int x, int y, int width, int height, Sprite alone, Sprite left, Sprite right,
			Sprite top, Sprite bottom, Sprite bottomright, Sprite bottomleft, Sprite bottomtop, Sprite leftright,
			Sprite topleft, Sprite topright, Sprite all, Sprite bottomrighttop, Sprite bottomlefttop,
			Sprite topleftright, Sprite bottomleftright) {

		if (!blockOnBottom && !blockOnLeft && !blockOnRight && !blockOnTop) {
			g.drawImage(alone.getBufferedImage(), x, y, width, height, null);
			currentSprite=alone;
		} else if (blockOnBottom && !blockOnLeft && !blockOnRight && !blockOnTop) {
			g.drawImage(bottom.getBufferedImage(), x, y, width, height, null);
			currentSprite=bottom;
		} else if (blockOnTop && !blockOnLeft && !blockOnRight && !blockOnBottom) {
			g.drawImage(top.getBufferedImage(), x, y, width, height, null);
			currentSprite=top;
		} else if (blockOnRight && !blockOnLeft && !blockOnBottom && !blockOnTop) {
			g.drawImage(right.getBufferedImage(), x, y, width, height, null);
			currentSprite=right;
		} else if (blockOnLeft && !blockOnBottom && !blockOnRight && !blockOnTop) {
			g.drawImage(left.getBufferedImage(), x, y, width, height, null);
			currentSprite=left;
		} else if (blockOnLeft && blockOnBottom && !blockOnRight && !blockOnTop) {//
			g.drawImage(bottomleft.getBufferedImage(), x, y, width, height, null);
			currentSprite=bottomleft;
		} else if (blockOnLeft && !blockOnBottom && blockOnRight && !blockOnTop) {
			g.drawImage(leftright.getBufferedImage(), x, y, width, height, null);
			currentSprite=leftright;
		} else if (blockOnLeft && !blockOnBottom && !blockOnRight && blockOnTop) {
			g.drawImage(topleft.getBufferedImage(), x, y, width, height, null);
			currentSprite=topleft;
		} else if (!blockOnLeft && blockOnBottom && blockOnRight && !blockOnTop) {
			g.drawImage(bottomright.getBufferedImage(), x, y, width, height, null);
			currentSprite=bottomright;
		} else if (!blockOnLeft && !blockOnBottom && blockOnRight && blockOnTop) {
			g.drawImage(topright.getBufferedImage(), x, y, width, height, null);
			currentSprite=topright;
		} else if (blockOnLeft && blockOnBottom && blockOnRight && blockOnTop) {
			g.drawImage(all.getBufferedImage(), x, y, width, height, null);
			currentSprite=all;
		} else if (!blockOnLeft && blockOnBottom && !blockOnRight && blockOnTop) {
			g.drawImage(bottomtop.getBufferedImage(), x, y, width, height, null);
			currentSprite=bottomtop;
		} else if (!blockOnLeft && blockOnBottom && blockOnRight && blockOnTop) {
			g.drawImage(bottomrighttop.getBufferedImage(), x, y, width, height, null);
			currentSprite=bottomrighttop;
		} else if (blockOnLeft && blockOnBottom && !blockOnRight && blockOnTop) {
			g.drawImage(bottomlefttop.getBufferedImage(), x, y, width, height, null);
			currentSprite=bottomlefttop;
		} else if (blockOnLeft && !blockOnBottom && blockOnRight && blockOnTop) {
			g.drawImage(topleftright.getBufferedImage(), x, y, width, height, null);
			currentSprite=topleftright;
		} else if (blockOnLeft && blockOnBottom && blockOnRight && !blockOnTop) {
			g.drawImage(bottomleftright.getBufferedImage(), x, y, width, height, null);
			currentSprite=bottomleftright;
		} else {
			g.drawImage(all.getBufferedImage(), x, y, width, height, null);
			currentSprite=all;
		}
	}



//	public void renderMap(Graphics g) {
//		// tick++;
//		// if(tick>=60){
//		// tick=0;
//		//
//		double tmp;
//		if ((Game.player.getX() + Game.player.getBreite() / 2) < x) {
//			tmp = (x - Game.player.getX()) / 16;
//			mapX = (Game.player.getX() + 200 + 175) + Utils.toInt(tmp);
//		} else {
//			tmp = (Game.player.getX() - x) / 16;
//			mapX = (Game.player.getX() + 200 + 175) - Utils.toInt(tmp);
//			;
//		}
//		if ((Game.player.getY() + Game.player.getHeight() / 2) < y) {
//			tmp = (y - Game.player.getY()) / 16;
//			mapY = (Game.player.getY() - 430 + 103 + 30) + Utils.toInt(tmp);
//		} else {
//			tmp = (Game.player.getY() - y) / 16;
//			mapY = (Game.player.getY() - 430 + 103 + 30) - Utils.toInt(tmp);
//		}
//		if (Game.map.getBounds().intersects(new Rectangle(mapX, mapY, 2, 2))) {
//			g.drawImage(currentSprite.getBufferedImage(), mapX, mapY, 2, 2, null);
//		}
//
//	}
}
