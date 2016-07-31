package Terracraft;

import java.awt.Graphics;
import java.util.ArrayList;

import Input.Mouse;
import Tile.source.Tile;
import gfx.Sprite;
import network.packets.Packet10RemoveTile;

public class MiningHandler {

	private ArrayList<Id> scrollbarTiles = new ArrayList<Id>();
	private Sprite[] scrollsprite = new Sprite[10];
	private Sprite scrollspriteaimed = new Sprite(Game.sheet, 3, 1, 1, 1);
	private boolean left, right = true;
	private int tick;
	public static Id equippedTool;

	public void init() {
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Hammer);
		for (int i = 0; i < 10; i++) {
			scrollsprite[i] = new Sprite(Game.sheet, 1, 1, 1, 1);
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < 10; i++) {
				g.drawImage(scrollsprite[i].getBufferedImage(), i * 74 + 20 + Game.player.getX()-650, Game.player.getY()-350, 64, 64, null);
		}
		
		if(Mouse.mouseRotation<10&&Mouse.mouseRotation>-1){
			g.drawImage(scrollspriteaimed.getBufferedImage(), Mouse.mouseRotation * 74 + Game.player.getX()-650 + 17, Game.player.getY()-353, 70, 70, null);
		}
			
		for (int i = 0; i < scrollbarTiles.size(); i++) {
			if (Mouse.mouseRotation==i) {
				g.drawImage(scrollbarTiles.get(i).getImage().getBufferedImage(), (i * 74) + 34 + Game.player.getX()-650, Game.player.getY()-338, 36, 36, null);
				equippedTool = scrollbarTiles.get(i);
			} else {
				g.drawImage(scrollbarTiles.get(i).getImage().getBufferedImage(), (i * 74) + 34 + Game.player.getX()-650, Game.player.getY()-338, 36, 36, null);
			}
		}
		
//		if(Mouse.mouseRotation<scrollbarTiles.size()&&Mouse.mouseRotation>-1){
//			if (Game.player.getVelX() > 0 || right) {
//				g.drawImage(scrollbarTiles.get(Mouse.mouseRotation).getImage().getBufferedImage(), Game.player.getX() + 10,
//						Game.player.getY() + 10, 32, 32, null);
//				left = false;
//				right = true;
//			}
//			if (Game.player.getVelX() < 0 || left) {
//				g.drawImage(scrollbarTiles.get(Mouse.mouseRotation).getImage().getBufferedImage(), Game.player.getX() + 10,
//						Game.player.getY() + 10, -32, 32, null);
//				right = false;
//				left = true;
//			}
//		}

		if (Mouse.mouseRotation < 0)
			Mouse.mouseRotation = 9;
		if (Mouse.mouseRotation > 9)
			Mouse.mouseRotation = 0;

	}

	public void tick(){
		
		if(Mouse.pressed&&Mouse.mouseRotation<scrollbarTiles.size()&&Mouse.mouseRotation>-1&&tick==10&&Mouse.degradedTile.getBounds().intersects(Game.player.getArea())){
			tick=0;
				if(scrollbarTiles.get(Mouse.mouseRotation).getBlock().equalsIgnoreCase(Mouse.degradedTile.getId().toString())){
					Mouse.degradedTile.addDamage(10);
				}else{
					Mouse.degradedTile.addDamage(1);

				}
		}
		if(Mouse.degradedTile!=null){
			if(Mouse.degradedTile.getDamage()<=0){
				for(Tile ti:Game.handler.tile){
					if(ti==Mouse.degradedTile){
						new Packet10RemoveTile(ti.getX(), ti.getY()).send(Game.client);
						ti.setAsRemoved();
					}
				}
			}
		}
		
		if(tick<10){
			tick++;
		}
	}
}
