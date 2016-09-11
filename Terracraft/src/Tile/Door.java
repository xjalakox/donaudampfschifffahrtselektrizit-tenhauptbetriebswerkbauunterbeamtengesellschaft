package Tile;

import java.awt.Graphics;

import Terracraft.Game;
import Terracraft.Id;
import Tile.source.Tile;
import gfx.Sprite;

public class Door extends Tile {
	private Sprite sprite = new Sprite(Game.sheet, 6, 2, 1, 3);

	public void render(Graphics g) {
		g.drawImage(sprite.getBufferedImage(), x, y, width, height, null);
		//setHealth(100);
	}

	public void tick() {

	}

	public void mapRender(Graphics g) {

		renderMap(g, sprite);

	}

	public Door(int x, int y, int Width, int height, Id id) {
		super(x, y, Width, height, id);
	}

}
