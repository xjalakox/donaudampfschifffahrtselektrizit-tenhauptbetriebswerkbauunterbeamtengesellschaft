package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import terracraft.Game;

public class Settings extends Element {

	public Slider soundvol_slider, musicvol_slider;
	public Text soundvol_text, musicvol_text;

	public Settings(int x, int y, int width, int height) {
		super(x, y, width, height);
		musicvol_text = new Text(x + 25, y + 65, "Musiklautstärke", blue, normal);
		musicvol_slider = new Slider(x + 25, y + 25, width - 50, height - 390, musicvol_text);

		soundvol_text = new Text(x + 25, y + 130, "Soundlautstärke", blue, normal);
		soundvol_slider = new Slider(x + 25, y + 100, width - 50, height - 390, soundvol_text);
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(200, 200, 200, 200));
		RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, width, height, 50, 50);
		g2d.fill(roundedRectangle);
		g2d.draw(roundedRectangle);

		musicvol_text.text = "Musiklautstärke: " + ((musicvol_slider.ex / 2) - 140);

		musicvol_slider.render(g2d);
		musicvol_text.render(g2d);

		soundvol_text.text = "Soundlautstärke: " + ((soundvol_slider.ex / 2) - 140);

		soundvol_slider.render(g2d);
		soundvol_text.render(g2d);

		Game.sm.setVolume(0, ((musicvol_slider.ex / 2) - 140 - musicvol_slider.ex / 8 - 40));

		Game.sm.setVolume(1, ((soundvol_slider.ex / 2) - 140 - soundvol_slider.ex / 8 - 40));
	}

	@Override
	public void tick() {

	}

}
