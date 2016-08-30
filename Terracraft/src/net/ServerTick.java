package net;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import Entity.Entity;
import Terracraft.Handler;
import Terracraft.Id;
import net.Network.SendCoordinates;

public class ServerTick implements Runnable{
	
	private static Thread thread;
	private static boolean running = false;
	private Handler handler;
	
	ServerTick(Handler handler, Thread thread){
		this.handler = handler;
		ServerTick.thread = thread;
	}
	
	
	public void init() {
		
	}

	public void tick() {
		
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
			if (System.currentTimeMillis() - timer > 1000) {
				System.out.println("tick");
				timer += 1000;
				ticks = 0;
			}
		}
		stop();
	}

}
