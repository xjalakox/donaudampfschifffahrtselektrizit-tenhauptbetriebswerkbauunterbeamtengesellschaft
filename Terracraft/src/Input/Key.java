package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Terracraft.*;
import crafting.Recipe;
import net.Network.Inventory;

public class Key implements KeyListener {

	private int key;
	private boolean exiting;
	public static boolean run = false, d = false, w = false, shift = false, a = false, s = false;

	public void keyPressed(KeyEvent k) {
		key = k.getKeyCode();
		if (k.getKeyCode() == KeyEvent.VK_ESCAPE&&!exiting) {
			exiting = true;
			for (int i = 0; i < Game.player.Inventory.size(); i++) {
				Inventory request = new Inventory();
				request.itemid = Game.player.Inventory.get(i) + "," + Game.player.Inventory_amount[i];
				Game.client.sendTCP(request);
			}
		}
		if(k.getKeyCode() == KeyEvent.VK_Z){
			Recipe.craftItem(Recipe.Workbench);
		}
		if (!Game.consoleOpen) {
			switch (key) {
			case KeyEvent.VK_W:
				w = true;
				if (!Game.player.jumping && !Game.player.falling) {
					if (!Game.player.jumping && !Game.player.falling) {
						Game.player.jumping = true;
						Game.player.gravity = 17.0f;
					}
				}

				break;
			case KeyEvent.VK_D:
				d = true;
				break;
			case KeyEvent.VK_A:
				a = true;

				break;
			case KeyEvent.VK_S:
				s = true;
				break;
			case KeyEvent.VK_T:
				if (!Game.player.isInventoryOpen())
					Game.consoleOpen = true;
				break;
			case KeyEvent.VK_1:
				Mouse.mouseRotation = 0;
				break;
			case KeyEvent.VK_2:
				Mouse.mouseRotation = 1;
				break;
			case KeyEvent.VK_3:
				Mouse.mouseRotation = 2;
				break;
			case KeyEvent.VK_4:
				Mouse.mouseRotation = 3;
				break;
			case KeyEvent.VK_5:
				Mouse.mouseRotation = 4;
				break;
			case KeyEvent.VK_6:
				Mouse.mouseRotation = 5;
				break;
			case KeyEvent.VK_7:
				Mouse.mouseRotation = 6;
				break;
			case KeyEvent.VK_8:
				Mouse.mouseRotation = 7;
				break;
			case KeyEvent.VK_9:
				Mouse.mouseRotation = 8;
				break;
			case KeyEvent.VK_0:
//				Mouse.mouseRotation = 9;
				Game.player.Inventory.set(4, Id.Workbench);
				Game.player.Inventory_amount[4]=15;
				Game.player.Inventory.set(5, Id.Pickaxe);
				break;
			case KeyEvent.VK_E:
				if (!Game.player.isInventoryOpen()) {
					Game.player.setInventoryOpen(true);
				} else {
					Game.player.setInventoryOpen(false);
				}
				break;

			}
		} else {
			String text = KeyEvent.getKeyText(k.getKeyCode());
			if (k.getKeyCode() == KeyEvent.VK_SPACE) {
				text = " ";
				Game.drawKeyInput(text);
			} else if (k.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				Game.drawKeyInput("backspace");
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

		case KeyEvent.VK_D:
			Game.player.setVelX(0);
			Game.player.moving = -1;
			d = false;
			break;
		case KeyEvent.VK_A:
			Game.player.setVelX(0);
			Game.player.moving = -2;
			a = false;
			break;
		case KeyEvent.VK_W:
			w = false;
			break;
		case KeyEvent.VK_S:
			s = false;
			break;
		case KeyEvent.VK_ENTER:
			if (Game.consoleOpen) {
				String without = Utils.removeFirstChar(Game.TextToDrawInConsole);
				String[] commands = without.split("\\s");
				Game.executeCommand(commands);
				Game.consoleOpen = false;
				Game.TextToDrawInConsole = "";

				// send to all und evtl. command ausführen

			}
			break;
		}

	}

	public void keyTyped(KeyEvent k) {

	}

	private boolean isWritable(String text) {
		return text.matches("[a-zA-ZäÄöÖüÜß1234567890!/§$%&()=?`´*'#~|><]");
	}

}
