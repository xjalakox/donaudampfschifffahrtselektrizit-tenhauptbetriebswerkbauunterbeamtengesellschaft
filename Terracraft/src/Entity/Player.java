package Entity;

import java.awt.Color;
import java.awt.Graphics;

import Input.Key;

import Terracraft.Game;
import Terracraft.Id;
import gfx.Sprite;


public class Player extends Entity {
	private String username;
	private Key key;
	private Sprite sprite;

	public Player(String username, int x, int y, int breite, int höhe, Id id, Key key ) {
		super(x, y, breite, höhe, Game.handler,id);
		this.key = key;
		this.username = username;
	}
	
	public Player(String username, int x, int y, int breite, int höhe, Id id) {
		super(x, y, breite, höhe, Game.handler,id);
		this.username = username;
	}


	public void render(Graphics g) {
		sprite=new Sprite(Game.sheet,6,1,1,2);
		g.drawImage(sprite.getBufferedImage(), x,y-32,breite,höhe,null);
	}

	public void tick() {
		x+=velX;
		y+=velY;
		for (Tile.source.Tile ti : handler.tile) {
			if (getBounds().intersects(ti.getBounds())) {
				setVelX(0);
				setVelY(0);
				jumping = false;
				falling = true;
				gravity = 0;
			}
			
		}
		
		
		
		
		
		//Movement
		if (key.d ) {
			setVelX(4);
		}
		

		if (Key.a ) {
			setVelX(-4);
		}
		
		if (jumping) {
			jumping(0.5f);
		}
		if (falling) {
			falling();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}