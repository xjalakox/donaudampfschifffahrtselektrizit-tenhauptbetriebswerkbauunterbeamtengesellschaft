package net;

import java.util.Collections;
import java.util.Comparator;

import com.esotericsoftware.kryonet.Server;
import com.sun.org.apache.xerces.internal.impl.dv.xs.FullDVFactory;

import net.Network.SendCoordinates;
import terracraft.Handler;
import terracraft.Id;
import worldgeneration.Gen;
import worldgeneration.Element;

public class ServerTick implements Runnable{
	
	private static Thread thread;
	private static boolean running = false;
	private Handler handler;
	private Server server;
	
	ServerTick(Handler handler, Server server){
		this.handler = handler;
		this.server = server;
	}
	
	
	public void init() {
		Gen g=new Gen();
		
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
