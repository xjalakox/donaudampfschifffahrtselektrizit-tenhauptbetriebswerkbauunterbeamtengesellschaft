package Terracraft;

import java.awt.Rectangle;

import Tile.Grass;
import Tile.source.Tile;
import gfx.Sprite;

public enum Id {

	//Block
	Player,NetPlayer, Stone, Dirt, Grass,
	
	//Tool
	Pickaxe(new Sprite(Game.sheet,2,1,1,1),"grass",4), Hammer(new Sprite(Game.sheet,4,1,1,1),"iron",10);
	
	public String tool,block;
	private int efficiency;
	private Sprite image;
	
	
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
	
	public Rectangle getToolBounds(int x,int y){
		return new Rectangle(x,y,32,32);
	}
	
	public String toString() {
		switch (this) {
		case Player:
			return null;
		case Grass:
			return "grass";
		case Pickaxe:
			return "Pickaxe";
		case Hammer:
			return "Hammer";
		}
		return null;
	}

	public static Tile getTile(String tileId, int x, int y) {
		switch (tileId) {
		case "player":
			return null;
		case "grass":
			return new Grass(x, y, 32, 32, Id.Grass);
		}
		return null;
	}

}
