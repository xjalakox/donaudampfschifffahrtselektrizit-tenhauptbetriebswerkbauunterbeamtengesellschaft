package tile;

import java.awt.Graphics;

import gfx.Sprite;
import terracraft.Game;
import terracraft.Id;
import tile.source.Tile;

public class Stone extends Tile {

	private Sprite sprite = new Sprite(Game.sheet_stone, 2, 2, 1, 1, 16, 2);
	private int tick = 0;

	private Sprite top = new Sprite(Game.sheet_stone, 7, 4, 1, 1, 16, 2);
	private Sprite bottom = new Sprite(Game.sheet_stone, 7, 1, 1, 1, 16, 2);
	private Sprite left = new Sprite(Game.sheet_stone, 13, 1, 1, 1, 16, 2);
	private Sprite right = new Sprite(Game.sheet_stone, 10, 2, 1, 1, 16, 2);
	private Sprite alone = new Sprite(Game.sheet_stone, 12, 4, 1, 1, 16, 2);
	private Sprite all = new Sprite(Game.sheet_stone, 2, 2, 1, 1, 16, 2);
	private Sprite leftright = new Sprite(Game.sheet_stone, 7, 5, 1, 1, 16, 2);
	private Sprite bottomtop = new Sprite(Game.sheet_stone, 6, 1, 1, 1, 16, 2);
	private Sprite bottomleft = new Sprite(Game.sheet_stone, 2, 4, 1, 1, 16, 2);
	private Sprite bottomright = new Sprite(Game.sheet_stone, 3, 4, 1, 1, 16, 2);
	private Sprite topright = new Sprite(Game.sheet_stone, 3, 5, 1, 1, 16, 2);
	private Sprite topleft = new Sprite(Game.sheet_stone, 2, 5, 1, 1, 16, 2);
	private Sprite bottomrighttop = new Sprite(Game.sheet_stone, 1, 1, 1, 1, 16, 2);
	private Sprite bottomlefttop = new Sprite(Game.sheet_stone, 5, 1, 1, 1, 16, 2);
	private Sprite bottomleftright = new Sprite(Game.sheet_stone, 2, 1, 1, 1, 16, 2);
	private Sprite topleftright = new Sprite(Game.sheet_stone, 2, 3, 1, 1, 16, 2);

	public Stone(int x, int y, int width, int height, Id id) {
		super(x, y, width, height, id);
		setHealth(300);
	}

	public void render(Graphics g) {

		drawTile(g, x, y, width, height, alone, left, right, top, bottom, bottomright, bottomleft, bottomtop, leftright,
				topleft, topright, all, bottomrighttop, bottomlefttop, topleftright, bottomleftright);
	}

	public void tick() {
		tick++;
		if (tick >= 60) {

			tick = 0;
			checkForBlockonTop();
			checkForBlockonBottom();
			checkForBlockonLeft();
			checkForBlockonRight();
		}
	}

}