package Tile;

import java.awt.Graphics;
import Terracraft.Game;
import Terracraft.Id;
import Tile.source.Tile;
import gfx.Sprite;

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

	public void mapRender(Graphics g) {

		renderMap(g, sprite);

	}

}