package gui;

import java.awt.Graphics2D;

public class Settings extends Element {
	
	private Slider slider;

	public Settings(int x, int y, int width, int height) {
		super(x, y, width, height);
		slider = new Slider(x+125,y+100,width-100,20);
	}

	@Override
	public void render(Graphics2D g2d) {
		slider.render(g2d);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}


}
