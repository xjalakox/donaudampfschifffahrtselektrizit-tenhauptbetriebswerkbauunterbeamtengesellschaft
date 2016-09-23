package input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import crafting.Recipe;
import net.Network.AddTile;
import net.Network.HittingBlock;
import terracraft.Game;
import terracraft.Id;
import tile.Door;
import tile.source.Tile;

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
		x = m.getX();
		y = m.getY();
	}

	public void mouseEntered(MouseEvent m) {

	}

	public void mouseExited(MouseEvent m) {

	}

	public void mousePressed(MouseEvent m) {

		if (m.getButton() == MouseEvent.BUTTON1) {
			if (!Game.player.isInventoryOpen()) {
				if (!Collision().intersects(Game.player.closedInventoryBounds())) {
					mouseClick();

				} else {
					clickInventoryClosed();
				}
			} else {
				if (!Collision().intersects(Game.player.InventoryBounds())) {
					if (!Collision().intersects(Game.player.recipeBounds())) {
						mouseClick();
					} else {
						recipeClick();
					}
				} else {
					clickInventory();

				}

			}
			mousedown = true;
		}

		if (m.getButton() == MouseEvent.BUTTON3) {
			if (!Game.player.isInventoryOpen()) {
				if (!Collision().intersects(Game.player.closedInventoryBounds())) {
					placeBlock();
				}
			} else {
				if (!Collision().intersects(Game.player.InventoryBounds())
						&& !Collision().intersects(Game.player.recipeBounds())) {
					placeBlock();
				}
			}

		}

	}

	public void mouseReleased(MouseEvent m) {
		mousedown = false;
		pressed = false;
		HittingBlock request = new HittingBlock();
		request.click = Game.player.click;
		request.clicked = false;
		request.username = Game.player.getUsername();
		Game.client.sendUDP(request);
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
		Tile dummyTile = Id.getTile(Game.player.Inventory.get(mouseRotation).toString());
		for (Tile ti : Game.handler.tile2) {
			if (ti.getBounds().intersects(Collision())) {
				if (ti.getId() == Id.Door) {
					((Door) ti).changeState();
				}
			}
			if (Game.mininghandler.scrollbarTiles.get(mouseRotation).getType().equalsIgnoreCase("block")) {
				if(dummyTile.getId().equals(Id.Door)){
					if (ti.getBounds().intersects(new Rectangle(lookingAtX, lookingAtY-64, dummyTile.getWidth(), dummyTile.getHeight()))) {
						return true;
					}
				}else{
				if (ti.getBounds().intersects(new Rectangle(lookingAtX, lookingAtY, dummyTile.getWidth(), dummyTile.getHeight()))) {
					return true;
				}
				}
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

	public void mouseClick() {
		Game.player.setClicked(true);
		Game.player.setClick(true);
		HittingBlock request = new HittingBlock();
		request.click = true;
		request.clicked = true;
		request.username = Game.player.getUsername();
		Game.client.sendUDP(request);
	}

	public void clickInventory() {
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

	}

	public void clickInventoryClosed() {
		for (int i = 0; i < 10; i++) {
			if (Collision().intersects(new Rectangle(i * 74 + 20 + Game.player.getX() - 650,
					20 + Game.player.getY() - 450 + 74 * 0, 64, 64))) {
				if (mouseItem.equals(Id.Empty)) {
					mouseItem = Game.player.Inventory.get(0 * 10 + i);
					Game.player.Inventory.set(0 * 10 + i, Id.Empty);
					mouse_amount = Game.player.Inventory_amount[0 * 10 + i];
				} else {
					if (mouseItem.equals(Game.player.Inventory.get(0 * 10 + i))
							&& mouseItem.getType().equals("block")) {
						Game.player.Inventory_amount[0 * 10 + i] += mouse_amount;
						mouseItem = Id.Empty;
						mouse_amount = 0;
					} else {
						int temporary_amount = Game.player.Inventory_amount[0 * 10 + i];
						Id temporary = Game.player.Inventory.get(0 * 10 + i);
						Game.player.Inventory_amount[0 * 10 + i] = mouse_amount;
						Game.player.Inventory.set(0 * 10 + i, mouseItem);
						mouseItem = temporary;
						mouse_amount = temporary_amount;
					}

				}

			}
		}

	}

	private void placeBlock() {
		if (!Game.console.consoleOpen && !lookIfOccupied()
				&& !Game.player.getBounds().intersects(new Rectangle(lookingAtX, lookingAtY, 32, 32))
				&& Game.player.getArea().intersects(new Rectangle(lookingAtX, lookingAtY, 32, 32))) {
			if (Game.mininghandler.scrollbarTiles.get(mouseRotation).getType().equalsIgnoreCase("block")
					&& Game.player.Inventory_amount[mouseRotation] >= 1) {
				AddTile request = new AddTile();
				if(Game.mininghandler.scrollbarTiles.get(mouseRotation).equals(Id.Door)){
					request.x = lookingAtX;
					request.y = lookingAtY-64;
				}else{
				request.x = lookingAtX;
				request.y = lookingAtY;
				}
				request.type = Game.mininghandler.scrollbarTiles.get(mouseRotation).toString();
				Game.client.sendTCP(request);
				Tile ti = Id.getTile(request.type);
				ti.setX(request.x);
				ti.setY(request.y);

				Game.handler.addTile(ti);

				Game.player.Inventory_amount[mouseRotation] -= 1;
			}
		}
	}

	public void recipeClick() {
		for (int i = 0; i < Game.player.recipes.length; i++) {
			if (Collision().intersects(
					new Rectangle(Game.player.getX() - 615 - 7, Game.player.getY() - 140 + i * 48, 48, 48))) {
				Recipe.craftItem(Game.player.recipes[i]);
			}
		}
	}
}
