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
import Terracraft.Id;
import Terracraft.MiningHandler;
import Tile.source.Tile;
import net.Network.AddTile;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	public int x;
	public int y;
	public static int mouseRotation = 0;
	public static boolean pressed;
	public static Tile degradedTile;
	public int lookingAtX;
	public int lookingAtY;
	public static boolean mousedown;
	public Id mouseItem;
	public int mouse_amount;

	public void mouseClicked(MouseEvent m) {
		int x = m.getX();
		int y = m.getY();
	}

	public void mouseEntered(MouseEvent m) {

	}

	public void mouseExited(MouseEvent m) {

	}

	public void mousePressed(MouseEvent m) {
		if (!Game.player.InventoryBounds().intersects(Collision())) {
			if (m.getButton() == m.BUTTON1) {
				Game.player.setClicked(true);
				Game.player.setClick(true);
				/**new Packet11Mine(1, 1, Game.player.getUsername()).send(Game.client);**/
			}
			if (m.getButton() == m.BUTTON1) {
				mousedown = true;
			}

			if (m.getButton() == m.BUTTON3 && !Game.consoleOpen && !lookIfOccupied()) {
				System.out.println(MiningHandler.equippedTool.toString());
				if (MiningHandler.scrollbarTiles.get(mouseRotation).equals(Id.Grass)
						&& Game.player.Inventory_amount[mouseRotation] >= 1) {
					AddTile request = new AddTile();
					request.x = lookingAtX;
					request.y=lookingAtY;
					request.type="Grass";
					Game.client.sendTCP(request);
					Game.player.Inventory_amount[mouseRotation] -= 1;
				}else if (MiningHandler.scrollbarTiles.get(mouseRotation).equals(Id.Workbench)
						&& Game.player.Inventory_amount[mouseRotation] >= 1) {
					AddTile request = new AddTile();
					request.x = lookingAtX;
					request.y=lookingAtY;
					request.type="Workbench";
					Game.client.sendTCP(request);
					Game.player.Inventory_amount[mouseRotation] -= 1;
				}
				

			}
		} else {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 4; j++) {

					if (Collision().intersects(new Rectangle(i * 74 + 20 + Game.player.getX() - 650,
							20 + Game.player.getY() - 450 + 74 * j, 64, 64))) {
						if (mouseItem.equals(Id.Empty)) {
							mouseItem = Game.player.Inventory.get(j * 10 + i);
							Game.player.Inventory.set(j * 10 + i, Id.Empty);
							mouse_amount = Game.player.Inventory_amount[j * 10 + i];
						} else {
							if (mouseItem.equals(Game.player.Inventory.get(j * 10 + i))
									&& mouseItem.getType().equals("block")) {
								Game.player.Inventory_amount[j * 10 + i] += mouse_amount;
								mouseItem = Id.Empty;
								mouse_amount = 0;
							} else {
								int temporary_amount = Game.player.Inventory_amount[j * 10 + i];
								Id temporary = Game.player.Inventory.get(j * 10 + i);
								Game.player.Inventory_amount[j * 10 + i] = mouse_amount;
								Game.player.Inventory.set(j * 10 + i, mouseItem);
								mouseItem = temporary;
								mouse_amount = temporary_amount;
							}

						}

					}
				}
			}

			// for(int i=0;i<10;i++){
			//
			//
			// if(Collision().intersects(new Rectangle(i * 74 + 20 +
			// Game.player.getX()- 650, 20 + Game.player.getY()-450, 64, 64))){
			// if(mouseItem.equals(Id.Empty)){
			// mouseItem=MiningHandler.scrollbarTiles.get(i);
			// MiningHandler.scrollbarTiles.set(i,Id.Empty);
			// mouse_amount=MiningHandler.scrollbar_amount[i];
			// }else{
			// if(mouseItem.equals(MiningHandler.scrollbarTiles.get(i))&&mouseItem.getType().equals("block")){
			// MiningHandler.scrollbar_amount[i]+=mouse_amount;
			// mouseItem=Id.Empty;
			// }else{
			// int temporary_amount=MiningHandler.scrollbar_amount[i];
			// Id temporary=MiningHandler.scrollbarTiles.get(i);
			// MiningHandler.scrollbarTiles.set(i,mouseItem);
			// MiningHandler.scrollbar_amount[i]=mouse_amount;
			// mouseItem=temporary;
			// mouse_amount=temporary_amount;
			// }
			//
			// }
			//
			// }
			//
			//
			//
			//
			// }

		}
	}

	public void mouseReleased(MouseEvent m) {
		mousedown = false;
		pressed = false;

		if (Game.player.click == false) {
		//	new Packet11Mine(0, 0, Game.player.getUsername()).send(Game.client);
		} else {
		//	new Packet11Mine(1, 0, Game.player.getUsername()).send(Game.client);
		}
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

	public boolean lookIfOccupied() {
		for (Tile ti : Game.handler.tile2) {
			if (ti.getX() == lookingAtX && ti.getY() == lookingAtY) {
				return true;
			}
		}
		return false;
	}

	public Rectangle Collision() {
		return new Rectangle(lookingAtX, lookingAtY, 2, 2);
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
