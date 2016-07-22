package Terracraft;

import java.awt.Graphics;
import java.util.ArrayList;

import Tile.source.Tile;
import gfx.Sprite;

public class MiningHandler {
	
	private ArrayList<Tile> scrollbarTiles = new ArrayList<Tile>();
	private Sprite[] scrollsprite = new Sprite[10];
	
	public void render(Graphics g){
		for(int i=0;i<10;i++){
			scrollsprite[i] = new Sprite(Game.sheet,1,1,1,1);
			g.drawImage(scrollsprite[i].getBufferedImage(),i*64,20,63,63,null);
		}
		
		
	}
	
	public void tick(){
		
	}
}
