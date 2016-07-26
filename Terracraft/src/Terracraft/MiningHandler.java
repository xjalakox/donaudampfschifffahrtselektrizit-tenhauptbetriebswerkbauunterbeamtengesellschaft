package Terracraft;

import java.awt.Graphics;
import java.util.ArrayList;
import Input.Mouse;
import Tile.source.Tile;
import gfx.Sprite;

public class MiningHandler {
	
	private ArrayList<Id> scrollbarTiles = new ArrayList<Id>();
	private Sprite[] scrollsprite = new Sprite[10];
	private Sprite scrollspriteaimed = new Sprite(Game.sheet,3,1,1,1);
	private boolean left,right=true;
	private int tick=0;
	
	public void init(){
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Hammer);
		for(int i=0;i<10;i++){
			scrollsprite[i] = new Sprite(Game.sheet,1,1,1,1);
		}
	}
	
	public void render(Graphics g){
		
		for(int i=0;i<10;i++){
			g.drawImage(scrollsprite[i].getBufferedImage(),i*74+20,20,64,64,null);
		}
		if(Mouse.mouseRotation<10&&Mouse.mouseRotation>-1){
			g.drawImage(scrollspriteaimed.getBufferedImage(),Mouse.mouseRotation*74+17,17,70,70,null);
		}
		
		for(int i=0;i<scrollbarTiles.size();i++){
			if(Mouse.mouseRotation!=i){
				g.drawImage(scrollbarTiles.get(i).getImage().getBufferedImage(), (i*74)+34, 36, 32, 32, null);
			}
		}
		
		if(Mouse.mouseRotation<scrollbarTiles.size()&&Mouse.mouseRotation>-1){
			g.drawImage(scrollbarTiles.get(Mouse.mouseRotation).getImage().getBufferedImage(), (Mouse.mouseRotation*74)+34, 33, 35, 35, null);
			if(Game.player.getVelX()>0||right){
				g.drawImage(scrollbarTiles.get(Mouse.mouseRotation).getImage().getBufferedImage(),Game.player.getX()+10,Game.player.getY()+10,32,32,null);
				left=false;
				right=true;
			}
			if(Game.player.getVelX()<0||left){
				g.drawImage(scrollbarTiles.get(Mouse.mouseRotation).getImage().getBufferedImage(),Game.player.getX()+10,Game.player.getY()+10,-32,32,null);
				right=false;
				left=true;
			}
		}
			
		if(Mouse.mouseRotation<0)Mouse.mouseRotation=9;
		if(Mouse.mouseRotation>9)Mouse.mouseRotation=0;
		
	}
	
	public void tick(){
		
		if(Mouse.pressed&&Mouse.mouseRotation<scrollbarTiles.size()&&Mouse.mouseRotation>-1&&tick==10){
			tick=0;
				if(scrollbarTiles.get(Mouse.mouseRotation).getBlock().equalsIgnoreCase(Mouse.degradedTile.getId().toString())){
					Mouse.degradedTile.addDamage(10);
				}else{
					Mouse.degradedTile.addDamage(1);
				}
		}
		
		if(tick<10){
			tick++;
		}
	}
}
