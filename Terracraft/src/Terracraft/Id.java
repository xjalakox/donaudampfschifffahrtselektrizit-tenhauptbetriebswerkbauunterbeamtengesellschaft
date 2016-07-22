package Terracraft;

import Tile.Grass;
import Tile.source.Tile;
import gfx.Sprite;

public enum Id {

	Player,Stone("pickaxe"), Dirt("shovel"), Pickaxe(new Sprite(Game.sheet,2,1,1,1),"stone",4), Grass("shovel");
	
	private String tool,block;
	private int efficiency;
	private Sprite image;
	
	Id(String tool){
		this.tool = tool;
	}
	
	Id(){
	}
	
	Id(Sprite image, String block, int efficiency){
		this.image = image;
		this.block = block;
		this.efficiency = efficiency;
	}
	
	public String getTool(){
		return tool;
	}
	
	public String getBlock(){
		return block;
	}
	
	public int getEfficiency(){
		return efficiency;
	}
	
	public Sprite getImage(){
		return image;
	}
	
	public String toString() {
		switch (this) {
		case Player:
			return null;
		case Grass:
			return "grass";
		}
		return null;
	}

	public static Tile getTile(String tileId, int x, int y) {
		switch (tileId) {
		case "player":
			return null;
		case "grass":
			return new Grass(x, y, 64, 64, Id.Grass);
		}
		return null;
	}

}
