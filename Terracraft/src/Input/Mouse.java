package Input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Terracraft.Game;
import network.packets.Packet07AddTile;

public class Mouse implements MouseListener, MouseMotionListener {

	private int x;
	private int y;

	public void mouseClicked(MouseEvent m) {
	}

	public void mouseEntered(MouseEvent m) {

	}

	public void mouseExited(MouseEvent m) {

	}

	public void mousePressed(MouseEvent m) {
		// Das hier grass platziert wird ist erstmal nur testweise und kann noch
		// geändert werden bzw. wird auch geändert
		if (!Game.consoleOpen) {
			new Packet07AddTile(x, y, "grass").send(Game.client);
		}
	}

	public void mouseReleased(MouseEvent m) {

	}

	public void mouseDragged(MouseEvent m) {
		x = m.getX();
		y = m.getY();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle Collision() {
		return new Rectangle(x, y, 2, 2);
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		x = m.getX();
		y = m.getY();
	}

}
