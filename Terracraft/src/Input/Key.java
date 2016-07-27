package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.sun.java.accessibility.util.java.awt.TextComponentTranslator;

import Terracraft.Game;
import network.packets.Packet01Disconnect;

public class Key implements KeyListener {

	private int key;
	public static boolean run = false,d=false,shift=false,a=false;
	public void keyPressed(KeyEvent k) {
		key = k.getKeyCode();
		if (k.getKeyCode() == KeyEvent.VK_ESCAPE) {
			new Packet01Disconnect(Game.player.getUsername(), Game.player.getX(), Game.player.getY()).send(Game.client);
			System.exit(0);
		}
		if (!Game.consoleOpen) {
			switch (key) {
			case KeyEvent.VK_W:
				if(!Game.player.jumping&&!Game.player.falling){
					if(!Game.player.jumping&&!Game.player.falling){
						Game.player.jumping=true;
						Game.player.gravity=17.0f;
					}
				}
					
				break;
			case KeyEvent.VK_D:
				d = true;
				break;
			case KeyEvent.VK_A:
				a=true;

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
			Game.player.moving=-1;
			d=false;
			break;
		case KeyEvent.VK_A:
			Game.player.setVelX(0);
			Game.player.moving=-2;
			a=false;
			break;
		case KeyEvent.VK_ENTER:
			if (Game.consoleOpen) {
				String without = Game.removeFirstChar(Game.TextToDrawInConsole);
				String[] commands = without.split("\\s");
				for(int i=0;i<commands.length;i++){
					System.out.println(commands[i]);
				}
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
