package network.packets;

import network.Client;
import network.Server;

public class Packet02Move extends Packet {

	private String username;
	private int x, y;

	public Packet02Move(byte[] data) {
		super(PacketTypes.MOVE.getId());
		String[] message = readData(data).split(",");
		username = message[0];
		x = Integer.parseInt(message[1]);
		y = Integer.parseInt(message[2]);
	}

	public Packet02Move(String username, int x, int y) {
		super(PacketTypes.MOVE.getId());
		this.username = username;
		this.x = x;
		this.y = y;
	}

	public void send(Client client) {
		client.send(getData());
	}

	public void send(Server server) {
		server.sendToAll(getData());
	}

	public byte[] getData() {
		return ("02" + username + "," + x + "," + y).getBytes();
	}

	public String getUsername() {
		return username;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
