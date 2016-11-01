package tile;

import java.awt.Graphics;

import gfx.Sprite;
import terracraft.Game;
import terracraft.Id;
import tile.source.Tile;

public class Workbench extends Tile {

	private Sprite sprite = new Sprite(Game.sheet, 1, 2, 2, 1);

	public Workbench(int x, int y, int width, int height, Id id) {
		super(x, y, width, height, id);
		setHealth(100);
	}

	public void render(Graphics g) {
		g.drawImage(sprite.getBufferedImage(), x, y, width, height, null);

	}

	public void tick() {

	}

	

}
































































































































