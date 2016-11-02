package terracraft;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import crafting.Recipe;
import gfx.Sprite;
import input.Mouse;
import net.Network.RemoveTile;
import tile.source.Tile;

public class MiningHandler {
	public boolean itemexists, itemdeployed;
	public ArrayList<Id> scrollbarTiles = new ArrayList<Id>();
	public static int[] scrollbar_amount = new int[10];
	private Sprite[] scrollsprite = new Sprite[10];
	private Sprite scrollspriteaimed = new Sprite(Game.sheet, 3, 1, 1, 1);
	private Sprite inventory_background = new Sprite(Game.sheet, 1, 1, 1, 1);
	private int tick;
	public static Id equippedTool;

	public void init() {
		scrollbarTiles.add(Id.Pickaxe);
		scrollbarTiles.add(Id.Hammer);
		scrollbarTiles.add(Id.Grass);
		scrollbarTiles.add(Id.Empty);
		scrollbarTiles.add(Id.Empty);
		scrollbarTiles.add(Id.Empty);
		scrollbarTiles.add(Id.Empty);
		scrollbarTiles.add(Id.Empty);
		scrollbarTiles.add(Id.Empty);
		scrollbarTiles.add(Id.Empty);
		scrollbar_amount[2] = 10;
		for (int i = 0; i < 10; i++) {
			scrollsprite[i] = new Sprite(Game.sheet, 1, 1, 1, 1);
		}
	}

	@SuppressWarnings("unchecked")
	public void render(Graphics2D g) {
		if (Game.player.isInventoryOpen()) {
			renderInventory(g);
		}
		scrollbarTiles = (ArrayList<Id>) Game.player.Inventory.clone();
		for (int i = 0; i < 10; i++) {
			scrollbar_amount[i] = Game.player.Inventory_amount[i];
		}
		for (int i = 0; i < 10; i++) {
			g.drawImage(scrollsprite[i].getBufferedImage(), i * 74 + 20, 20, 64, 64, null);
		}
		if (Mouse.mouseRotation < 10 && Mouse.mouseRotation > -1) {
			g.drawImage(scrollspriteaimed.getBufferedImage(), Mouse.mouseRotation * 74 + 17, 17, 70, 70, null);
		}

		for (int i = 0; i < 10; i++) {
			if (Mouse.mouseRotation == i) {
				if (scrollbarTiles.get(i).getType().equals("block") || scrollbarTiles.get(i).getType().equals("item")) {
					g.setColor(Color.white);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString(Utils.toString(scrollbar_amount[i]), (i * 74) + 25, 76);
				}
				if (!scrollbarTiles.get(i).equals(Id.Empty)) {
					g.drawImage(scrollbarTiles.get(i).getImage().getBufferedImage(), (i * 74) + 34, 29, 35, 35, null);
				}
				equippedTool = scrollbarTiles.get(i);
			} else {
				if (!scrollbarTiles.get(i).equals(Id.Empty)) {
					g.drawImage(scrollbarTiles.get(i).getImage().getBufferedImage(), (i * 74) + 34, 29, 32, 32, null);
				}
				if (scrollbarTiles.get(i).getType().equals("block") || scrollbarTiles.get(i).getType().equals("item")) {
					g.setColor(Color.white);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString(Utils.toString(scrollbar_amount[i]), i * 74 + 25, 76);
				}
			}
		}

		if (Mouse.mouseRotation < 0)
			Mouse.mouseRotation = 9;
		if (Mouse.mouseRotation > 9)
			Mouse.mouseRotation = 0;
	}

	public void tick() {

		if (Mouse.pressed && Mouse.mouseRotation < 10 && Mouse.mouseRotation > -1 && tick == 30
				&& Mouse.degradedTile.getBounds().intersects(Game.player.getArea())) {
			tick = 0;
			if (Game.player.isClick() || Game.player.isClicked()) {
				if (!scrollbarTiles.get(Mouse.mouseRotation).getType().equals("block")) {
					if (scrollbarTiles.get(Mouse.mouseRotation).getType().equalsIgnoreCase("tool")
							&& Mouse.degradedTile.getId().getType().equalsIgnoreCase("block")) {
						Mouse.degradedTile.addDamage(scrollbarTiles.get(Mouse.mouseRotation).getEfficiency());
					}
				}
			}
		}

		if (Mouse.mousedown) {
			for (Tile ti : Game.handler.tile2) {
				if (Game.m.Collision().intersects(ti.getBounds())) {
					Mouse.degradedTile = ti;
					Mouse.pressed = true;
				}
			}
		}

		if (Mouse.degradedTile != null) {
			if (Mouse.degradedTile.getDamage() <= 0) {
				for (Tile ti : Game.handler.tile) {
					if (ti == Mouse.degradedTile) {
						RemoveTile request = new RemoveTile();
						request.x = ti.getX();
						request.y = ti.getY();
						Game.client.sendTCP(request);
						Game.handler.setToBeRemoved(ti.getX(), ti.getY());
						if (!ti.getId().equals(Id.Tree)) {
							for (int i = 0; i < 40; i++) {
								if (Game.player.Inventory.get(i).equals(ti.getId())) {
									Game.player.Inventory_amount[i] += 1;
									itemexists = true;
								}
							}

							if (!itemexists) {

								for (int i = 0; i < 40; i++) {
									if (!itemdeployed) {
										if (Game.player.Inventory.get(i).equals(Id.Empty)) {
											Game.player.Inventory.set(i, ti.getId());
											Game.player.Inventory_amount[i] = 1;
											itemdeployed = true;

										}
									}

								}
							}
						} else {
							for (int i = 0; i < 40; i++) {
								if (Game.player.Inventory.get(i).equals(Id.Wood)) {
									Game.player.Inventory_amount[i] += Utils.RandomInt(7, 10);
									itemexists = true;
								}
							}

							if (!itemexists) {

								for (int i = 0; i < 40; i++) {
									if (!itemdeployed) {
										if (Game.player.Inventory.get(i).equals(Id.Empty)) {
											Game.player.Inventory.set(i, Id.Wood);
											Game.player.Inventory_amount[i] = Utils.RandomInt(7, 10);
											itemdeployed = true;

										}
									}

								}
							}
						}
						ti.setAsRemoved();
						itemdeployed = false;
						itemexists = false;
					}
				}
			}
		}
		for (int i = 0; i < 40; i++) {
			if (Game.player.Inventory.get(i).getType().equals("block") && Game.player.Inventory_amount[i] <= 0) {
				Game.player.Inventory.set(i, Id.Empty);
			}
		}
		if (tick < 30) {
			tick++;
		}
	}

	public void renderInventory(Graphics2D g) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 4; j++) {

				g.drawImage(inventory_background.getBufferedImage(), i * 74 + 20, 20 + 74 * j, 64, 64, null);
				if (!Game.player.Inventory.get(j * 10 + i).equals(Id.Empty)) {
					g.drawImage(Game.player.Inventory.get(j * 10 + i).getImage().getBufferedImage(), i * 74 + 20 + 16,
							20 + 16 - 74 + 74 * j + 74, 32, 32, null);
					if (Game.player.Inventory.get(j * 10 + i).getType().equals("block")
							|| Game.player.Inventory.get(j * 10 + i).getType().equals("item")) {
						g.setColor(Color.white);
						g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
						g.drawString(Utils.toString(Game.player.Inventory_amount[j * 10 + i]), i * 74 + 25,
								74 * j + 76);
					}
				}

			}
		}
		if (!Game.m.mouseItem.equals(Id.Empty)) {
			g.drawImage(Game.m.mouseItem.getImage().getBufferedImage(), Game.m.lookingAtX, Game.m.lookingAtY, 32, 32,
					null);
		}

		// Craftable Items
		if (!Game.player.isGotRecipes()) {
			Game.player.recipes = Recipe.getCraftableRecipes();
			Game.player.setGotRecipes(true);
		}
		for (int i = 0; i < Game.player.recipes.length; i++) {
			if (Game.player.recipes[i] != null) {
				g.drawImage(inventory_background.getBufferedImage(), 20, 20 + 74 * 4 + i * 48, 48, 48, null);
				g.drawImage(Id.toId(Game.player.recipes[i].getName()).getImage().getBufferedImage(), 28,
						20 + 74 * 4 + i * 48 + 8, 32, 32, null);

			}
		}
	}
}
