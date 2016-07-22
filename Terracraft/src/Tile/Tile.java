package Tile;

import java.awt.Graphics;
import java.awt.Rectangle;
import Terracraft.Handler;
import Terracraft.Id;

public class Tile {

	public int x, y, width, height, velX, velY;
	Handler handler;
	Id id;
	private int damage =100;

	public Tile(int x, int y, int Width, int height,Id id) {
		this.x = x;
		this.y = y;
		this.width = Width;
		this.height = height;
		this.id = id;
	}

	public  void render(Graphics g){
		
	}

	public  void tick(){
		
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

	public int getheight() {
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
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
}
