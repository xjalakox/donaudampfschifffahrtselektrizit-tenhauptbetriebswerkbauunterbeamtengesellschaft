package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Element {
	protected int x, y, width, height;

	protected Font small, normal, big, large, extralarge;

	protected Color green,red,blue, orange;

	public Element(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		green = new Color(0, 160, 20, 200);
		red = new Color(200,0,0,200);
		blue = new Color(0,40,240,220);
		orange = new Color(255,165,0,255);
		small = new Font("serif", Font.BOLD, 12);
		normal = new Font("serif", Font.BOLD, 24);
		big = new Font("serif", Font.BOLD, 36);
		large = new Font("serif", Font.BOLD, 48);
		extralarge = new Font("serif", Font.BOLD, 60);
	}

	public abstract void render(Graphics2D g2d);

	public abstract void tick();

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

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle Bounds() {
		return new Rectangle(x, y, width, height);
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
