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
	private Sprite []sprite_moving=new Sprite[13];
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
		for(int i=0;i<6;i++){
			sprite_moving[i+7]=new Sprite(Game.sheet,i,3,1,2);
		}
		for(int i=0;i<7;i++){
			sprite_moving[i]=new Sprite(Game.sheet,9+i,1,1,2);
		}
		sprite=new Sprite(Game.sheet,6,1,1,2);
		if(moving==2||moving==-2){
			g.drawImage(sprite.getBufferedImage(), x,y-32,breite,4*32,null);
		}else{
			g.drawImage(sprite.getBufferedImage(), x+breite,y-32,-breite,4*32,null);
		}

      
	}

	public void tick() {
		x+=velX;
		y+=velY;
		
		for (Tile.source.Tile ti : handler.tile) {
			
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
				x = ti.getX() + 33;
				
			}
			if (getRight().intersects(ti.getLeft())) {
				setVelX(0);
				x = ti.getX() - 64;
			
			}
		}
			
		
		
		
		
		
		
		//Movement
		if (key.d ) {
			moving=1;
			setVelX(4);
		}
		

		if (Key.a ) {
			moving=2;
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