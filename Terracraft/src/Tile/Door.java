package tile;

import java.awt.Color;
import java.awt.Graphics;

import gfx.Sprite;
import terracraft.Game;
import terracraft.Id;
import tile.source.Tile;

public class Door extends Tile {
	private Sprite sprite = new Sprite(Game.sheet, 6, 2, 1, 3);
	private Sprite sprite2 = new Sprite(Game.sheet, 7, 2, 2, 3);
	private boolean open;

	public Door(int x, int y, int Width, int height, Id id) {
		super(x, y, Width, height, id);
	}

	public void render(Graphics g) {
		// setHealth(100);
		g.setColor(Color.red);
		g.drawRect(x, y, width, height);

		if (!open) {
			width=32;
			g.drawImage(sprite.getBufferedImage(), x, y, sprite.getWidth(), sprite.getHeight(), null);
		} else {
			width=64;
			g.drawImage(sprite2.getBufferedImage(), x, y, sprite2.getWidth(), sprite2.getHeight(), null);

		}
	}

	public void tick() {

	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}


	public void changeState() {
		if (open) {
			Game.sm.playSound(1);
			open = false;
		} else {
			Game.sm.playSound(0);
			open = true;
		}
	}

}
