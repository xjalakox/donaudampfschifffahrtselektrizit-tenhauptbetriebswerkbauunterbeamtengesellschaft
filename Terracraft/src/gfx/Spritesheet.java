package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage sheet;

	public Spritesheet(String path){
		try {
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {}
	}
	
	public BufferedImage getSprite(int x,int y,int a,int b){
		return sheet.getSubimage(x*16-16, y*16-16, 16*a, 16*b);
	}
}
