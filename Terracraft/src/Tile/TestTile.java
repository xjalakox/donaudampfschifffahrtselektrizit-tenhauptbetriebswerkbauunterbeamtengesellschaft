package Tile;

import java.awt.Color;
import java.awt.Graphics;

import Terracraft.Handler;
import Terracraft.Id;
import Tile.source.Tile;

public class TestTile extends Tile{

	public TestTile(int x, int y, int width, int height, Id id) {
		super(x, y, width, height, id);
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}
	
	public void tick() {
		
	}

}
