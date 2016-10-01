package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class Slider extends Element{
	
	public Ellipse2D e;
	public int ex;

	public Slider(int x, int y, int width, int height, Text text) {
		super(x, y, width, height);

		ex = x + 100;
		e = new Ellipse2D.Float(ex,y-6,25,25);
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(0,0,0,200));
		RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, width, height, 10, 10);
		g2d.fill(roundedRectangle);
		g2d.draw(roundedRectangle);
		

		g2d.setColor(new Color(255,0,0,255));
		e = new Ellipse2D.Float(ex,y-6,25,25);
		g2d.fill(e);
		g2d.draw(e);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}


}
