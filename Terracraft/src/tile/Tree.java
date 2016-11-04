package tile;

import java.awt.*;

import gfx.Sprite;
import terracraft.Game;
import terracraft.Id;
import terracraft.Utils;
import tile.source.Tile;

public class Tree extends Tile {
	private int level, level_right, level_left;
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
	private Sprite branch_left, branch_right, top;

	public Tree(int x, int y, int Width, int height, Id id) {
		super(x, y, Width, height, id);
		generateBranchRight();
		generateBranchLeft();
		generateTop();
		generateHeight();
		level_right = Utils.RandomInt(2, level);
		level_left = Utils.RandomInt(2, level);
		setHealth(400);
	}

	public void render(Graphics2D g) {
		g.drawImage(root_middle.getBufferedImage(), x, y, 32, 32, null);
		g.drawImage(root_left.getBufferedImage(), x - 32 + 6, y, 32, 32, null);
		g.drawImage(root_right.getBufferedImage(), x + 32 - 6, y, 32, 32, null);
		for (int i = 1; i <= level; i++) {
			g.drawImage(tree_middle.getBufferedImage(), x, y - 32 * i, 32, 32, null);
		}
		g.drawImage(branch_right.getBufferedImage(), x + 32 - 6, y - 32 * level_right, 32, 32, null);
		g.drawImage(branch_left.getBufferedImage(), x - 32 + 6, y - 32 * level_left, 32, 32, null);
		g.drawImage(top.getBufferedImage(), x - 32 * 2 + 14, y - 32 * (level) - 32 * 5, 32 * 5 - 28, 32 * 5, null);
	}

	public void tick() {

	}

	public void generateBranchRight() {
		int i = Utils.RandomInt(1, 3);
		switch (i) {
		case 1:
			branch_right = branch_right1;
			return;
		case 2:
			branch_right = branch_right2;
			return;
		case 3:
			branch_right = branch_right3;
			return;
		}
	}

	public void generateBranchLeft() {
		int i = Utils.RandomInt(1, 3);
		switch (i) {
		case 1:
			branch_left = branch_left1;
			return;
		case 2:
			branch_left = branch_left2;
			return;
		case 3:
			branch_left = branch_left3;
			return;
		}
	}

	public void generateTop() {
		int i = Utils.RandomInt(1, 3);
		switch (i) {
		case 1:
			top = top1;
			return;
		case 2:
			top = top2;
			return;
		case 3:
			top = top3;
			return;
		}

	}

	public void generateHeight() {
		int i = Utils.RandomInt(4, 6);
		level = i;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y - (level + 5) * 32, width, height + (level + 5) * 32);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle(x + 200, y - 430, 9, 10);
	}

	public Rectangle getRight() {
		return new Rectangle(x + width - 5, y - (level + 5) * 32 + 5, 5, height + (level + 5) * 32 - 10);
	}

	public Rectangle getLeft() {
		return new Rectangle(x, y - (level + 5) * 32 + 5, 5, height + (level + 5) * 32 - 10);
	}

	public Rectangle getBottom() {
		return new Rectangle(x, y - (level + 5) * 32 + height + (level + 5) * 32 - 8, width, 8);
	}

	public Rectangle getTop() {
		return new Rectangle(x + 2, y - (level + 5) * 32, width - 4, 8);
	}

}
