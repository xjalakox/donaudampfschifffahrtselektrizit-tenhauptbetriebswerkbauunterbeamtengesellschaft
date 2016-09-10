package Terracraft;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Entity;
import Entity.NetPlayer;
import Tile.source.Tile;
import gfx.Sprite;
import gfx.Sprite2;

public class Map {
	private boolean online;
	private int playerCounter = 0;
	private int tick;
	BufferedImage map,mapframe;
	private Sprite2 player_head=new Sprite2(Game.sheet_head,1,1,1,1);
	private Sprite2 player_armor_head=new Sprite2(Game.sheet_armor_head,1,1,1,1);
	public Map(){
		try {
			map = ImageIO.read(getClass().getResource("/Map.png"));
			mapframe = ImageIO.read(getClass().getResource("/MapFrame.png"));
		} catch (IOException e) {}
	}
	public void render(Graphics g) {
		
		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString("Eigene Position:", Game.player.getX() + 450, Game.player.getY() + 74 - 400 - 74);
		g.drawString(Game.player.getX() + " " + Game.player.getY(), Game.player.getX() + 450,
				Game.player.getY() + 94 - 400 - 74);

		for (Entity e : Game.handler.entity2) {
			if (e.getId().equals(Id.NetPlayer)) {
				online = true;
				g.drawString("Username: " + ((NetPlayer) e).getUsername(), Game.player.getX() + 450,
						Game.player.getY() + 134 - 400 - 74 + playerCounter * 40);
				g.drawString(e.getX() + " " + e.getY(), Game.player.getX() + 450,
						Game.player.getY() + 154 - 400 - 74 + playerCounter * 40);
				playerCounter += 1;
			}
		}
		if (online == true) {
			g.drawString("Andere Positionen:", Game.player.getX() + 450, Game.player.getY() + 114 - 400 - 74);
		}
		g.drawImage(this.getBufferedImage(map),  Game.player.getX() + 200,Game.player.getY() - 430,360,220,null);
//		g.setColor(Color.green);
//		g.fillRect(Game.player.getX() - 650-Game.getFrameBreite()/2,Game.player.getY() - 440-350, Game.getFrameBreite()*2, 700*2);


		online = false;
		playerCounter = 0;
		
		
		
		
		for(Tile ti:Game.handler.tile2){
			if (Game.handler.shouldRenderMap(ti)) {

				ti.mapRender(g);
			}
		}
		g.drawImage(player_head.getBufferedImage(), Game.player.getX() + 200+175-16,Game.player.getY() - 430+103+30-8,32,32,null);
		g.drawImage(player_armor_head.getBufferedImage(), Game.player.getX() + 200+175-16,Game.player.getY() - 430+103+30-8,32,32,null);
		g.drawImage(this.getBufferedImage(mapframe),  Game.player.getX() + 200,Game.player.getY() - 430,360,220,null);
	}
	public void tick(){
		tick++;
		if(tick>=60){
			tick=0;
		}
	}
	
	public BufferedImage getBufferedImage(BufferedImage image){
		return image;
	}
	public Rectangle getBounds(){
		return new Rectangle(  Game.player.getX() + 200,Game.player.getY() - 430,360,220);
	}
	
	
	
	

}
