package Input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import Entity.Entity;
import Terracraft.Game;
import Tile.source.Tile;
import network.packets.Packet07AddTile;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	private int x;
	private int y;
	public static int mouseRotation = 0;
	public static boolean pressed;
	public static Tile degradedTile;
	public int lookingAtX;
	public int lookingAtY;

	public void mouseClicked(MouseEvent m) {

	}

	public void mouseEntered(MouseEvent m) {

	}

	public void mouseExited(MouseEvent m) {

	}

	public void mousePressed(MouseEvent m) {

		for (Tile ti : Game.handler.tile2) {
			if (m.getButton() == m.BUTTON1) {
				if ( Collision().intersects(ti.getBounds())){
					degradedTile = ti;
					pressed = true;
				}
				 	Game.player.setClicked(true);
				
				
				
		}
		}
		if (m.getButton() == m.BUTTON3 &&!Game.consoleOpen) {
			new Packet07AddTile(lookingAtX, lookingAtY, "grass").send(Game.client);
			
		}
	}

	public void mouseReleased(MouseEvent m) {
		pressed = false;
		Game.player.setClicked(false);
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

	public void mouseMoved(MouseEvent m) {
		x = m.getX();
		y = m.getY();
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
			mouseRotation++;
		}
		if (e.getWheelRotation() > 0) {
			mouseRotation--;
		}
	}

}
