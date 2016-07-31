package Terracraft;

import java.awt.Graphics;
import java.util.LinkedList;
import Entity.Entity;
import Entity.NetPlayer;
import Entity.Player;
import Tile.source.Tile;

public class Handler {

	public static LinkedList<Entity> entity = new LinkedList<Entity>();
	public static LinkedList<Entity> entity2 = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();
	public LinkedList<Tile> tile2 = new LinkedList<Tile>();

	public void render(Graphics g) {

		tile2 = (LinkedList<Tile>) tile.clone();
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
		entity2 = (LinkedList<Entity>) entity.clone();
		for (Entity en : entity) {
			en.tick();
		}
		for (int i = 0; i < entity.size(); i++) {
			if (entity.get(i).isRemoved())
				entity.remove(i);
		}
		
		remove();
	}
	
	public void remove(){
		for(int i = 0; i < tile.size(); i++){
			if(tile.get(i).shouldRemove()) {
				tile.remove(i);
			}
		}
	}
	

	public void addEntity(Entity en) {
		entity.add(en);
	}

	public void addTile(Tile ti) {
		tile.add(ti);
	}

	public void removePlayer(String username) {
		if (!username.equals(Terracraft.Game.player.getUsername())) {
			for (Entity en : entity) {
				if (en.getId() == Id.Player) {
					if (username.equals(((Player) en).getUsername())) {
						en.remove();
					}
				} else if (en.getId() == Id.NetPlayer) {
					if (username.equals(((NetPlayer) en).getUsername())) {
						en.remove();
					}
				}
			}
		}
	}

	public Entity getPlayer(String username) {
		for (Entity entity : entity) {
			if (entity.getId() == Id.Player) {
				if (((Player) entity).getUsername().equals(username)) {
					return entity;
				}
			} else if (entity.getId() == Id.NetPlayer) {
				if (((NetPlayer) entity).getUsername().equals(username)) {
					return entity;
				}
			}
		}
		return null;
	}


	public void setPlayerPosition(String username, int x, int y, Id tool) {
		if (!username.equals(Game.player.getUsername()) && getPlayer(username) != null) {
			if (getPlayer(username).getId() == Id.Player) {
				//((Player) getPlayer(username)).setPosition(x, y);
				//((Player) getPlayer(username)).setTool(tool, x, y);
			} else if (getPlayer(username).getId() == Id.NetPlayer) { 
				
				((NetPlayer) getPlayer(username)).setDirectionGoing(x);
				((NetPlayer) getPlayer(username)).setPosition(x, y);
				((NetPlayer) getPlayer(username)).setTool(tool, x, y);
				
			}
		}
	}

}