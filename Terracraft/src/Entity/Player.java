package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Input.Key;
import Terracraft.Game;
import Terracraft.Id;
import Tile.source.Tile;
import gfx.Sprite2;


public class Player extends Entity {
	private String username;
	private Key key;
	public Id tool;
	public int ToolX,ToolY;
	private Sprite2[] armor=new Sprite2[20];
	private Sprite2[] legs=new Sprite2[21];
	private Sprite2[] head=new Sprite2[21];
	private Sprite2[] body=new Sprite2[21];
	private Sprite2[] armor_head=new Sprite2[21];
	public int spritex,spritey;
	public Player(String username, int x, int y, int breite, int höhe, Id id, Key key ) {
		super(x, y, breite, höhe, Game.handler,id);
		this.key = key;
		this.username = username;
		for(int i =1;i<armor.length;i++){
			armor[i]=new Sprite2(Game.sheet_armor,1,i,1,1);
			legs[i]=new Sprite2(Game.sheet_legs,1,i,1,1);
			head[i]=new Sprite2(Game.sheet_head,1,i,1,1);
			body[i]=new Sprite2(Game.sheet_body,1,i,1,1);
			armor_head[i]=new Sprite2(Game.sheet_armor_head,1,i,1,1);
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
			armor_head[i]=new Sprite2(Game.sheet_armor_head,1,i,1,1);
		}
	}


	public void render(Graphics g) {
			
		
		Zeichnung(g);
			if (tool != null) {
				 g.drawImage(Game.player.tool.getImage().getBufferedImage(), ToolX
				 + 10,ToolY + 10, 62, 62, null);
				 System.out.println("Player: "+Game.player.tool.toString());
			}
			g.setColor(Color.black);
			g.drawRect(x-96,y-96,96*2+breite,96*2+höhe);
	}

	public void tick() {
		x+=velX;
		y+=velY;

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
				x = ti.getX() + 33;
				
			}
			if (getRight().intersects(ti.getLeft())) {
				setVelX(0);
				x = ti.getX() - 46;
			
			}
			
		}
		
		//Movement
		if (key.d ) {
			moving=1;
			setVelX(4);
		}
		

		if (key.a ) {
			moving=2;
			setVelX(-4);
		}
		
		if (jumping) {
			jumping(0.5f);
		}
		if (falling) {
			falling();
		}
		

		framedelay2++;
		if (framedelay2 >= 9) {
			frame2++;
			if (frame2 >= 3) {
				frame2 = 0;
			}
			framedelay2 = 0;
		}
		
		

		framedelay++;
		if (framedelay >= 4) {
			frame++;
			if (frame >= 13) {
				frame = 0;
			}
			framedelay = 0;
		}
	}
	
	
	public void setTool(Id tool,int x,int y){
		this.tool = tool;
		this.ToolX = x;
		this.ToolY = y;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Rectangle getArea(){
		return new Rectangle(x-96,y-96,96*2+breite,96*2+höhe);
	}
	
	
	public void Zeichnung(Graphics g){
		
		if (moving == -1 && !jumping && !falling) {
			g.drawImage(body[1].getBufferedImage(),x-10,y+2,70,96,null);
			g.drawImage(legs[1].getBufferedImage(),x-10,y+2,70,96,null);
			g.drawImage(head[1].getBufferedImage(),x-10,y+2,70,96,null);
			g.drawImage(armor[1].getBufferedImage(),x-10,y+2,70,96,null);
			g.drawImage(armor_head[1].getBufferedImage(),x-10,y+2,70,96,null);
		}
		
		
		if (moving == -2 && !jumping && !falling) {
			g.drawImage(body[1].getBufferedImage(),x+56,y+2,-70,96,null);
			g.drawImage(legs[1].getBufferedImage(),x+56,y+2,-70,96,null);
			g.drawImage(head[1].getBufferedImage(),x+56,y+2,-70,96,null);
			g.drawImage(armor[1].getBufferedImage(),x+56,y+2,-70,96,null);
			g.drawImage(armor_head[1].getBufferedImage(),x+56,y+2,-70,96,null);
		}
        
		
		if (moving == 2 && !jumping && !falling) {
        	g.drawImage(body[frame+7].getBufferedImage(),x+56,y+2,-70,96,null); 	
        	g.drawImage(legs[frame+7].getBufferedImage(),x+56,y+2,-70,96,null);
        	g.drawImage(armor[frame+7].getBufferedImage(),x+56,y+2,-70,96,null);
        	g.drawImage(head[frame+7].getBufferedImage(),x+56,y+2,-70,96,null);
        	g.drawImage(armor_head[frame+7].getBufferedImage(),x+56,y+2,-70,96,null);
        }
        
        
        if (moving ==1 && !jumping && !falling) {
        	g.drawImage(body[frame+7].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(legs[frame+7].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(armor[frame+7].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(head[frame+7].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(armor_head[frame+7].getBufferedImage(),x-10,y+2,70,96,null);
        }
        
        
        if (moving == 1 && jumping && !falling || moving == -1 && jumping && !falling) {
        	g.drawImage(body[1].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(legs[6].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(armor[1].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(head[1].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(armor_head[1].getBufferedImage(),x-10,y+2,70,96,null);
        }
        
        
        if (moving == 1 && !jumping && falling || moving == -1 && !jumping && falling) {
        	g.drawImage(body[6].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(legs[6].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(armor[6].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(head[6].getBufferedImage(),x-10,y+2,70,96,null);
        	g.drawImage(armor_head[6].getBufferedImage(),x-10,y+2,70,96,null);
        }
        
        
        if (moving == 2 && jumping && !falling || moving == -2 && jumping && !falling) {
        	g.drawImage(body[1].getBufferedImage(),x+56,y+2,-70,96,null); 	
        	g.drawImage(legs[6].getBufferedImage(),x+56,y+2,-70,96,null);
        	g.drawImage(armor[1].getBufferedImage(),x+56,y+2,-70,96,null);
        	g.drawImage(head[1].getBufferedImage(),x+56,y+2,-70,96,null);
        	g.drawImage(armor_head[1].getBufferedImage(),x+56,y+2,-70,96,null);
        }
        
        
        if (moving == 2 && !jumping && falling || moving == -2 && !jumping && falling) {
        	g.drawImage(body[6].getBufferedImage(),x+56,y+2,-70,96,null); 	
        	g.drawImage(legs[6].getBufferedImage(),x+56,y+2,-70,96,null);
        	g.drawImage(armor[6].getBufferedImage(),x+56,y+2,-70,96,null);
        	g.drawImage(head[6].getBufferedImage(),x+56,y+2,-70,96,null);
        	g.drawImage(armor_head[6].getBufferedImage(),x+56,y+2,-70,96,null);
        }
        
       if(clicked){
    	   if (moving == 2 || moving == -2 ) {
    		   g.drawImage(armor[2+frame2].getBufferedImage(),x+56,y+2,-70,96,null);
    	   }
    	   if (moving == 1 || moving == -1 ) {
    		   g.drawImage(armor[2+frame2].getBufferedImage(),x-10,y+2,70,96,null);
    	   }
       }
    }

}