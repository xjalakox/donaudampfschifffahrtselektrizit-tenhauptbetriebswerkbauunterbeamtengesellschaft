package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import gfx.Sprite2;
import terracraft.Game;
import terracraft.Id;

public class NetPlayer extends Entity {

	private String username;

	private Sprite2[] armor = new Sprite2[20];
	private Sprite2[] legs = new Sprite2[21];
	private Sprite2[] head = new Sprite2[21];
	private Sprite2[] body = new Sprite2[21];
	private Sprite2[] armor_head = new Sprite2[21];
	public int spritex, spritey;
	private Id tool;
	private int xnetwork, ynetwork;
	private boolean goRight, goLeft, goUp, goDown, still;

	public NetPlayer(String username, int x, int y, int width, int height, Id id) {
		super(x, y, width, height, Game.handler, id);
		this.username = username;
		for (int i = 1; i < armor.length; i++) {
			armor[i] = new Sprite2(Game.sheet_armor, 1, i, 1, 1);
			legs[i] = new Sprite2(Game.sheet_legs, 1, i, 1, 1);
			head[i] = new Sprite2(Game.sheet_head, 1, i, 1, 1);
			body[i] = new Sprite2(Game.sheet_body, 1, i, 1, 1);
			armor_head[i] = new Sprite2(Game.sheet_armor_head, 1, i, 1, 1);
		}
	}

	public void render(Graphics2D g) {

		g.setColor(Color.blue);
		g.drawRect(x, y, width, height);
		drawPlayer(g);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(getUsername(), getX(), getY() - 10);

	}

	public void tick() {
		if (goLeft) {
			x -= 4;
			moving = 2;
		}
		if (goRight) {
			x += 4;
			moving = 1;
		}
		if (goUp) {
			jumping=true;
			falling =false;
			y -= 4;
		}
		if (goDown) {
			falling=true;
			jumping=false;
			y += 4;
		}

		if (still) {
			if (moving == 2) {
				moving = -2;
			} else if (moving == 1) {
				moving = -1;
			}
			jumping = false;
			falling = false;
		}
		if (framereset == true) {
			frame2 = 0;
			framedelay2 = 0;
		}

		framedelay2++;
		if (framedelay2 >= 7) {
			frame2++;
			if (frame2 >= 4) {
				frame2 = 0;
				click = false;

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

	public void setDirectionGoing(int x, int y) {
		this.x = x;
		this.y = y;
		if (x > xnetwork) {
			goLeft = false;
			goRight = true;
			still = false;
		} else if (x < xnetwork) {
			goRight = false;
			goLeft = true;
			still = false;
		} else {
			goRight = false;
			goLeft = false;
			still = true;
		}

		if (y < ynetwork) {
			goDown = false;
			goUp = true;
			still = false;
		} else if (y > ynetwork) {
			goUp = false;
			goDown = true;
			still = false;
		} else {
			goDown = false;
			goUp = false;
		}
		xnetwork = x;
		ynetwork = y;
	}

	public void setTool(Id tool) {
		this.tool = tool;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void drawPlayer(Graphics2D g) {

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
				if (tool != null) {
					g2d.drawImage(tool.getImage().getBufferedImage(), x + 44, y + 8, 32, 32, null);
				}
				g2d.dispose();
				g.drawImage(body[2 + frame2].getBufferedImage(), x + 56, y + 2, -70, 96, null);
				g.drawImage(armor[2 + frame2].getBufferedImage(), x + 56, y + 2, -70, 96, null);
			}
			if (moving == 1 || moving == -1) {
				Graphics2D g2d;
				g2d = (Graphics2D) g.create();
				g2d.rotate(Math.toRadians(rotateAnglesRight[frame2]), x + 32, y + 54);
				if (tool != null) {
					if (frame2 == 0) {
						g2d.drawImage(tool.getImage().getBufferedImage(), x + 30, y - 5, 32, 32, null);
					} else if (frame2 == 1) {
						g2d.drawImage(tool.getImage().getBufferedImage(), x + 30, y + 10, 32, 32, null);
					} else if (frame2 == 2) {
						g2d.drawImage(tool.getImage().getBufferedImage(), x + 40, y + 25, 32, 32, null);
					} else if (frame2 == 3) {
						g2d.drawImage(tool.getImage().getBufferedImage(), x + 40, y + 30, 32, 32, null);
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
}
