package gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class Slider extends Element{

	public Slider(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(200,200,200,200));
		g2d.drawRect(x, y, width, height);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}


}
