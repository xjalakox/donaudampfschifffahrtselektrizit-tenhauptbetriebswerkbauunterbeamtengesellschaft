package Entity;

import java.awt.Color;
import java.awt.Graphics;

import Input.Key;
import Terracraft.Game;
import Terracraft.Id;

public class Player extends Entity {
	private String username;
	private Key key;

	public Player(String username, int x, int y, int breite, int höhe, Id id, Key key ) {
		super(x, y, breite, höhe, Game.handler,id);
		this.key = key;
		this.username = username;
	}
	
	public Player(String username, int x, int y, int breite, int höhe, Id id) {
		super(x, y, breite, höhe, Game.handler,id);
		this.username = username;
	}


	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, breite, höhe);
	}

	public void tick() {
		if (velX > 0 && velX < 61) {
			x += 2;
			velX--;
		} else if (velX > -61 && velX < 0) {
			x -= 2;
			velX++;
		} else if (velY > 0 && velY < 61) {
			y += 2;
			velY--;
		} else if (velY > -61 && velY < 0) {
			y -= 2;
			velY++;
		}

	}

	public String getUsername() {
		return username;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}