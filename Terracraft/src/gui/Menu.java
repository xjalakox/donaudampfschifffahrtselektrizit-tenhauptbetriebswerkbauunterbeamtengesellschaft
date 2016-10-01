package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Menu extends Element {

	private int tick = 0;
	private boolean MenuOpen;
	private boolean renderSettings;

	private Text quit_button_text;
	private Text settings_button_text;
	public Button quit_button;
	public Button settings_button;
	public Settings settings;

	public Menu(int x, int y, int width, int height) {
		super(x, y, width, height);
		tick = y;
		quit_button = new Button(x, y + 175, 200, 60, green);
		settings_button = new Button(x, y + 260, 200, 60, green);
		quit_button_text = new Text(x + 90, quit_button.y, "Quit", orange, big);
		settings_button_text = new Text(x + 65, settings_button.y, "Settings", orange, big);

		settings = new Settings(x - 275, y + 500, width, height);
	}

	@Override
	public void render(Graphics2D g2d) {
		if (renderSettings) {

			settings.render(g2d);
		}
		if (MenuOpen) {
			g2d.setColor(new Color(50, 50, 200, 155));
			RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, width, height, 50, 50);
			g2d.fill(roundedRectangle);
			g2d.draw(roundedRectangle);

			quit_button.render(g2d);
			settings_button.render(g2d);

			quit_button_text.render(g2d);
			settings_button_text.render(g2d);

		}

	}

	@Override
	public void tick() {
		if (!MenuOpen) {
			if (y != tick) {
				y = tick;
				quit_button.y = tick;
				settings_button.y = tick;
				quit_button_text.y = tick;
				settings_button_text.y = tick;
				settings.y = tick;
				settings.musicvol_slider.y = tick;
				settings.soundvol_slider.y = tick;
			}
		} else if (y < tick + 500 && MenuOpen) {
			y += 25;
			quit_button.y = y + 10;
			settings_button.y = y + 100;
			quit_button_text.y = y + 50;
			settings_button_text.y = y + 140;
			settings.y = y;
			settings.musicvol_slider.y = y + 25;
			settings.soundvol_slider.y = y + 85;
		}
	}

	public void open() {
		MenuOpen = true;

		renderSettings = false;
	}

	public boolean isOpen() {
		return MenuOpen;
	}

	public void close() {
		MenuOpen = false;
	}

	public void renderSettings(boolean setting) {
		renderSettings = setting;
	}

	public boolean isRenderingSettings() {
		return renderSettings;
	}

}
