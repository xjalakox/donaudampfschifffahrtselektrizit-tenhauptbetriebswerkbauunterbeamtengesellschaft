package Terracraft;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import Entity.Player;
import Input.Key;
import Input.Mouse;
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
	private int rendertick = 0;
	public static Client client;
	private int x, y, networktick;
	private Key key;
	public static Mouse m = new Mouse();
	public static boolean consoleOpen;
	public static String TextToDrawInConsole = "";
	public static Spritesheet sheet = new Spritesheet("/Spritesheet.png");

	public static MiningHandler mininghandler = new MiningHandler();

	public static Camera cam;

	public static Spritesheet2 sheet_armor = new Spritesheet2("/Armor.png");
	public static Spritesheet2 sheet_legs = new Spritesheet2("/Legs.png");
	public static Spritesheet2 sheet_head = new Spritesheet2("/Head.png");
	public static Spritesheet2 sheet_body = new Spritesheet2("/Body.png");
	public static Spritesheet2 sheet_armor_head = new Spritesheet2("/Armor_Head.png");
	private JFrame frame;

	public void init() {
		mininghandler.init();
		System.out.println(breite * scale + "  " + höhe * scale);
		Login.frame.dispose();
		handler = new Handler();
		key = new Key();
		player = new Player(client.getUsername(), x, y, 46, 96, Id.Player, key);
		handler.addEntity(player);
		new Packet00Login(player.getUsername(), player.getX(), player.getY()).send(client);

		addMouseListener(m);
		addMouseMotionListener(m);
		addKeyListener(key);
		addMouseWheelListener(m);
		cam = new Camera();
		requestFocus();

		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon(new Sprite(sheet, 5, 1, 1, 1).getBufferedImage()).getImage(), new Point(0, 0),
				"custom cursor"));
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		g2d.translate(cam.getX(), cam.getY());
		handler.render(g);
		mininghandler.render(g);
		g2d.translate(-cam.getX(), -cam.getY());

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

		g.dispose();
		bs.show();
	}

	public void tick() {
		cam.tick(player);
		handler.tick();
		mininghandler.tick();
		if (networktick == 6) {
			networktick = 0;
			new Packet02Move(player.getUsername(), player.getX(), player.getY(), MiningHandler.equippedTool)
					.send(client);

			/**
			 * Ich glaube das ist useless, wenn irgendwas mit dem Movement buggt
			 * einfach oben die Zeile ausklammern und das wieder aktivieren
			 */
			/*
			 * for (Entity.Entity e : Handler.entity) { if (e.getId() ==
			 * Id.Player) { new Packet02Move(((Player) e).getUsername(),
			 * ((Player) e).getX(), ((Player) e).getY(),
			 * MiningHandler.equippedTool).send(client); } }
			 */
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

	private void renderLookingBlock(Graphics g) {
		int x = 0;
		int y = 0;
		int x2 = m.getX();
		int y2 = m.getY();
		
		int x3 = player.getX() - 640;
		if(x3<=0){
			x3 = 0;
		}
		int y3 = player.getY() - 360;
		if(y3<=0){
			y3 = 0;
		}
		System.out.println(x3);
		//System.out.println(y3);
		while (x3 >= 32) {
			x += 32;
			x3 -= 32;
		}
		while (y3 >= 32) {
			y += 32;
			y3 -= 32;
		}
		while (x2 >= 32) {
			x += 32;
			x2 -= 32;
		}
		while (y2 >= 32) {
			y += 32;
			y2 -= 32;
		}
		m.lookingAtX = x;
		m.lookingAtY = y;
		g.setColor(Color.RED);
		g.drawRect(x, y, 32, 32);
	}

	private void renderConsole(Graphics g) {
		Color ConsoleColor = new Color(200, 0, 200, 50);
		g.setColor(ConsoleColor);
		g.fillRect(980, 450, 300, 200);
		Color WritingText = new Color(0, 0, 0, 100);
		g.setColor(WritingText);
		g.drawRect(980, 650, 300, 30);
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
}