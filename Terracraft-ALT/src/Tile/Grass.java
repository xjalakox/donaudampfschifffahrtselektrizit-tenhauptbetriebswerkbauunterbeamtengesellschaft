package Tile;

import java.awt.Color;
import java.awt.Graphics;
import Terracraft.Game;
import Terracraft.Handler;
import Terracraft.Id;
import Tile.source.Tile;
import gfx.Sprite;

public class Grass extends Tile{

	private Sprite sprite = new Sprite(Game.sheet,7,1,1,1);
	private Sprite sprite2 = new Sprite(Game.sheet,6,1,1,1);
	private int tick = 0;
	
	public Grass(int x, int y, int width, int height, Id id) {
		super(x, y, width, height, id);
		setDamage(100);
	}

	public void render(Graphics g) {
		
		if(underground){
			g.drawImage(sprite2.getBufferedImage(),x,y,width,height,null);
		}else{
			g.drawImage(sprite.getBufferedImage(),x,y,width,height,null);
		}


		

	}

	public void tick() {
		if(tick>=60){
			tick = 0;
			checkForBlockonTop();
		}
		tick++;
	}
	
	public void checkForBlockonTop(){
		underground=false;
		for(Tile ti:Game.handler.tile2){
			if(getTileTop().intersects(ti.getBounds())&&ti.getId().equals(Id.Grass)){
				underground=true;
			}
		}
	}
	
	
}