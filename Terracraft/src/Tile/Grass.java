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

	public void render(Graphics g) {
		showDamage(g);
		g.setColor(Color.green);
		g.drawRect(x,y,width,height);
	}

	public void tick() {
		
	}

}