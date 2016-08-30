package net;

import java.net.InetAddress;

import com.esotericsoftware.kryonet.Connection;

public class NetUser {

	private String username;
	private InetAddress address;
	private int port;
	private long lastPacket;
	private Connection Connection;
	private boolean isConnected;

	public NetUser(String username, InetAddress address, int port, Connection Connection) {
		this.username = username;
		this.address = address;
		this.port = port;
		this.Connection = Connection;
	}

	public Connection getConnection() {
		return Connection;
	}

	public void setConnection(Connection Connection) {
		Connection = Connection;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getlastPacket() {
		return lastPacket;
	}

	public void setLastPacket(long l) {
		this.lastPacket = l;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	

}
