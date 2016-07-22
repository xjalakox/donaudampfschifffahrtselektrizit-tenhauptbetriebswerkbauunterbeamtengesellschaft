package Tile;

import java.awt.Color;
import java.awt.Graphics;
import Terracraft.Game;
import Terracraft.Handler;
import Terracraft.Id;
import Tile.source.Tile;
import gfx.Sprite;

public class Grass extends Tile{

	public Grass(int x, int y, int width, int height, Id id) {
		super(x, y, width, height, id);
	}
	
	private Sprite sprite = new Sprite(Game.sheet,1,1,1,1);

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.drawRect(x,y,width,height);
		if(Game.m.Collision().intersects(getBounds())){
			g.drawImage(sprite.getBufferedImage(),x,y,32,32,null);
		}
	}

	public void tick() {
		
	}

}