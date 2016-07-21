package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Terracraft.Game;
import network.packets.Packet01Disconnect;
import network.packets.Packet07AddTile;

public class Key implements KeyListener {

	private int key;

	public void keyPressed(KeyEvent k) {
		key = k.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			if (Game.player.velY == 0 && Game.player.velX == 0)
				Game.player.velY = -60;
			break;
		case KeyEvent.VK_D:
			if (Game.player.velX == 0 && Game.player.velY == 0)
				Game.player.velX = 60;
			break;
		case KeyEvent.VK_S:
			if (Game.player.velY == 0 && Game.player.velX == 0)
				Game.player.velY = 60;
			break;
		case KeyEvent.VK_A:
			if (Game.player.velX == 0 && Game.player.velY == 0)
				Game.player.velX = -60;
			break;
		case KeyEvent.VK_ESCAPE:
			new Packet01Disconnect(Game.player.getUsername(), Game.player.getX(), Game.player.getY()).send(Game.client);
			System.exit(0);
			break;
		}
	}

	public void keyReleased(KeyEvent k) {
		key = k.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			// Game.p.setVelY(0);
			break;
		case KeyEvent.VK_D:
			// Game.p.setVelX(0);
			break;
		case KeyEvent.VK_S:
			// Game.p.setVelY(0);
			break;
		case KeyEvent.VK_A:
			// Game.p.setVelX(0);
			break;
		}

	}

	public void keyTyped(KeyEvent k) {

	}

}
