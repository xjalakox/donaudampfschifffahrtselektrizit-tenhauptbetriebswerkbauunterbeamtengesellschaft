package Terracraft;

import java.awt.Graphics;
import java.util.ArrayList;
import Input.Mouse;
import gfx.Sprite;

public class MiningHandler {
	
	private ArrayList<Id> scrollbarTiles = new ArrayList<Id>();
	private Sprite[] scrollsprite = new Sprite[10];
	private boolean aimed;
	
	public void init(){
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Pickaxe);
		for(int i=0;i<10;i++){
			scrollsprite[i] = new Sprite(Game.sheet,1,1,1,1);
		}
	}
	
	public void render(Graphics g){
		for(int i=0;i<10;i++){
			if(Mouse.mouseRotation==i){
				g.drawImage(scrollsprite[i].getBufferedImage(),i*74+17,17,70,70,null);
			}else{
				g.drawImage(scrollsprite[i].getBufferedImage(),i*74+20,20,64,64,null);
			}
		}
			
		for(int i=0;i<scrollbarTiles.size();i++){
				if(Mouse.mouseRotation==i){
					aimed = true;
				}else{
					aimed = false;
				}
			if(!aimed){
				g.drawImage(scrollbarTiles.get(i).getImage().getBufferedImage(), (i*74)+34, 36, 32, 32, null);
			}else{
				g.drawImage(scrollbarTiles.get(i).getImage().getBufferedImage(), (i*74)+34, 33, 35, 35, null);
			}
		}
		
		if(Mouse.mouseRotation<0)Mouse.mouseRotation=9;
		if(Mouse.mouseRotation>9)Mouse.mouseRotation=0;
	}
	
	public void tick(){
		
	}
}
