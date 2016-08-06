package Terracraft;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Entity.Dragon;
import Entity.Entity;
import Entity.Player;
import Input.Key;
import Input.Mouse;
import audio.SoundManager;
import gfx.Sprite;
import gfx.Spritesheet;
import gfx.Spritesheet2;
import network.Client;
import network.mysql.Login;
import network.packets.Packet00Login;
import network.packets.Packet02Move;
import network.packets.Packet07AddTile;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int breite = 320, höhe = 180, scale = 4;
	public static boolean running = false;
	private static Thread thread = new Thread();
	public static Handler handler;
	public static Player player;
	public static Camera cam;
	private int rendertick = 0;
	public static Client client;
	private int x, y, networktick;
	private Key key;
	public static Mouse m = new Mouse();
	public static boolean consoleOpen;
	public static String TextToDrawInConsole = "";
	public static Spritesheet sheet = new Spritesheet("/Spritesheet.png");
	public Dragon dragon;
	public static SoundManager sm = new SoundManager();
	public static MiningHandler mininghandler = new MiningHandler();

	public static Spritesheet2 sheet_armor = new Spritesheet2("/Armor.png");
	public static Spritesheet2 sheet_legs = new Spritesheet2("/Legs.png");
	public static Spritesheet2 sheet_head = new Spritesheet2("/Head.png");
	public static Spritesheet2 sheet_body = new Spritesheet2("/Body.png");
	public static Spritesheet2 sheet_armor_head = new Spritesheet2("/Armor_Head.png");
	private JFrame frame;

	public void init() {
		mininghandler.init();
		Login.frame.dispose();
		handler = new Handler("Client");
		cam = new Camera();
		key = new Key();
		player = new Player(client.getUsername(), x, y, 46, 96, Id.Player, key);

		dragon=new Dragon(x-1000,300,64,64,handler,Id.Drache);

		handler.addEntity(player);
		handler.addEntity(dragon);
		new Packet00Login(player.getUsername(), player.getX(), player.getY()).send(client);
		addMouseListener(m);
		addMouseMotionListener(m);
		addKeyListener(new Key());
		addMouseWheelListener(m);
		requestFocus();

		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon(new Sprite(sheet, 5, 1, 1, 1).getBufferedImage()).getImage(), new Point(0, 0),
				"custom cursor"));
		// sm.playSound(0);
		m.mouseItem = Id.Empty;
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.translate(cam.getX(), cam.getY());
		mininghandler.render(g);
		handler.render(g);
		doConsoleStuff(g);

		g.dispose();
		bs.show();
	}

	public void tick() {
		for (Entity e : handler.entity) {
			if (e.getId() == Id.Player) {
				cam.tick(e);
			}
		}
		mininghandler.tick();
		handler.tick();
		if (networktick == 2) {
			networktick = 0;
			for (Entity e : Handler.entity) {
				if (e.getId() == Id.Player) {
					new Packet02Move(((Player) e).getUsername(), ((Player) e).getX(), ((Player) e).getY(),
							MiningHandler.equippedTool).send(client);
				}
			}
		} else {
			networktick++;
		}
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
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
		rendertick = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoseconds;
			lastTime = now;
			while (delta >= 1) {
				tick();
				rendertick++;
				ticks++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("Terracraft FPS: " + frames + " Ticks: " + ticks);
				frames = 0;
				ticks = 0;
				rendertick = 0;
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

	public Game(int x, int y, Client client, JFrame frame) {
		this.x = x;
		this.y = y;
		this.client = client;
		this.frame = frame;
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

	private void doConsoleStuff(Graphics g) {
		if (consoleOpen) {
			renderConsole(g);
			if (TextToDrawInConsole != null) {
				String without = Utils.removeFirstChar(TextToDrawInConsole);
				if (!TextToDrawInConsole.contains("/") && !without.contains("/")) {
					renderKeyInput(g, TextToDrawInConsole);

				} else {
					renderKeyInput(g, "Console : " + TextToDrawInConsole);
				}
			}
			if (rendertick < 30) {
				g.drawLine(985, 650, 985, 680);
			}
		} else {
			renderLookingBlock(g);
		}
	}

	private void renderLookingBlock(Graphics g) {
		int x = 0;
		int y = 0;
		int x2 = m.getX() - cam.getX();
		int y2 = m.getY() - cam.getY();
		/**
		 * Der Code sieht nach Cancer aus Und er ist Hodenkrebs im Endstadium...
		 */
		if (x2 > 0) {
			while (x2 >= 32) {
				x += 32;
				x2 -= 32;
			}
		} else if (x2 < 0) {
			while (x2 <= 32) {
				x -= 32;
				x2 += 32;
			}
			x += 32;
		}
		if (y2 > 0) {
			while (y2 >= 32) {
				y += 32;
				y2 -= 32;
			}

		} else if (y2 < 0) {
			while (y2 <= 32) {
				y -= 32;
				y2 += 32;
			}
			y += 32;
		}
		m.lookingAtX = x;
		m.lookingAtY = y;
		g.setColor(Color.RED);
		g.drawRect(x, y, 32, 32);
		g.drawRect(player.getX() - 650, player.getY() - 440, getFrameBreite() , 700);
	}

	private void renderConsole(Graphics g) {
		Color ConsoleColor = new Color(200, 0, 200, 50);
		g.setColor(ConsoleColor);
		g.fillRect(980 + player.getX(), 450 + player.getY(), 300, 200);
		Color WritingText = new Color(0, 0, 0, 100);
		g.setColor(WritingText);
		g.drawRect(980 + player.getX(), 650 + player.getY(), 300, 30);
	}

	private void renderKeyInput(Graphics g, String keyToDraw) {
		g.setColor(Color.BLUE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(keyToDraw, 983, 671);

	}

	public static void drawKeyInput(String keyText) {
		if (keyText.equalsIgnoreCase("backspace")) {
			Utils.removeLastChar(TextToDrawInConsole);
			TextToDrawInConsole = Utils.removeLastChar(TextToDrawInConsole);
		} else {
			TextToDrawInConsole = TextToDrawInConsole + keyText;
		}
	}

	public static void executeCommand(String[] args) {
		if (args[0].equalsIgnoreCase("placeblock")) {
			System.out.println("placed block at " + player.x + "   " + player.y);
			new Packet07AddTile(player.x, player.y, "Grass").send(Game.client);
		}
		if (args[0].equalsIgnoreCase("fly")) {
			player.fly = true;
		}
		if (args[0].equalsIgnoreCase("give")) {
			for (int i = 0; i < args.length; i++) {
				if (Utils.isNotNull(args)) {
					System.out.println(args[i]);
				}
			}

			// 2 Arg Block
			// 3 Arg Anzahl

		}

	}

	public static Rectangle getVisisbleArea() {

		return new Rectangle(player.getX() - 650, player.getY() - 440, getFrameBreite() , 700);

	}
}