package Entity;

import java.awt.Graphics;

import Input.Key;

import Terracraft.Game;
import Terracraft.Id;
import Tile.source.Tile;
import gfx.Sprite;
import gfx.Sprite2;
import gfx.Sprite2;


public class Player extends Entity {
	private String username;
	private Key key;

	private Sprite2[] armor=new Sprite2[20];
	private Sprite2[] legs=new Sprite2[20];
	private Sprite2[] head=new Sprite2[20];
	private Sprite2[] body=new Sprite2[20];
	private Sprite2[] hair=new Sprite2[20];
	public Player(String username, int x, int y, int breite, int höhe, Id id, Key key ) {
		super(x, y, breite, höhe, Game.handler,id);
		this.key = key;
		this.username = username;
		for(int i =1;i<armor.length;i++){
			armor[i]=new Sprite2(Game.sheet_armor,1,i,1,1);
			legs[i]=new Sprite2(Game.sheet_legs,1,i,1,1);
			head[i]=new Sprite2(Game.sheet_head,1,i,1,1);
			body[i]=new Sprite2(Game.sheet_body,1,i,1,1);
			hair[i]=new Sprite2(Game.sheet_hair,1,1,1,1);
		}
		
	}
	
	public Player(String username, int x, int y, int breite, int höhe, Id id) {
		super(x, y, breite, höhe, Game.handler,id);
		this.username = username;
		for(int i =1;i<armor.length;i++){
			armor[i]=new Sprite2(Game.sheet_armor,1,i,1,1);
			legs[i]=new Sprite2(Game.sheet_legs,1,i,1,1);
			head[i]=new Sprite2(Game.sheet_head,1,i,1,1);
			body[i]=new Sprite2(Game.sheet_body,1,i,1,1);
			hair[i]=new Sprite2(Game.sheet_hair,1,1,1,1);
		}
		
	}


	public void render(Graphics g) {

		
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
			if (frame2 >= 13) {
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
		if (moving == -1 && !jumping && !falling) {
			g.drawImage(body[17].getBufferedImage(), x-24,y+20,30*3,29*2,null);
			g.drawImage(legs[17].getBufferedImage(), x-24,y+20,30*3,29*2,null);
			g.drawImage(head[17].getBufferedImage(), x-24,y+20,30*3,29*2,null);
			g.drawImage(armor[17].getBufferedImage(), x-24,y+20,30*3,29*2,null);
		}
		
		
		if (moving == -2 && !jumping && !falling) {
			g.drawImage(body[17].getBufferedImage(), x-24+60+53,y+20,-30*3,29*2,null);
			g.drawImage(legs[17].getBufferedImage(), x-24+60+53,y+20,-30*3,29*2,null);
			g.drawImage(head[17].getBufferedImage(), x-24+60+53,y+20,-30*3,29*2,null);
			g.drawImage(armor[17].getBufferedImage(), x-24+60+53,y+20,-30*3,29*2,null);
		}
        
		
		if (moving == 2 && !jumping && !falling) {
        	g.drawImage(body[frame2+7].getBufferedImage(), x-24+60+53,y+20,-30*2,29*3,null); 	
        	g.drawImage(legs[frame2+7].getBufferedImage(), x-24+60+53,y+20,-30*2,29*3,null);
        	g.drawImage(armor[frame2+7].getBufferedImage(), x-24+60+53,y+20,-30*2,29*3,null);
        	g.drawImage(head[frame2+7].getBufferedImage(), x-24+60+53,y+20,-30*2,29*3,null);
        	g.drawImage(hair[frame2+7].getBufferedImage(), x-24+60+53,y+20,-30*2,29*3,null);
        }
        
        
        if (moving ==1 && !jumping && !falling) {
        	g.drawImage(body[frame2+7].getBufferedImage(), x-24,y+20,30*2,29*3,null);
        	g.drawImage(legs[frame2+7].getBufferedImage(), x-24,y+20,30*2,29*3,null);
        	g.drawImage(armor[frame2+7].getBufferedImage(), x-24,y+20,30*2,29*3,null);
        	g.drawImage(head[frame2+7].getBufferedImage(), x-24,y+20,30*2,29*3,null);
        	g.drawImage(hair[frame2+7].getBufferedImage(), x-24,y+20,30*2,29*3,null);
        }
       
    }

}