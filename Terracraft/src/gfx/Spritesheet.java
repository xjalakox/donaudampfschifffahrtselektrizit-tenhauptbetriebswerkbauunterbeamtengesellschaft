package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage sheet;

	public Spritesheet(String path) {
		try {
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
		}
	}

	public BufferedImage getSprite(int x, int y, int a, int b) {
		return sheet.getSubimage(x * 32 - 32, y * 32 - 32, 32 * a, 32 * b);
	}

	public BufferedImage getSprite2(int x, int y, int a, int b, int i, int abstand) {
		return sheet.getSubimage(x * i - i + abstand * (x - 1), y * i - i + abstand * (y - 1), i * a, i * b);
	}
}
