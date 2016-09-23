package tile;

import java.awt.Color;
import java.awt.Graphics;

import gfx.Sprite;
import terracraft.Game;
import terracraft.Id;
import tile.source.Tile;

public class Grass extends Tile {

	private Sprite sprite = new Sprite(Game.sheet, 7, 1, 1, 1);
	private Sprite sprite2 = new Sprite(Game.sheet, 6, 1, 1, 1);
	private int tick = 0;

	public Grass(int x, int y, int width, int height, Id id) {
		super(x, y, width, height, id);
		setHealth(100);
	}

	public void render(Graphics g) {

		if (underground) {
			g.drawImage(sprite2.getBufferedImage(), x, y, width, height, null);
			renderMap(g, sprite2);
		} else {
			g.drawImage(sprite.getBufferedImage(), x, y, width, height, null);
			renderMap(g, sprite);

		}
		
	/*	g.setColor(Color.WHITE);
		g.drawRect(getBottom().x,getBottom().y,getBottom().width,getBottom().height);
		g.setColor(Color.BLUE);
		g.drawRect(getTop().x,getTop().y,getTop().width,getTop().height);
		g.setColor(Color.RED);
		g.drawRect(getLeft().x,getLeft().y,getLeft().width,getLeft().height);
		g.setColor(Color.YELLOW);
		g.drawRect(getRight().x,getRight().y,getRight().width,getRight().height);
*/
	}

	public void tick() {
		tick++;
		if (tick >= 60) {

			tick = 0;
			checkForBlockonTop();
		}
	}

	public void checkForBlockonTop() {
		underground = false;
		for (Tile ti : Game.handler.tile2) {
			if (getTileTop().intersects(ti.getBounds()) && ti.getId().equals(Id.Grass)) {
				underground = true;
			}

		}
	}

	public void mapRender(Graphics g) {

		if (underground) {
			renderMap(g, sprite2);
		} else {
			renderMap(g, sprite);
		}
	}
}