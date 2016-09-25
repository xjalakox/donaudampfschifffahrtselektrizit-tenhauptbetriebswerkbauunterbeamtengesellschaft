package tile;

import java.awt.*;

import gfx.Sprite;
import terracraft.Game;
import terracraft.Id;
import tile.source.Tile;

public class Tree extends Tile {
	private int level;
	private Sprite tree_middle = new Sprite(Game.sheet_tree, 1, 1, 1, 1, 20, 2);
	private Sprite root_right = new Sprite(Game.sheet_tree, 2, 7, 1, 1, 20, 2);
	private Sprite root_left = new Sprite(Game.sheet_tree, 3, 7, 1, 1, 20, 2);
	private Sprite root_middle = new Sprite(Game.sheet_tree, 5, 7, 1, 1, 20, 2);
	private Sprite branch_right1 = new Sprite(Game.sheet_tree_branches, 2, 1, 1, 1, 40, 2);
	private Sprite branch_right2 = new Sprite(Game.sheet_tree_branches, 2, 2, 1, 1, 40, 2);
	private Sprite branch_right3 = new Sprite(Game.sheet_tree_branches, 2, 3, 1, 1, 40, 2);
	private Sprite branch_left1 = new Sprite(Game.sheet_tree_branches, 1, 1, 1, 1, 40, 2);
	private Sprite branch_left2 = new Sprite(Game.sheet_tree_branches, 1, 2, 1, 1, 40, 2);
	private Sprite branch_left3 = new Sprite(Game.sheet_tree_branches, 1, 3, 1, 1, 40, 2);
	private Sprite top1 = new Sprite(Game.sheet_tree_tops, 1, 1, 1, 1, 80, 2);
	private Sprite top2 = new Sprite(Game.sheet_tree_tops, 2, 1, 1, 1, 80, 2);
	private Sprite top3 = new Sprite(Game.sheet_tree_tops, 3, 1, 1, 1, 80, 2);

	public Tree(int x, int y, int Width, int height, int level, Id id) {
		super(x, y, Width, height, id);
		this.level = level;
		// TODO Auto-generated constructor stub
	}

	public void render(Graphics g) {
		g.drawImage(root_middle.getBufferedImage(), x, y, width, height, null);
		g.drawImage(root_left.getBufferedImage(), x - width + 6, y, width, height, null);
		g.drawImage(root_right.getBufferedImage(), x + width - 6, y, width, height, null);
		for (int i = 1; i <= level; i++) {
			g.drawImage(tree_middle.getBufferedImage(), x, y - height * i, width, height, null);
		}
		g.drawImage(branch_right1.getBufferedImage(), x+width-6, y - height*4, width, height, null);
		g.drawImage(branch_left3.getBufferedImage(), x-width+6, y - height*2, width, height, null);
		g.drawImage(top1.getBufferedImage(), x - width * 2 + 14, y - height * (level) - height * 5, width * 5 - 28,height * 5, null);
	}

	public void tick() {

	}
}
