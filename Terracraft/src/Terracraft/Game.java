package terracraft;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.esotericsoftware.kryonet.Client;

import audio.SoundManager;
import entity.Entity;
import entity.Player;
import entity.Snowman;
import enviroment.Lighting;
import enviroment.Map;
import gfx.Image;
import gfx.Sprite;
import gfx.Spritesheet;
import gfx.Spritesheet2;
import gui.Menu;
import input.Key;
import input.Mouse;
import net.Network.*;
import net.registerlogin.Login;
import tile.Tree;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	private static final int WIDTH = 320, HEIGHT = 180, SCALE = 4;
	private static boolean running = false;

	private Thread thread;
	public static Handler handler;
	public static Player player;
	public static Camera cam;
	public static Map map;
	public static Mouse m;
	public static MiningHandler mininghandler;
	public static SoundManager sm;
	public static Console console;
	public static Client client;

	public static Spritesheet sheet = new Spritesheet("/Sprites/Spritesheet.png");
	public static Spritesheet2 sheet_armor = new Spritesheet2("/Sprites/Armor.png");
	public static Spritesheet2 sheet_legs = new Spritesheet2("/Sprites/Legs.png");
	public static Spritesheet2 sheet_head = new Spritesheet2("/Sprites/Head.png");
	public static Spritesheet2 sheet_body = new Spritesheet2("/Sprites/Body.png");
	public static Spritesheet2 sheet_armor_head = new Spritesheet2("/Sprites/Armor_Head.png");
	public static Spritesheet sheet_tree = new Spritesheet("/Sprites/Tree.png");
	public static Spritesheet sheet_grass = new Spritesheet("/Sprites/Grass.png");
	public static Spritesheet sheet_dirt = new Spritesheet("/Sprites/Dirt.png");
	public static Spritesheet sheet_wood_block = new Spritesheet("/Sprites/Wood_Block.png");
	public static Spritesheet sheet_stone = new Spritesheet("/Sprites/Stone.png");
	public static Spritesheet sheet_tin = new Spritesheet("/Sprites/Tin.png");
	public static Spritesheet sheet_copper = new Spritesheet("/Sprites/Copper.png");
	public static Spritesheet sheet_gold = new Spritesheet("/Sprites/Gold.png");
	public static Spritesheet sheet_platinum = new Spritesheet("/Sprites/Platinum.png");
	public static Spritesheet sheet_tree_tops = new Spritesheet("/Sprites/Tree_Tops.png");
	public static Spritesheet sheet_tree_branches = new Spritesheet("/Sprites/Tree_Branches.png");
	public static Spritesheet sheet_chest = new Spritesheet("/Sprites/Chest.png");
	public static Menu menu;
	private Snowman snowman;
	private Image background;
	private JFrame frame;
	private String username;
	private int x, y;
	public static boolean isAdmin = false;

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
		console = new Console(player);
		snowman = new Snowman(x - 1000, 300, 64, 64, handler, Id.Dragon);
		handler.addEntity(player);
		handler.addEntity(snowman);
		mininghandler.init();
		sm.playSound(2);

		menu = new Menu(getWidth() / 2 - 100, -400, 250, 400);
		addMouseListener(m);
		addMouseMotionListener(m);
		addMouseWheelListener(m);
		m.mouseItem = Id.Empty;
		addKeyListener(new Key(this));

		background = new Image("/Backgrounds/Background_1.png");
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon(new Sprite(sheet, 5, 1, 1, 1).getBufferedImage()).getImage(), new Point(0, 0),
				"custom cursor"));

		initNetWork();

		/** INIT ENDE **/

		System.out.println(Utils.getTimerMillis() + " um das Spiel zu laden");
	}

	public void reinitialize() {
		menu = new Menu(getWidth() / 2 - 100, -400, 250, 400);
		frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(4);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g.create();
		Graphics2D g2d2 = (Graphics2D) g.create();

		g2d.setColor(new Color(135, 206, 250));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.translate(cam.getX(), cam.getY());
		g2d.drawImage(background.getBufferedImage(), player.getX() / 2 + player.getX() / 8,
				player.getY() / 2 + player.getY() / 8, background.getWidth() * 2, background.getHeight() * 2, null);

		handler.render(g2d);

		//renderTestFlashLight(g2d);

		//new Lighting(550, 550, g2d);
		mininghandler.render(g);
		map.render(g);
		console.render(g, g2d);
		menu.render(g2d2);

		g2d.setColor(Color.BLACK);
		g2d.drawRect(m.x, m.y, 2, 2);

		g.dispose();
		g2d.dispose();
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
		menu.tick();
		player.sendPosition();
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
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

	private void renderTestFlashLight(Graphics2D g2d) {
		Point mpoint = new Point();
		mpoint.x = m.getX();
		mpoint.y = m.getY();

		Paint paint = new Color(0, 0, 0, 0);
		if (mpoint != null) {
			paint = new RadialGradientPaint(mpoint, 200, new float[] { 0, 1f },
					new Color[] { new Color(0, 0, 0, 0), new Color(0, 0, 0, 225) });
		}
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}

	private void initNetWork() {
		NetUserSpawnResponse spawn = new NetUserSpawnResponse();
		spawn.username = username;
		spawn.x = x;
		spawn.y = y;
		client.sendTCP(spawn);

		FinishedLoading finished = new FinishedLoading();
		finished.username = username;
		client.sendTCP(finished);
	}

	public Game(int x, int y, String username, JFrame frame, Client client) {
		this.x = x;
		this.y = y;
		this.username = username;
		this.frame = frame;
		Game.client = client;
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}

	public static int getFrameWidth() {
		return WIDTH * SCALE;
	}

	public static int getFrameHeight() {
		return HEIGHT * SCALE;
	}

	public static Rectangle getVisisbleArea() {
		return new Rectangle(player.getX() - 650, player.getY() - 440, getFrameWidth(), 700);
	}

}