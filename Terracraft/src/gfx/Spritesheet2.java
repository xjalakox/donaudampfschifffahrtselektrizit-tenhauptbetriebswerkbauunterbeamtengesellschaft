package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet2 {

	private BufferedImage sheet;

	public Spritesheet2(String path){
		try {
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {}
	}
	
	public BufferedImage getSprite(int x,int y,int a,int b){
		return sheet.getSubimage(x*30-30, y*29-29, 30*a, 29*b);
	}
}
