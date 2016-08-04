package network.abstracts;

import java.net.InetAddress;

public class NetUser {

	private String username;
	private InetAddress address;
	private int port;
	private long lastPacket;

	public NetUser(String username, InetAddress address, int port) {
		this.username = username;
		this.address = address;
		this.port = port;
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
}
