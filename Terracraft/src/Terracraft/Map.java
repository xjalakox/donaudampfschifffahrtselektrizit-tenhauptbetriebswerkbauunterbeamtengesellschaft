package Terracraft;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.Entity;
import Entity.NetPlayer;
import Tile.source.Tile;

public class Map {
	private boolean online;
	private int playerCounter = 0;
	BufferedImage map;
	public Map(){
		try {
			map = ImageIO.read(getClass().getResource("/Map.png"));
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
		online = false;
		playerCounter = 0;
		
		
	}
	
	
	
	public BufferedImage getBufferedImage(BufferedImage image){
		return image;
	}
	public Rectangle getBounds(){
		return new Rectangle(  Game.player.getX() + 200,Game.player.getY() - 430,360,220);
	}
	
	
	
	

}
