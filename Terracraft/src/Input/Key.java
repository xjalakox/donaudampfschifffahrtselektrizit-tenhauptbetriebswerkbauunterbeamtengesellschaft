package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Terracraft.Game;
import network.packets.Packet01Disconnect;

public class Key implements KeyListener {

	private int key;

	public void keyPressed(KeyEvent k) {
		key = k.getKeyCode();
		if (k.getKeyCode() == KeyEvent.VK_ESCAPE) {
			new Packet01Disconnect(Game.player.getUsername(), Game.player.getX(), Game.player.getY()).send(Game.client);
			System.exit(0);
		}
		if (!Game.consoleOpen) {
			switch (key) {
			case KeyEvent.VK_W:
				Game.player.y-=1;
				break;
			case KeyEvent.VK_D:
				Game.player.x+=1;
				break;
			case KeyEvent.VK_S:
				Game.player.y+=1;
				break;
			case KeyEvent.VK_A:
				Game.player.x-=1;
				break;
			case KeyEvent.VK_T:
				Game.consoleOpen = true;
				break;

			}
		} else {
			String text = KeyEvent.getKeyText(k.getKeyCode());
			if (k.getKeyCode() == KeyEvent.VK_SPACE) {
				text = " ";
				Game.drawKeyInput(text);
			} else if (k.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				Game.drawKeyInput(text);
			} else {
				char t = k.getKeyChar();
				String textToWrite = t + "";
				if (isWritable(textToWrite))
					Game.drawKeyInput(textToWrite);
			}
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
		case KeyEvent.VK_ENTER:
			if (Game.consoleOpen) {
				Game.TextToDrawInConsole = "";
				Game.consoleOpen = false;
				// send to all und evtl. command ausfќhren

			}
			break;
		}

	}

	public void keyTyped(KeyEvent k) {

	}

	private boolean isWritable(String text) {
		return text.matches("[a-zA-ZфФіжќмп1234567890!/Ї$%&()=?`Д*'#~|><]");
	}

}
