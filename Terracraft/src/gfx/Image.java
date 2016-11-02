package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {

	private BufferedImage image;

	public Image(String path) {

		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
		}
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
