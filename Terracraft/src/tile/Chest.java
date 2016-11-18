package tile;

import java.awt.Graphics;
import java.awt.Graphics2D;

import gfx.Sprite;
import terracraft.Game;
import terracraft.Id;
import tile.source.Tile;

public class Chest extends Tile{
	private boolean open;
	public Id[] inventory=new Id[40];
	public int[] inventory_amount=new int[40];
	private Sprite topleft=new Sprite(Game.sheet_chest,1,1,1,1,16,2);
	private Sprite topright=new Sprite(Game.sheet_chest,2,1,1,1,16,2);
	private Sprite bottomleft=new Sprite(Game.sheet_chest,1,2,1,1,16,18,2);
	private Sprite bottomright=new Sprite(Game.sheet_chest,2,2,1,1,16,18,2);
	
	
	public Chest(int x, int y, int Width, int height, Id id) {
		super(x, y, Width, height, id);
		setHealth(150);
		
	}
	
	public void render(Graphics2D g){
		g.drawImage(topleft.getBufferedImage(),x,y,width/2,height/2,null);
		g.drawImage(topright.getBufferedImage(),x+width/2,y,width/2,height/2,null);
		g.drawImage(bottomleft.getBufferedImage(),x,y+height/2,width/2,height/2,null);
		g.drawImage(bottomright.getBufferedImage(),x+width/2,y+height/2,width/2,height/2,null);
	}
	
	
	public void openChest(){
		
	}
	
	public void renderOpen(Graphics g){
		
	}
	
	
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	public void changeOpen(){
		if(open){
			open=false;
		}else{
			open=true;
		}
	}
}

