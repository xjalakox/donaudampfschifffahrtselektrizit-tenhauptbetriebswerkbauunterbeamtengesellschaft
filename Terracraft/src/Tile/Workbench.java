package Tile;

import java.awt.Color;
import java.awt.Graphics;
import Terracraft.Game;
import Terracraft.Handler;
import Terracraft.Id;
import Terracraft.Utils;
import Tile.source.Tile;
import gfx.Sprite;

public class Workbench extends Tile{

	private Sprite sprite = new Sprite(Game.sheet,1,2,2,1);
	
	public Workbench(int x, int y, int width, int height, Id id) {
		super(x, y, width, height, id);
		setHealth(100);
	}

	public void render(Graphics g) {
			double tmp;
			g.drawImage(sprite.getBufferedImage(),x,y,width,height,null);
			g.setColor(Color.red);
			if((Game.player.getX()+Game.player.getBreite()/2)<x){
				tmp=(x-Game.player.getX())/3.55;
				mapX=(Game.player.getX() + 200+175)+Utils.toInt(tmp);
			}else{
				tmp=(Game.player.getX()-x)/3.55;
				mapX=(Game.player.getX() + 200+175)-Utils.toInt(tmp);;
			}
			if((Game.player.getY()+Game.player.getHöhe()/2)<y){
				tmp=(y-Game.player.getY())/3.55;
				mapY=(Game.player.getY() - 430+103)+Utils.toInt(tmp);
			}else{
				tmp=(Game.player.getY()-y)/3.55;
				mapY=(Game.player.getY() - 430+103)-Utils.toInt(tmp);
			}

			g.fillRect(mapX,mapY,9,9);
	}

	public void tick() {
		
	}
	
	
	
	
}