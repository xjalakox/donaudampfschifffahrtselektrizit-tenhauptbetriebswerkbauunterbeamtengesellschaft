package network.abstracts;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Terracraft.Utils;

public abstract class NetClient implements Runnable {

	private Thread thread;
	private boolean running;
	private int packetSize;
	private DatagramSocket socket;
	protected InetAddress ipAddress;
	protected int port;
	protected Object game;
	protected String username;

	public NetClient(Object game, int packetSize) {
		this.game = game;
		this.packetSize = packetSize;
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public NetClient(int packetSize){
		this.packetSize = packetSize;
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		shutdown();
		thread.interrupt();
	}

	public void run() {
		while (running) {
			byte[] data = new byte[packetSize];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}

	public void setConnection(String username, String ipAddress) {
		this.username = username;
		try {
			this.ipAddress = InetAddress.getByName(ipAddress.split(":")[0]);
			this.port = Utils.toInt(ipAddress.split(":")[1]);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void setConnection(String username, String ipAddress, int port) {
		this.username = username;
		this.port = port;
		try {
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	protected abstract void init();

	protected abstract void parsePacket(byte[] data, InetAddress address, int port);

	protected abstract void shutdown();

	public void send(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}
}
