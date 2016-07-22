package Entity;

import java.awt.Graphics;

import Terracraft.Handler;
import Terracraft.Id;

public class Entity {

	public int x, y, breite, höhe, velX, velY;
	private boolean removed;
	Handler handler;
	Id id;
	
	public Entity(int x, int y, int breite, int höhe, Handler handler, Id id) {
		this.x = x;
		this.y = y;
		this.breite = breite;
		this.höhe = höhe;
		this.handler = handler;
		this.id = id;
	}

	public void render(Graphics g){}
	public void tick(){}
	
	
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

	public int getHöhe() {
		return höhe;
	}

	public void setHöhe(int höhe) {
		this.höhe = höhe;
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
}
