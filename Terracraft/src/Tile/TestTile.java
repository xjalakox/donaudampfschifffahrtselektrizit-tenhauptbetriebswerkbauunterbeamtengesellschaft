package Tile;

import java.awt.Color;
import java.awt.Graphics;

import Terracraft.Handler;
import Terracraft.Id;

public class TestTile extends Tile{

	public TestTile(int x, int y, int breite, int höhe, Handler handler, Id id) {
		super(x, y, breite, höhe, handler, id);
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, breite, höhe);
	}
	
	public void tick() {
		
	}

}
