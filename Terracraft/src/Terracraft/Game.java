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

import com.esotericsoftware.kryonet.Client;

import Entity.Snowman;
import Entity.Entity;
import Entity.Player;
import Input.Key;
import Input.Mouse;
import audio.SoundManager;
import gfx.Image;
import gfx.Sprite;
import gfx.Spritesheet;
import gfx.Spritesheet2;
import net.Network.*;
import net.registerlogin.Login;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1337L;
	private static final int breite = 320, height = 180, scale = 4;
	private static boolean running = false;

	private static Thread thread;
	public static Handler handler;
	public static Player player;
	public static Camera cam;
	public static Map map;
	public static Mouse m;
	public static MiningHandler mininghandler;
	public static SoundManager sm;
	public static Client client;

	public static Spritesheet sheet = new Spritesheet("/Sprites/Spritesheet.png");
	public static Spritesheet2 sheet_armor = new Spritesheet2("/Sprites/Armor.png");
	public static Spritesheet2 sheet_legs = new Spritesheet2("/Sprites/Legs.png");
	public static Spritesheet2 sheet_head = new Spritesheet2("/Sprites/Head.png");
	public static Spritesheet2 sheet_body = new Spritesheet2("/Sprites/Body.png");
	public static Spritesheet2 sheet_armor_head = new Spritesheet2("/Sprites/Armor_Head.png");

	private Snowman snowman;
	private Image background;
	private JFrame frame;
	private String username;
	private int x, y;

	public static boolean consoleOpen;
	public static String TextToDrawInConsole;

	public void init() {

		Utils.startTimerMillis();
		Login.frame.dispose();

		/** INIT **/

		thread = new Thread();
		handler = new Handler("Client");
		cam = new Camera();
		map = new Map();
		m = new Mouse();
		sm = new SoundManager();
		mininghandler = new MiningHandler();
		player = new Player(username, x, y, 46, 96, Id.Player);
		snowman = new Snowman(x - 1000, 300, 64, 64, handler, Id.Dragon);
		
		handler.addEntity(player);
		handler.addEntity(snowman);
		mininghandler.init();

		addMouseListener(m);
		addMouseMotionListener(m);
		addMouseWheelListener(m);
		m.mouseItem = Id.Empty;

		addKeyListener(new Key());

		

		background = new Image("/Backgrounds/Background_1.png");
		TextToDrawInConsole = "";
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon(new Sprite(sheet, 5, 1, 1, 1).getBufferedImage()).getImage(), new Point(0, 0),
				"custom cursor"));

		/** INIT ENDE **/

		/** INIT NETWORK **/

		NetUserSpawnResponse spawn = new NetUserSpawnResponse();
		spawn.username = username;
		spawn.x = x;
		spawn.y = y;
		client.sendTCP(spawn);

		FinishedLoading finished = new FinishedLoading();
		finished.username = username;
		client.sendTCP(finished);

		/** INIT NETWORK ENDE **/

		System.out.println(Utils.getTimerMillis() + " um das Spiel zu laden");

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(4);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(135, 206, 250));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.translate(cam.getX(), cam.getY());
		g.drawImage(background.getBufferedImage(), player.getX() / 2 + player.getX() / 8,
				player.getY() / 2 + player.getY() / 8, background.getWidth() * 2, background.getHeight() * 2, null);
		map.render(g);

		handler.render(g);
		mininghandler.render(g);
		map.render(g);
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
		player.sendPosition();
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
				frame.setTitle("Terracraft FPS: " + frames + " Ticks: " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}

	public Game(int x, int y, String username, JFrame frame, Client client) {
		this.x = x;
		this.y = y;
		this.username = username;
		this.frame = frame;
		Game.client = client;
		Dimension size = new Dimension(breite * scale, height * scale);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}

	public static int getFrameWidth() {
		return breite * scale;
	}

	public static int getFrameHeight() {
		return height * scale;
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
		if (mininghandler.scrollbarTiles.get(Mouse.mouseRotation).getImage() != null && Mouse.mouseRotation >= 0
				&& mininghandler.scrollbarTiles.get(Mouse.mouseRotation).getType().equalsIgnoreCase("block")) {
			g.drawImage(mininghandler.scrollbarTiles.get(Mouse.mouseRotation).getImage().getBufferedImage(), x, y,
					mininghandler.scrollbarTiles.get(Mouse.mouseRotation).getImage().getBufferedImage().getWidth(),
					mininghandler.scrollbarTiles.get(Mouse.mouseRotation).getImage().getBufferedImage().getHeight(),
					null);
		}
		g.drawRect(player.getX() - 650, player.getY() - 440, getFrameWidth(), 700);
	}

	private void doConsoleStuff(Graphics g) {
		// TODO Ein Int den man gut nutzen kann um nur bei bestimmten Ticks
		// etwas zu zeichnen etc.
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
			if (TextToDrawInConsole.length() == 0) {
				g.drawLine(320 + player.getX(), 210 + player.getY(), 320 + player.getX(), 771);
			}
		} else {
			renderLookingBlock(g);
		}
	}

	private void renderConsole(Graphics g) {
		Color ConsoleColor = new Color(200, 0, 200, 50);
		g.setColor(ConsoleColor);
		g.fillRect(315 + player.getX(), 10 + player.getY(), 300, 200);
		Color WritingText = new Color(0, 0, 0, 100);
		g.setColor(WritingText);
		g.drawRect(315 + player.getX(), 210 + player.getY(), 300, 30);
	}

	private void renderKeyInput(Graphics g, String keyToDraw) {
		g.setColor(Color.BLUE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(keyToDraw, player.getX() + 320, player.getY() + 230);

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
		if (args[0].equalsIgnoreCase("fly")) {
			player.fly = true;
		}
		if (args[0].equalsIgnoreCase("give")) {
			boolean breakout = false;
			for (int i = 0; i < args.length; i++) {
				if (Utils.isNotNull(args)) {
					// mininghandler.scrollbarTiles
					for (int j = 0; j < 39; j++) {
						if (player.Inventory_amount[j] == 0) {
							player.Inventory.add(j, Id.toId(args[1]));
							player.Inventory_amount[j] = Utils.toInt(args[2]);
							breakout = true;
						}
						if (breakout)
							break;
					}
					// player.Inventory.set(mininghandler.equippedTool,
					// Id.toId(SplitInventoryData[0]));
					// player.Inventory_amount[InventoryPlace] =
					// Utils.toInt(SplitInventoryData[1]);
				}
			}

			// 2 Arg Block
			// 3 Arg Anzahl

		}

	}

	public static Rectangle getVisisbleArea() {
		return new Rectangle(player.getX() - 650, player.getY() - 440, getFrameWidth(), 700);
	}

	public static Rectangle getVisisbleAreaMap() {
		return new Rectangle(player.getX() - 650 - 1920, player.getY() - 440 - 1050, getFrameWidth() * 4, 700 * 4);
	}

}