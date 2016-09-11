package gfx;

import java.awt.image.BufferedImage;

public class Sprite2 {
	
	public Spritesheet2 sheet;
	public BufferedImage image;
	
	public Sprite2(Spritesheet2 sheet, int x,int y, int a,int b){
		image = sheet.getSprite(x,y,a,b);
	}
	
	public BufferedImage getBufferedImage(){
		return image;
	}
	
	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight(){
		return image.getHeight();
	}
}
