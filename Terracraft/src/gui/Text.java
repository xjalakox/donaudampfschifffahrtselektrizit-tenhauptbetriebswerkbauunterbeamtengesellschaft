package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Text extends Element {

	private String text;
	private Font font;
	private Color color;

	public Text(int x, int y, String text, Color color, Font font) {
		super(x, y, 0, 0);
		this.text = text;
		this.color = color;
		this.font = font;
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setFont(font);
		g2d.setColor(color);
		g2d.drawString(text, x, y);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
