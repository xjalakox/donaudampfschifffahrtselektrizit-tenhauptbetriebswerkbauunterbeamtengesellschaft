package Tile;

import java.awt.Color;
import java.awt.Graphics;

import Terracraft.Game;
import Terracraft.Handler;
import Terracraft.Id;

public class Grass extends Tile{

	public Grass(int x, int y, int width, int height, Handler handler, Id id) {
		super(x, y, width, height, handler, id);
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.drawRect(x,y,width,height);
		if(Game.m.Collision().intersects(getBounds())){
			g.drawString(Integer.toString(getDamage()), x-10, y);
		}
	}

	public void tick() {
		
	}

}
