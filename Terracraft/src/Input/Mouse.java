package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

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
		new Packet07AddTile(x, y, "TestTile").send(Game.client);
	}

	public void mouseReleased(MouseEvent m) {

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		x = m.getX();
		y = m.getY();	
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
