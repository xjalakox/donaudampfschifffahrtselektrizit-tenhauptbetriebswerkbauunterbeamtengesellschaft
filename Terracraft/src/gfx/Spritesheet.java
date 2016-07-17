package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage sheet;
	public static int a,b;
	
	public Spritesheet(String path){
		try {
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {}
	}
	
	public BufferedImage getSprite(int x,int y,int a,int b){
		this.a=a;
		this.b=b;
		return sheet.getSubimage(x*32-32, y*32-32, 32*a, 32*b);
	}
}
