package Terracraft;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import Entity.Entity;
import Entity.Player;
import Tile.source.Tile;

public class Handler {

	public static LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();
	public LinkedList<Tile> tile2 = new LinkedList<Tile>();
	int tilecounter=0;
	public void render(Graphics g) {
		tile2 =(LinkedList<Tile>)tile.clone();
		for (Tile ti : tile2) {
			ti.render(g);
		}

		for (Entity en : entity) {
			en.render(g);
		}
	}

	public void tick() {
		for (Tile ti : tile2) {
			ti.tick();
		}
		for (Entity en : entity) {
			en.tick();
		}
		for (int i = 0; i < entity.size(); i++) {
			if (entity.get(i).isRemoved())
				entity.remove(i);
		}
	}

	public void addEntity(Entity en) {
		entity.add(en);
	}

	public void addTile(Tile ti) {
		tile.add(ti);
	}

	public void createlevel(BufferedImage level) {
		
		int breite = level.getWidth();
		int höhe = level.getHeight();

		for (int y = 0; y < höhe; y++) {
			for (int x = 0; x < breite; x++) {
				int pixel = level.getRGB(x, y);

				int red, green, blue;
				red = (pixel >> 16) & 0xff;
				green = (pixel >> 8) & 0xff;
				blue = (pixel) & 0xff;
			}
		}
	}

	public void removePlayer(String username) {
		if (!username.equals(Terracraft.Game.player.getUsername())) {
			for (Entity en : entity) {
				if (en.getId() == Id.player) {
					if (username.equals(((Player) en).getUsername())) {
						en.remove();
						
					}
				}
			}
		}
	}

	public Entity getPlayer(String username) {
		for (Entity entity : entity) {
			if (entity.getId() == Id.player)
				if (((Player) entity).getUsername().equals(username)) {
					return entity;
				}
		}
		return null;
	}

	public void setPlayerPosition(String username, int x, int y) {
		if (!username.equals(Game.player.getUsername())&&getPlayer(username)!=null)
			((Player) getPlayer(username)).setPosition(x, y);
	}
}