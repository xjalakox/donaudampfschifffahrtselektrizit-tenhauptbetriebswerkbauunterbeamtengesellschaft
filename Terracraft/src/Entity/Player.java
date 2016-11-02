package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import crafting.Recipe;
import gfx.Sprite;
import gfx.Sprite2;
import input.Key;
import input.Mouse;
import net.Network;
import net.Network.HittingBlock;
import terracraft.Game;
import terracraft.Id;
import terracraft.MiningHandler;
import terracraft.Utils;
import tile.source.Tile;
import tile.Door;

public class Player extends Entity {
	private String username;
	public Id tool;
	public int ToolX, ToolY;
	private Sprite2[] armor = new Sprite2[20];
	private Sprite2[] legs = new Sprite2[21];
	private Sprite2[] head = new Sprite2[21];
	private Sprite2[] body = new Sprite2[21];
	private Sprite2[] armor_head = new Sprite2[21];
	public int spritex, spritey;
	public boolean fly;
	public ArrayList<Id> Inventory = new ArrayList<Id>();
	public int[] Inventory_amount = new int[40];
	public boolean inventoryOpen;
	private boolean gotRecipes = false;
	public Recipe[] recipes = new Recipe[0];

	public Player(String username, int x, int y, int width, int height, Id id) {
		super(x, y, width, height, Game.handler, id);
		this.username = username;
		for (int i = 1; i < armor.length; i++) {
			armor[i] = new Sprite2(Game.sheet_armor, 1, i, 1, 1);
			legs[i] = new Sprite2(Game.sheet_legs, 1, i, 1, 1);
			head[i] = new Sprite2(Game.sheet_head, 1, i, 1, 1);
			body[i] = new Sprite2(Game.sheet_body, 1, i, 1, 1);
			armor_head[i] = new Sprite2(Game.sheet_armor_head, 1, i, 1, 1);
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 4; j++) {
				Inventory.add(Id.Empty);
				Inventory_amount[10 * j + i] = 0;
			}
		}
	}

	public void render(Graphics g) {
		// g.setColor(Color.blue);
		// g.drawRect(getX() + 6, getY() + height - 16, width - 10, 16);
		// g.setColor(Color.green);
		// g.drawRect(getX() + width - 5, getY() + 5, 5, height - 10);
		// g.setColor(Color.black);
		// g.drawRect(getX(), getY() + 5, 5, height - 10);
		// g.setColor(Color.cyan);
		drawPlayer(g);

		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(getUsername(), getX(), getY() - 10);
		if (!isInventoryOpen()) {
			setGotRecipes(false);
		}

	}

	public void tick() {
		x += velX;
		y += velY;

		for (Tile ti : handler.tile2) {
			if (ti.getId().equals(Id.Grass) || ti.getId().equals(Id.Dirt) || ti.getId().equals(Id.Stone)
					|| ti.getId().equals(Id.Copper) || ti.getId().equals(Id.Gold) || ti.getId().equals(Id.Platinum)) {
				testForCollision(ti);
			} else if (ti.getId().equals(Id.Door)) {
				if (!((Door) ti).isOpen()) {
					testForCollision(ti);
				}
			}

		}

		// Movement
		if (Key.d) {
			moving = 1;
			setVelX(4);
		}

		if (Key.a) {
			moving = 2;
			setVelX(-4);
		}

		if (Key.w) {
			if (fly) {
				setVelY(-4);
			}
		}
		if (Key.s) {
			if (fly) {
				setVelY(4);
			}
		}

		if (jumping) {
			if (!fly) {
				jumping(0.5f);
			}
		}

		if (falling) {
			if (!fly) {
				falling();
			}
		}

		framedelay2++;
		if (framedelay2 >= 7) {
			frame2++;
			if (frame2 >= 4) {
				frame2 = 0;
				click = false;
				HittingBlock request = new HittingBlock();
				request.click = false;
				request.username = getUsername();
				if (clicked == false) {
					request.clicked = false;
					Game.client.sendUDP(request);
				} else {
					request.clicked = true;
					Game.client.sendUDP(request);
				}
			}
			framedelay2 = 0;
		}

		if (framereset == true) {
			frame2 = 0;
			framedelay2 = 0;
		}

		framedelay++;
		if (framedelay >= 4) {
			frame++;
			if (frame >= 13) {
				frame = 0;
			}
			framedelay = 0;
		}

	}

	public void setTool(Id tool, int x, int y) {
		this.tool = tool;
		this.ToolX = x;
		this.ToolY = y;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void drawPlayer(Graphics g) {

		if (moving == -1 && !jumping && !falling) {
			g.drawImage(legs[1].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			g.drawImage(head[1].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			if (!click && !clicked) {
				g.drawImage(body[1].getBufferedImage(), x - 10, y + 2, 70, 96, null);
				g.drawImage(armor[1].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			}
			g.drawImage(armor_head[1].getBufferedImage(), x - 10, y + 2, 70, 96, null);
		}

		if (moving == -2 && !jumping && !falling) {
			g.drawImage(legs[1].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			g.drawImage(head[1].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			if (!click && !clicked) {
				g.drawImage(body[1].getBufferedImage(), x + 56, y + 2, -70, 96, null);
				g.drawImage(armor[1].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			}
			g.drawImage(armor_head[1].getBufferedImage(), x + 56, y + 2, -70, 96, null);
		}

		if (moving == 2 && !jumping && !falling) {
			g.drawImage(legs[frame + 7].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			if (!click && !clicked) {
				g.drawImage(body[frame + 7].getBufferedImage(), x + 56, y + 2, -70, 96, null);
				g.drawImage(armor[frame + 7].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			}
			g.drawImage(head[frame + 7].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			g.drawImage(armor_head[frame + 7].getBufferedImage(), x + 56, y + 2, -70, 96, null);
		}

		if (moving == 1 && !jumping && !falling) {
			g.drawImage(legs[frame + 7].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			if (!click && !clicked) {
				g.drawImage(body[frame + 7].getBufferedImage(), x - 10, y + 2, 70, 96, null);
				g.drawImage(armor[frame + 7].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			}
			g.drawImage(head[frame + 7].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			g.drawImage(armor_head[frame + 7].getBufferedImage(), x - 10, y + 2, 70, 96, null);
		}

		if (moving == 1 && jumping && !falling || moving == -1 && jumping && !falling) {
			g.drawImage(legs[6].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			if (!click && !clicked) {
				g.drawImage(body[1].getBufferedImage(), x - 10, y + 2, 70, 96, null);
				g.drawImage(armor[1].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			}
			g.drawImage(head[1].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			g.drawImage(armor_head[1].getBufferedImage(), x - 10, y + 2, 70, 96, null);
		}

		if (moving == 1 && !jumping && falling || moving == -1 && !jumping && falling) {
			g.drawImage(legs[6].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			if (!click && !clicked) {
				g.drawImage(body[6].getBufferedImage(), x - 10, y + 2, 70, 96, null);
				g.drawImage(armor[6].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			}
			g.drawImage(head[6].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			g.drawImage(armor_head[6].getBufferedImage(), x - 10, y + 2, 70, 96, null);
		}

		if (moving == 2 && jumping && !falling || moving == -2 && jumping && !falling) {
			g.drawImage(legs[6].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			if (!click && !clicked) {
				g.drawImage(body[1].getBufferedImage(), x + 56, y + 2, -70, 96, null);
				g.drawImage(armor[1].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			}
			g.drawImage(head[1].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			g.drawImage(armor_head[1].getBufferedImage(), x + 56, y + 2, -70, 96, null);
		}

		if (moving == 2 && !jumping && falling || moving == -2 && !jumping && falling) {
			g.drawImage(legs[6].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			if (!click && !clicked) {
				g.drawImage(body[6].getBufferedImage(), x + 56, y + 2, -70, 96, null);
				g.drawImage(armor[6].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			}
			g.drawImage(head[6].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			g.drawImage(armor_head[6].getBufferedImage(), x + 56, y + 2, -70, 96, null);
		}

		if (clicked || click) {
			framereset = false;
			if (moving == 2 || moving == -2) {
				Graphics2D g2d;
				g2d = (Graphics2D) g.create();
				g2d.rotate(Math.toRadians(rotateAnglesLeft[frame2]), x + 32, y + 54);
				if (MiningHandler.equippedTool != null) {
					g2d.drawImage(MiningHandler.equippedTool.getImage().getBufferedImage(), x + 44, y + 8, 32, 32,
							null);
				}
				g2d.dispose();
				g.drawImage(body[2 + frame2].getBufferedImage(), x + 56, y + 2, -70, 96, null);
				g.drawImage(armor[2 + frame2].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			}
			if (moving == 1 || moving == -1) {
				Graphics2D g2d;
				g2d = (Graphics2D) g.create();
				g2d.rotate(Math.toRadians(rotateAnglesRight[frame2]), x + 32, y + 54);
				if (MiningHandler.equippedTool != null) {
					if (frame2 == 0) {
						g2d.drawImage(MiningHandler.equippedTool.getImage().getBufferedImage(), x + 30, y - 5, 32, 32,
								null);
					} else if (frame2 == 1) {
						g2d.drawImage(MiningHandler.equippedTool.getImage().getBufferedImage(), x + 30, y + 10, 32, 32,
								null);
					} else if (frame2 == 2) {
						g2d.drawImage(MiningHandler.equippedTool.getImage().getBufferedImage(), x + 40, y + 25, 32, 32,
								null);
					} else if (frame2 == 3) {
						g2d.drawImage(MiningHandler.equippedTool.getImage().getBufferedImage(), x + 40, y + 30, 32, 32,
								null);
					}
				}
				g2d.dispose();
				g.drawImage(body[2 + frame2].getBufferedImage(), x - 10, y + 2, 70, 96, null);
				g.drawImage(armor[2 + frame2].getBufferedImage(), x - 10, y + 2, 70, 96, null);
			}
		} else {
			framereset = true;
		}
	}

	public boolean isInventoryOpen() {
		return inventoryOpen;
	}

	public void setInventoryOpen(boolean inventoryOpen) {
		this.inventoryOpen = inventoryOpen;
	}

	public void sendPosition() {
		Network.sendCoordinates(Game.client, x, y, username, MiningHandler.equippedTool, "udp");
	}

	public void renderLookingBlock(Graphics g) {
		int x = 0;
		int y = 0;
		int x2 = Game.m.getX() - Game.cam.getX();
		int y2 = Game.m.getY() - Game.cam.getY();
		/**
		 * Der Code sieht nach Cancer aus Und er ist Hodenkrebs im Endstadium...
		 */
		if (x2 > 0) {
			while (x2 >= 32) {
				x += 32;
				x2 -= 32;
			}
		} else if (x2 < 0) {
			while (x2 <= 32) {
				x -= 32;
				x2 += 32;
			}
			x += 32;
		}
		if (y2 > 0) {
			while (y2 >= 32) {
				y += 32;
				y2 -= 32;
			}

		} else if (y2 < 0) {
			while (y2 <= 32) {
				y -= 32;
				y2 += 32;
			}
			y += 32;
		}
		Game.m.lookingAtX = x;
		Game.m.lookingAtY = y;
		g.setColor(Color.RED);
	}

	public void testForCollision(Tile ti) {
		if (getTop().intersects(ti.getBottom())) {
			setVelY(0);
			y = ti.getY() + 33;
			jumping = false;
			falling = true;
			gravity = 0;

		}
		if (getBottom().intersects(ti.getTop())) {
			setVelY(0);

			y = ti.getY() - 87;

		} else if (!jumping) {
			falling = true;
		}

		if (getLeft().intersects(ti.getRight())) {
			setVelX(0);
			x = ti.getX() + 33;

		}
		if (getRight().intersects(ti.getLeft())) {
			setVelX(0);
			x = ti.getX() - 46;

		}
	}

	public boolean isGotRecipes() {
		return gotRecipes;
	}

	public void setGotRecipes(boolean gotRecipes) {
		this.gotRecipes = gotRecipes;
	}

	public Rectangle InventoryBounds() {
		return new Rectangle(20 + getX() - 650, 20 + getY() - 450, 74 * 10, 74 * 4);
	}

	public Rectangle closedInventoryBounds() {
		return new Rectangle(20 + getX() - 650, 20 + getY() - 450, 74 * 10, 74);
	}

	public Rectangle recipeBounds() {
		if (recipes.length >= 1) {
			return new Rectangle(getX() - 615 - 7, getY() - 140, 48, recipes.length * 48);
		} else {
			return new Rectangle(getX() - 615 - 7, getY() - 140 + 0 * 48, 0, 0);
		}

	}
}