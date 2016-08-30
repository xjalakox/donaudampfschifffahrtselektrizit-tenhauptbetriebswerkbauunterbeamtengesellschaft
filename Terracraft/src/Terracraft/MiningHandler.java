package Terracraft;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import Input.Mouse;
import Tile.source.Tile;
import gfx.Sprite;

public class MiningHandler {
	public boolean itemexists, itemdeployed;
	public static ArrayList<Id> scrollbarTiles = new ArrayList<Id>();
	public static int[] scrollbar_amount = new int[10];
	private Sprite[] scrollsprite = new Sprite[10];
	private Sprite scrollspriteaimed = new Sprite(Game.sheet, 3, 1, 1, 1);
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

	public void render(Graphics g) {
		scrollbarTiles = (ArrayList<Id>) Game.player.Inventory.clone();
		for (int i = 0; i < 10; i++) {
			scrollbar_amount[i] = Game.player.Inventory_amount[i];
		}
		for (int i = 0; i < 10; i++) {
			g.drawImage(scrollsprite[i].getBufferedImage(), i * 74 + 20 + Game.player.getX() - 650,
					20 + Game.player.getY() - 450, 64, 64, null);
		}
		if (Mouse.mouseRotation < 10 && Mouse.mouseRotation > -1) {
			g.drawImage(scrollspriteaimed.getBufferedImage(), Mouse.mouseRotation * 74 + 17 + Game.player.getX() - 650,
					17 + Game.player.getY() - 450, 70, 70, null);
		}

		for (int i = 0; i < 10; i++) {
			if (Mouse.mouseRotation == i) {
				if (scrollbarTiles.get(i).getType().equals("block")) {
					g.setColor(Color.white);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString(Utils.toString(scrollbar_amount[i]), (i * 74) + 34 + Game.player.getX() - 650 - 10,
							33 + Game.player.getY() - 400);
				}
				if (!scrollbarTiles.get(i).equals(Id.Empty)) {
					g.drawImage(scrollbarTiles.get(i).getImage().getBufferedImage(),
							(i * 74) + 34 + Game.player.getX() - 650, 33 + Game.player.getY() - 450, 35, 35, null);
				}
				equippedTool = scrollbarTiles.get(i);
			} else {
				if (!scrollbarTiles.get(i).equals(Id.Empty)) {
					g.drawImage(scrollbarTiles.get(i).getImage().getBufferedImage(),
							(i * 74) + 34 + Game.player.getX() - 650, 36 + Game.player.getY() - 450, 32, 32, null);
				}
				if (scrollbarTiles.get(i).getType().equals("block")) {
					g.setColor(Color.white);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
					g.drawString(Utils.toString(scrollbar_amount[i]), (i * 74) + Game.player.getX() - 626,
							Game.player.getY() - 377);
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
			if (!scrollbarTiles.get(Mouse.mouseRotation).getType().equals("block")) {
				if (scrollbarTiles.get(Mouse.mouseRotation).getBlock()
						.equalsIgnoreCase(Mouse.degradedTile.getId().toString())) {
					Mouse.degradedTile.addDamage(50);
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
						// new Packet10RemoveTile(ti.getX(),
						// ti.getY()).send(Game.client);

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

						ti.setAsRemoved();
						itemdeployed = false;
						itemexists = false;
					}
				}
			}
		}
		for (int i = 0; i < 40; i++) {
			if (Game.player.Inventory.get(i).getType().equals("block") && Game.player.Inventory_amount[i] == 0) {
				Game.player.Inventory.set(i, Id.Empty);
			}
		}
		if (tick < 30) {
			tick++;
		}
	}
}
