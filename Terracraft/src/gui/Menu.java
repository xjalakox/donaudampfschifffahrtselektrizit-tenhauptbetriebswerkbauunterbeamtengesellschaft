package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Menu extends Element {

	private int tick = 0;
	private boolean MenuIsOpen;
	private boolean renderSettings;

	private Font small, normal, big, large, extralarge;
	private Color color;
	private Text quit_button_text;
	private Text settings_button_text;
	public Button quit_button;
	public Button settings_button;
	public Settings settings;

	public Menu(int x, int y, int width, int height) {
		super(x, y, width, height);
		tick = y;
		color = new Color(0, 160, 20, 200);
		small = new Font("serif", Font.BOLD, 12);
		normal = new Font("serif", Font.BOLD, 24);
		big = new Font("serif", Font.BOLD, 36);
		large = new Font("serif", Font.BOLD, 48);
		extralarge = new Font("serif", Font.BOLD, 60);
		quit_button = new Button(x, y + 175, 200, 60, color);
		settings_button = new Button(x, y + 260, 200, 60, color);
		quit_button_text = new Text(x + 90, quit_button.y, "Quit", new Color(255, 0, 0, 255), big);
		settings_button_text = new Text(x + 65, settings_button.y, "Settings", new Color(255, 0, 0, 255), big);

		settings = new Settings(x, y, 250, 250);
	}

	@Override
	public void render(Graphics2D g2d) {
		if (renderSettings) {

		}
		if (MenuIsOpen) {
			g2d.setColor(new Color(50, 50, 200, 200));
			RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, width, height, 50, 50);
			g2d.fill(roundedRectangle);
			g2d.draw(roundedRectangle);

			quit_button.render(g2d);
			settings_button.render(g2d);

			quit_button_text.render(g2d);
			settings_button_text.render(g2d);

			settings.render(g2d);
		}

	}

	@Override
	public void tick() {
		if (!MenuIsOpen) {
			y = tick;
			quit_button.y = tick;
			settings_button.y = tick;
			quit_button_text.y = tick;
			settings_button_text.y = tick;
			settings.y = tick;
		}
		if (y < tick + 500 && MenuIsOpen) {
			y += 25;
			quit_button.y = y + 10;
			settings_button.y = y + 100;
			quit_button_text.y = y + 50;
			settings_button_text.y = y + 140;
			settings.y = y + 10;
		} else {
		}
	}

	public void open() {
		MenuIsOpen = true;
	}

	public boolean isOpen() {
		return MenuIsOpen;
	}

	public void close() {
		MenuIsOpen = false;
	}

	public void renderSettings(boolean setting) {
		renderSettings = setting;
	}

}
