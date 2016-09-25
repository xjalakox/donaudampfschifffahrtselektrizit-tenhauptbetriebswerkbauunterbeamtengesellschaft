package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Button extends Element {

	private Color color;

	public Button(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.color = color;

	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(color);
		RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x + 25, y, width, height, 25, 25);
		g2d.fill(roundedRectangle);
		g2d.draw(roundedRectangle);

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
