package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import terracraft.Game;

public class Settings extends Element {

	public Slider sound_slider, soundvol_slider, musicvol_slider;
	public Text sound_text, soundvol_text, musicvol_text;

	public Settings(int x, int y, int width, int height) {
		super(x, y, width, height);
		sound_text = new Text(x+20,y+65,"Gesamtlautstärke",blue,normal);
		sound_slider = new Slider(x + 25, y + 25, width - 50, height - 390, sound_text);
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(200, 200, 200, 200));
		RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, width, height, 50, 50);
		g2d.fill(roundedRectangle);
		g2d.draw(roundedRectangle);
		sound_text.text = "Gesamtlautstärke: " + (sound_slider.ex / 2 - 140);
		sound_slider.render(g2d);
		sound_text.render(g2d);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
