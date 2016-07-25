package Entity;

import java.awt.Graphics;

import Input.Key;

import Terracraft.Game;
import Terracraft.Id;
import Tile.source.Tile;
import gfx.Sprite;
import gfx.Sprite2;


public class Player extends Entity {
	private String username;
	private Key key;
	private Sprite sprite;

	private Sprite2[] armor=new Sprite2[18];

	private Sprite []sprite_moving=new Sprite[13];
	public Player(String username, int x, int y, int breite, int höhe, Id id, Key key ) {
		super(x, y, breite, höhe, Game.handler,id);
		this.key = key;
		this.username = username;
		sprite_moving[0]=new Sprite(Game.sheet,9,1,1,2);
		sprite_moving[1]=new Sprite(Game.sheet,10,1,1,2);
		sprite_moving[2]=new Sprite(Game.sheet,11,1,1,2);
		sprite_moving[3]=new Sprite(Game.sheet,12,1,1,2);
		sprite_moving[4]=new Sprite(Game.sheet,13,1,1,2);
		sprite_moving[5]=new Sprite(Game.sheet,14,1,1,2);
		sprite_moving[6]=new Sprite(Game.sheet,15,1,1,2);
		sprite_moving[7]=new Sprite(Game.sheet,1,3,1,2);
		sprite_moving[8]=new Sprite(Game.sheet,2,3,1,2);
		sprite_moving[9]=new Sprite(Game.sheet,3,3,1,2);
		sprite_moving[10]=new Sprite(Game.sheet,4,3,1,2);
		sprite_moving[11]=new Sprite(Game.sheet,5,3,1,2);
		sprite_moving[12]=new Sprite(Game.sheet,6,3,1,2);
		sprite=new Sprite(Game.sheet,6,1,1,2);
		for(int i =1;i<armor.length;i++){
			armor[i]=new Sprite2(Game.armor,1,i,1,1);
		}
	}
	
	public Player(String username, int x, int y, int breite, int höhe, Id id) {
		super(x, y, breite, höhe, Game.handler,id);
		this.username = username;
		sprite_moving[0]=new Sprite(Game.sheet,9,1,1,2);
		sprite_moving[1]=new Sprite(Game.sheet,10,1,1,2);
		sprite_moving[2]=new Sprite(Game.sheet,11,1,1,2);
		sprite_moving[3]=new Sprite(Game.sheet,12,1,1,2);
		sprite_moving[4]=new Sprite(Game.sheet,13,1,1,2);
		sprite_moving[5]=new Sprite(Game.sheet,14,1,1,2);
		sprite_moving[6]=new Sprite(Game.sheet,15,1,1,2);
		sprite_moving[7]=new Sprite(Game.sheet,1,3,1,2);
		sprite_moving[8]=new Sprite(Game.sheet,2,3,1,2);
		sprite_moving[9]=new Sprite(Game.sheet,3,3,1,2);
		sprite_moving[10]=new Sprite(Game.sheet,4,3,1,2);
		sprite_moving[11]=new Sprite(Game.sheet,5,3,1,2);
		sprite_moving[12]=new Sprite(Game.sheet,6,3,1,2);
		sprite=new Sprite(Game.sheet,6,1,1,2);
		for(int i =1;i<armor.length;i++){
			armor[i]=new Sprite2(Game.armor,1,i,1,1);
		}
	}


	public void render(Graphics g) {

		
			
			for(int i =1;i<armor.length;i++){
				armor[i]=new Sprite2(Game.armor,1,i,1,1);
				
			}
			

			


		
		Zeichnung(g);
		
	}

	public void tick() {
		x+=velX;
		y+=velY;
		
		for (Tile ti : handler.tile) {
			
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
		if (Key.d ) {
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
		
		framedelay++;
		if (framedelay >= 2) {
			frame++;
			if (frame >= 13) {
				frame = 0;
			}
			framedelay = 0;
		}
	
		
		

		framedelay2++;
		if (framedelay2 >= 4) {
			frame2++;
			if (frame2 >= 11) {
				frame2 = 0;
			}
			framedelay2 = 0;
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
	
	
	
	public void Zeichnung(Graphics g){
		if (moving == -1 && !jumping && !falling) {g.drawImage(sprite_moving[5].getBufferedImage(),  x+breite,y-32,-breite,4*32,null);g.drawImage(armor[17].getBufferedImage(), x-24,y+20,30*3,29*2,null);}
		if (moving == -2 && !jumping && !falling) {g.drawImage(sprite_moving[5].getBufferedImage(), x,y-32,breite,4*32,null);g.drawImage(armor[17].getBufferedImage(), x-24+60+53,y+20,-30*3,29*2,null);}
        if (moving == 2 && !jumping && !falling) {g.drawImage(sprite_moving[frame].getBufferedImage(), x,y-32,breite,4*32,null);
        g.drawImage(armor[frame2+7].getBufferedImage(), x-24+60+53,y+20,-30*2,29*3,null);}
      if (moving ==1 && !jumping && !falling) {g.drawImage(sprite_moving[frame].getBufferedImage(),  x+breite,y-32,-breite,4*32,null);
      g.drawImage(armor[frame2+7].getBufferedImage(), x-24,y+20,30*2,29*3,null);}
       
    }

}