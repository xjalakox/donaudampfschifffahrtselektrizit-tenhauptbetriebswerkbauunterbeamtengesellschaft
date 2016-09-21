package net;

import Terracraft.Handler;

public class ServerTick implements Runnable{
	
	private static Thread thread;
	private static boolean running = false;
	private Handler handler;
	
	ServerTick(Handler handler){
		this.handler = handler;
	}
	
	
	public void init() {
		
	}

	
	public void tick() {
		handler.tick();
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
		long lastTime = System.currentTimeMillis();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double nanoseconds = 1000.0 / 60.0;
		@SuppressWarnings("unused")
		int ticks = 0;
		while (running) {
			long now = System.currentTimeMillis();
			delta += (now - lastTime) / nanoseconds;
			lastTime = now;
			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				ticks = 0;
			}
		}
		stop();
	}
}
