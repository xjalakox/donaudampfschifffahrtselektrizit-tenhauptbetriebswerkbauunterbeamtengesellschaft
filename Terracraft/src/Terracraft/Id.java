package terracraft;

import java.awt.Rectangle;

import crafting.Recipe;
import gfx.Sprite;
import tile.*;
import tile.source.Tile;

public enum Id {

	// Block
	Player, NetPlayer, Dragon, Dirt(new Sprite(Game.sheet, 13, 1, 1, 1), "block",Recipe.Test), Grass(
			new Sprite(Game.sheet, 7, 1, 1, 1), "block", Recipe.Test), Empty(new Sprite(Game.sheet, 14, 1, 1, 1),
					"empty", 0,
					"empty"), Workbench(new Sprite(Game.sheet, 1, 2, 2, 1), "block", Recipe.Workbench), Door(
							new Sprite(Game.sheet, 9, 2, 1, 1),
							"block"), Tree(new Sprite(Game.sheet, 8, 1, 1, 1), "Tree", 0, "block"), Stone(
									new Sprite(Game.sheet_stone, 2, 2, 1, 1, 16, 2),
									"block"), Platinum(new Sprite(Game.sheet_platinum, 2, 2, 1, 1, 16, 2), "block"), Gold(
											new Sprite(Game.sheet_gold, 2, 2, 1, 1, 16, 2),
											 "block"), Copper(new Sprite(Game.sheet_copper, 2, 2, 1, 1, 16, 2), "block"),
	Chest(new Sprite(Game.sheet, 13, 2, 1, 1), "block"),


	// Tool
	Pickaxe(new Sprite(Game.sheet, 2, 1, 1, 1), "grass", 50, "tool"), Hammer(new Sprite(Game.sheet, 4, 1, 1, 1), "iron",
			10, "tool"), OP_Pickaxe(new Sprite(Game.sheet, 10, 2, 1, 1), "grass", 1000000, "tool"),

	// Item
	Wood(new Sprite(Game.sheet, 11, 2, 1, 1), "item");

	
	
	public String tool, block, type;
	private int efficiency, amount;
	private Sprite image;
	private Recipe recipe;

	Id() {

	}

	Id(Recipe recipe) {
		this.recipe = recipe;
	}

	Id(Sprite image, String type) {
		this.type = type;
		this.image = image;
	}

	Id(Sprite image, String type, Recipe recipe) {
		this.type = type;
		this.image = image;
		this.recipe = recipe;
	}

	Id(Sprite image, String block, int efficiency, String type) {
		this.image = image;
		this.block = block;
		this.efficiency = efficiency;
		this.type = type;
	}

	Id(Sprite image, String block, int efficiency, String type, Recipe recipe) {
		this.image = image;
		this.block = block;
		this.efficiency = efficiency;
		this.type = type;
		this.recipe = recipe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount += amount;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public static Id toId(String string) {
		switch (string) {
		case "Grass":
			return Grass;
		case "Pickaxe":
			return Pickaxe;
		case "OP_Pickaxe":
			return OP_Pickaxe;
		case "Hammer":
			return Hammer;
		case "Empty":
			return Empty;
		case "Workbench":
			return Workbench;
		case "Door":
			return Door;
		case "Tree":
			return Tree;
		case "Wood":
			return Wood;
		case "Stone":
			return Stone;
		case "Copper":
			return Copper;
		case "Gold":
			return Gold;
		case "Platinum":
			return Platinum;
		case "Chest":
			return Chest;
		case "Dirt":
			return Dirt;
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
		case OP_Pickaxe:
			return "OP_Pickaxe";
		case Hammer:
			return "Hammer";
		case Empty:
			return "Empty";
		case Workbench:
			return "Workbench";
		case Door:
			return "Door";
		case Tree:
			return "Tree";
		case Wood:
			return "Wood";
		case Stone:
			return "Stone";
		case Copper:
			return "Copper";
		case Gold:
			return "Gold";
		case Platinum:
			return "Platinum";
		case Chest:
			return "Chest";
		case Dirt:
			return "Dirt";
		default:
			break;
		}
		return null;
	}

	public static Tile getTile(String tileId) {
		switch (tileId) {
		case "Player":
			return null;
		case "Grass":
			return new Grass(0, 0, 32, 32, Id.Grass);
		case "Dirt":
			return new Dirt(0,0,32,32,Id.Dirt);
		case "Workbench":
			return new Workbench(0, 0, 64, 32, Id.Workbench);
		case "Door":
			return new Door(0, 0, 32, 96, Id.Door);
		case "Tree":
			return new Tree(0, 0, 32, 32, Id.Tree);
		case "Stone":
			return new Stone(0, 0, 32, 32, Id.Stone);
		case "Copper":
			return new Copper(0, 0, 32, 32, Id.Copper);
		case "Gold":
			return new Gold(0, 0, 32, 32, Id.Gold);
		case "Platinum":
			return new Platinum(0, 0, 32, 32, Id.Platinum);
		case "Chest":
			return new Chest(0,0,32,32,Id.Chest);
		}
		return null;
	}

	
}
