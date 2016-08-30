package Terracraft;

import java.awt.Rectangle;

import Tile.Grass;
import Tile.Workbench;
import Tile.source.Tile;
import gfx.Sprite;

public enum Id {

	// Block
			Player, NetPlayer, Stone, Dragon,
			Dirt(new Sprite(Game.sheet, 6, 1, 1, 1), "block"),
			Grass(new Sprite(Game.sheet, 13, 1, 1, 1), "block"),  
			Empty(new Sprite(Game.sheet, 14, 1, 1, 1), "empty",0,"empty"),
			Workbench(new Sprite(Game.sheet, 1, 2, 2, 1), "block"),
	
	// Tool
			Pickaxe(new Sprite(Game.sheet, 2, 1, 1, 1), "grass", 4, "tool"),
			Hammer(new Sprite(Game.sheet, 4, 1, 1, 1),"iron", 10, "tool");

	public String tool, block, type;
	private int efficiency, amount;
	private Sprite image;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount += amount;
	}

	Id() {

	}

	Id(Sprite image, String type) {
		this.type = type;
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	Id(Sprite image, String block, int efficiency, String type) {
		this.image = image;
		this.block = block;
		this.efficiency = efficiency;
		this.type = type;
	}

	public String getTool() {
		return tool;
	}

	public String getBlock() {
		return block;
	}

	public int getEfficiency() {
		return efficiency;
	}

	public Sprite getImage() {
		return image;
	}

	public Rectangle getToolBounds(int x, int y) {
		return new Rectangle(x, y, 32, 32);
	}

	public static Id toId(String string) {
		switch (string) {
		case "Grass":
			return Grass;
		case "Pickaxe":
			return Pickaxe;
		case "Hammer":
			return Hammer;
		case "Empty":
			return Empty;
		case "Workbench":
			return Workbench;
		}
		return null;
	}

	public String toString() {
		switch (this) {
		case Player:
			return null;
		case Grass:
			return "Grass";
		case Pickaxe:
			return "Pickaxe";
		case Hammer:
			return "Hammer";
		case Dirt:
			return "Dirt";
		case Empty:
			return "Empty";
		case Workbench:
			return "Workbench";
		default:
			break;
		}
		return null;
	}

	public static Tile getTile(String tileId, int x, int y) {
		switch (tileId) {
		case "Player":
			return null;
		case "Grass":
			return new Grass(x, y, 32, 32, Id.Grass);
		case "Workbench":
			return new Workbench(x, y, 64, 32, Id.Workbench);
		}
		return null;
	}

}
