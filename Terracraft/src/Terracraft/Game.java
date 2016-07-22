package Terracraft;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import network.Client;
import network.mysql.Login;
import network.packets.Packet00Login;
import network.packets.Packet02Move;
import Entity.Player;
import Input.Key;
import Input.Mouse;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int breite = 320, höhe = breite / 180, scale = 4;
	public static boolean running = false;
	private static Thread thread = new Thread();
	public static Handler handler;
	public static Player player;
	public static Client client;
	private int x, y, networktick;
	private Key key;
	public static Mouse m = new Mouse();
	public void init() {
		Login.frame.dispose();
		handler = new Handler();
		key = new Key();
		player = new Player(client.getUsername(), x, y, 24, 24, Id.player, key);
		handler.addEntity(player);
		new Packet00Login(player.getUsername(), player.getX(), player.getY()).send(client);

		addMouseListener(m);
		addMouseMotionListener(m);
		addMouseListener(m);
		addKeyListener(new Key());
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(4);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		handler.render(g);
		renderLookingBlock(g);
		g.dispose();
		bs.show();
	}

	private void renderLookingBlock(Graphics g) {
		int x = 0;
		int y = 0;
		int x2 = m.getX();
		int y2 = m.getY();
		while (x2 >= 60) {
			x += 60;
			x2 -= 60;
		}
		while (y2 >= 60) {
			y += 60;
			y2 -= 60;
		}
		g.setColor(Color.RED);
		g.drawRect(x, y, 64, 64);
	}

	public void tick() {
		if (networktick == 2) {
			networktick = 0;
			for (Entity.Entity e : Handler.entity) {
				if (e.getId() == Id.player) {
					new Packet02Move(((Player) e).getUsername(), ((Player) e).getX(), ((Player) e).getY()).send(client);
				}
			}
		} else {
			networktick++;
		}
		handler.tick();
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();

		// client = new Client(this, 64);
		// client.start();
	}

	public synchronized static void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
		}
	}

	public void run() {
		init();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double nanoseconds = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoseconds;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;

				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}

	public Game() {
		Dimension size = new Dimension(breite * scale, höhe * scale);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}

	public Game(int x, int y, Client client) {
		this.x = x;
		this.y = y;
		this.client = client;
		Dimension size = new Dimension(breite * scale, höhe * scale);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}

	public static int getFrameBreite() {
		return breite * scale;
	}

	public static int getFrameHöhe() {
		return höhe * scale;
	}

}