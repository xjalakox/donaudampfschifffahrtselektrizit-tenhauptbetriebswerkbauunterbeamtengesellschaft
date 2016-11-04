package gfx;

import java.awt.image.BufferedImage;

public class Sprite {

	public Spritesheet sheet;
	public BufferedImage image;

	public Sprite(Spritesheet sheet, int x, int y, int a, int b) {
		image = sheet.getSprite(x, y, a, b);
	}

	public Sprite(Spritesheet sheet, int x, int y, int a, int b, int i, int abstand) {
		image = sheet.getSprite2(x, y, a, b, i, abstand);
	}

	public BufferedImage getBufferedImage() {
		return image;
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}
}
