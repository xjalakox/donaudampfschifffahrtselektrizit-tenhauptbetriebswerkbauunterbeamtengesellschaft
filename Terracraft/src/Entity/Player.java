package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import Input.Key;
import Input.Mouse;
import Terracraft.Game;
import Terracraft.Id;
import Terracraft.MiningHandler;
import Terracraft.Utils;
import Tile.source.Tile;
import gfx.Sprite;
import gfx.Sprite2;
import network.packets.Packet11Mine;

public class Player extends Entity {
	private String username;
	private Key key;
	public Id tool;
	public int ToolX, ToolY;
	private Sprite2[] armor = new Sprite2[20];
	private Sprite2[] legs = new Sprite2[21];
	private Sprite2[] head = new Sprite2[21];
	private Sprite2[] body = new Sprite2[21];
	private Sprite2[] armor_head = new Sprite2[21];
	private Sprite inventory_background=new Sprite(Game.sheet,1,1,1,1);
	public int spritex, spritey;
	public boolean fly;
	public ArrayList<Id> Inventory = new ArrayList<Id>();
	public int []Inventory_amount=new int[30];
	public boolean inventoryOpen;
	public Player(String username, int x, int y, int breite, int höhe, Id id, Key key) {
		super(x, y, breite, höhe, Game.handler, id);
		this.key = key;
		this.username = username;
		for (int i = 1; i < armor.length; i++) {
			armor[i] = new Sprite2(Game.sheet_armor, 1, i, 1, 1);
			legs[i] = new Sprite2(Game.sheet_legs, 1, i, 1, 1);
			head[i] = new Sprite2(Game.sheet_head, 1, i, 1, 1);
			body[i] = new Sprite2(Game.sheet_body, 1, i, 1, 1);
			armor_head[i] = new Sprite2(Game.sheet_armor_head, 1, i, 1, 1);
		}
		for(int i=0;i<10;i++){
			for(int j=0;j<3;j++){
				Inventory.add(Id.Empty);
				Inventory_amount[10*j+i]=0;
			}
		}
		
		Inventory.set(15,Id.Grass);
		Inventory_amount[15]=15;
	}	

	public Player(String username, int x, int y, int breite, int höhe, Id id) {
		super(x, y, breite, höhe, Game.handler, id);
		this.username = username;
		for (int i = 1; i < armor.length; i++) {
			armor[i] = new Sprite2(Game.sheet_armor, 1, i, 1, 1);
			legs[i] = new Sprite2(Game.sheet_legs, 1, i, 1, 1);
			head[i] = new Sprite2(Game.sheet_head, 1, i, 1, 1);
			body[i] = new Sprite2(Game.sheet_body, 1, i, 1, 1);
			armor_head[i] = new Sprite2(Game.sheet_armor_head, 1, i, 1, 1);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(x, y, breite, höhe);
		g.setColor(Color.blue);
		g.drawRect(getX() + 6, getY() + höhe - 16,  breite-10, 16);
		g.setColor(Color.green);
		g.drawRect(getX() + breite - 5, getY() + 5, 5, höhe - 10);
		g.setColor(Color.black);
		g.drawRect(getX(), getY() + 5, 5, höhe - 10);
		g.setColor(Color.cyan);
		g.drawRect(getX() + 5, getY(),  breite-10, 16);
		Zeichnung(g);

		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(getUsername(), getX(), getY() - 10);
		if(isInventoryOpen()){
			Inventory(g);
		}
	}

	public void tick() {
		x += velX;
		y += velY;

		for (Tile ti : handler.tile2) {

			if (getTop().intersects(ti.getBottom())) {
				setVelY(0);
				y = ti.getY() + 33;
				jumping = false;
				falling = true;
				gravity = 0;

			}
			if (getBottom().intersects(ti.getTop())) {
				setVelY(0);

				y = ti.getY() - 90;

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

		// Movement
		if (key.d) {
			moving = 1;
			setVelX(4);
		}

		if (key.a) {
			moving = 2;
			setVelX(-4);
		}

		if (key.w) {
			if (fly) {
				setVelY(-4);
			}
		}
		if (key.s) {
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
				if (clicked == false) {
					new Packet11Mine(0, 0, getUsername()).send(Game.client);
				} else {
					new Packet11Mine(0, 1, getUsername()).send(Game.client);
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


	public void Zeichnung(Graphics g) {

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
	
	public void Inventory(Graphics g){
		for(int i=0;i<10;i++){
			for(int j=0;j<3;j++){
					g.drawImage(inventory_background.getBufferedImage(), i * 74 + 20 + getX()- 650, 20 + getY()-450+74*j+74, 64, 64, null);
					if(!Inventory.get(j*10+i).equals(Id.Empty)){
						g.drawImage(Inventory.get(j*10+i).getImage().getBufferedImage(), i * 74 + 20+16 + getX()- 650, 20 + getY()+16-450+74*j+74, 32, 32, null);
							if(Inventory.get(j*10+i).getType().equals("block")){
								g.setColor(Color.white);
								g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
								g.drawString(Utils.toString(Inventory_amount[j*10+i]), i * 74+ getX()- 626, getY()+74*j-300);
							}
					}
				
				
				
			}
		}
		if(!Game.m.mouseItem.equals(Id.Empty)){
			g.drawImage(Game.m.mouseItem.getImage().getBufferedImage(), Game.m.lookingAtX, Game.m.lookingAtY,32,32, null);
		}
	}
	public boolean isInventoryOpen() {
		return inventoryOpen;
	}

	public void setInventoryOpen(boolean inventoryOpen) {
		this.inventoryOpen = inventoryOpen;
	}
	
	public Rectangle InventoryBounds(){
		return new Rectangle(  20 + getX()- 650-5, 20 + getY()-450-5, 74*10, 74*4);
	}


}