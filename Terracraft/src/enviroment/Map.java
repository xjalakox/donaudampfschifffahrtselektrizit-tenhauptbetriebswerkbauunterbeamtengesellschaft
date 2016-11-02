package enviroment;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.NetPlayer;
import gfx.Sprite2;
import terracraft.Game;
import terracraft.Id;
import terracraft.Utils;
import tile.source.Tile;

public class Map {
	private boolean online;
	private int playerCounter = 0;
	private int tick;
	BufferedImage map, mapframe;
	private Sprite2 player_head = new Sprite2(Game.sheet_head, 1, 1, 1, 1);
	private Sprite2 player_armor_head = new Sprite2(Game.sheet_armor_head, 1, 1, 1, 1);

	public Map() {
		try {
			map = ImageIO.read(getClass().getResource("/Misc/Map.png"));
			mapframe = ImageIO.read(getClass().getResource("/Misc/MapFrame.png"));
		} catch (IOException e) {
		}
	}

	public void render(Graphics2D g) {

//		g.setColor(Color.black);
//		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
//		g.drawString("Eigene Position:", Game.player.getX() , Game.player.getY() + 74 - 400 - 74);
//		g.drawString(Game.player.getX() + " " + Game.player.getY(), Game.player.getX() ,
//				Game.player.getY() + 94 - 400 - 74);
//
//		for (Entity e : Game.handler.entity2) {
//			if (e.getId().equals(Id.NetPlayer)) {
//				online = true;
//				g.drawString("Username: " + ((NetPlayer) e).getUsername(), Game.player.getX() ,
//						Game.player.getY() + 134 - 400 - 74 + playerCounter * 40);
//				g.drawString(e.getX() + " " + e.getY(), Game.player.getX() ,
//						Game.player.getY() + 154 - 400 - 74 + playerCounter * 40);
//				playerCounter += 1;
//			}
//		}
//		if (online == true) {
//			g.drawString("Andere Positionen:", Game.player.getX() , Game.player.getY() + 114 - 400 - 74);
//		}
		g.drawImage(this.getBufferedImage(map),640+ 200, 5, 360, 220, null);
		online = false;
		playerCounter = 0;
		for (Tile ti : Game.handler.tile2) {
			if (shouldRenderMap(ti)) {

				renderMap(g,ti);
			}
		}
		g.drawImage(player_head.getBufferedImage(), 640 + 200 + 175 - 16,
				5 + 103 + 30 - 8, 32, 32, null);
		g.drawImage(player_armor_head.getBufferedImage(), 640 + 200 + 175 - 16,
				5 + 103 + 30 - 8, 32, 32, null);
		g.drawImage(this.getBufferedImage(mapframe),640+ 200, 5, 360, 220, null);
		g.setColor(Color.red);
//		g.fillRect(840, 5, 360, 220);
	}

	public void tick() {
		tick++;
		if (tick >= 60) {
			tick = 0;
		}
	}

	public BufferedImage getBufferedImage(BufferedImage image) {
		return image;
	}

	public Rectangle getBounds() {
		return new Rectangle(845, 5, 350, 220);
	}
	public void renderMap(Graphics2D g,Tile ti) {
		int mapX,mapY;
		double tmp;
		if ((Game.player.getX() + Game.player.getBreite() / 2) < ti.getX()) {
			tmp = (ti.getX() -Game.player.getX()) / 16;
			mapX = (640 + 200 + 175) + Utils.toInt(tmp);
		} else {
			tmp = (Game.player.getX() - ti.getX()) / 16;
			mapX = (640 + 200 + 175) - Utils.toInt(tmp);
			;
		}
		if ((Game.player.getY() + Game.player.getHeight() / 2) < ti.getY() ) {
			tmp = (ti.getY() - Game.player.getY()) / 16;
			mapY = (5 + 103 + 30) + Utils.toInt(tmp);
		} else {
			tmp = (Game.player.getY() - ti.getY() ) / 16;
			mapY = (5 + 103 + 30) - Utils.toInt(tmp);
		}
		if (getBounds().intersects(new Rectangle(mapX, mapY, 2, 2))) {
			g.drawImage(ti.getCurrentSprite().getBufferedImage(), mapX, mapY, 2, 2, null);
		}

	}
	
	public boolean shouldRenderMap(Tile ti) {
		if (getVisisbleAreaMap() != null && ti.getBounds().intersects(getVisisbleAreaMap())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Rectangle getVisisbleAreaMap() {
		return new Rectangle(640 - 650 - 1920, 360 - 440 - 1050, Game.getFrameWidth() * 4, 700 * 4);
	}
}
