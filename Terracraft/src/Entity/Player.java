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
		g.drawImage(sprite.getBufferedImage(), x,y-32,breite,4*32,null);
		 g.setColor(Color.white);
       g.drawRect(getX(), getY(),breite,höhe);
       g.setColor(Color.red);
       g.drawRect(getX()+5, getY()+höhe-5,54,5);
       g.setColor(Color.green);
       g.drawRect(getX()+breite-5, getY()+5,5, höhe-10);
       g.setColor(Color.CYAN);
       g.drawRect(getX(), getY()+5,5, höhe-10);
       g.setColor(Color.MAGENTA);
       g.drawRect(getX()+5, getY(),54,5);
      
	}

	public void tick() {
		x+=velX;
		y+=velY;
		for (Tile.source.Tile ti : handler.tile2) {
			
			if (getTop().intersects(ti.getBottom())) {
				setVelY(0);
				y = ti.getY() + 33;
				jumping = false;
				falling = true;
				gravity = 0;
			
			}
			if (getBottom().intersects(ti.getTop())) {
				setVelY(0);
				y = ti.getY() -höhe-10;
				
				if (falling) {
					falling = false;
					
					
				}
				
			} else if (!jumping) {
				falling = true;
				
			}

			if (getLeft().intersects(ti.getRight())) {
				setVelX(0);
				x = ti.getX() + 33;
				
			}
			if (getRight().intersects(ti.getLeft())) {
				setVelX(0);
				x = ti.getX() - 60;
			
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