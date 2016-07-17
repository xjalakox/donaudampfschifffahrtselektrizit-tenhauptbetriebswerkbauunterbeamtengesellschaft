package network.packets;

import network.Client;
import network.Server;

public class Packet01Disconnect extends Packet {

	private String username;
	private int x,y;

	public Packet01Disconnect(byte[] data) {
		super(PacketTypes.DISCONNECT.getId());
		String[] message = readData(data).split(",");
		username = message[0];
		x = Integer.parseInt(message[1]);
		y = Integer.parseInt(message[2]);
	}

	public Packet01Disconnect(String username, int x,int y) {
		super(PacketTypes.DISCONNECT.getId());
		this.username = username;
		this.x = x;
		this.y = y;
	}
	
	public Packet01Disconnect(String username) {
		super(PacketTypes.DISCONNECT.getId());
		this.username = username;
	}

	public void send(Client client) {
		client.send(getData());
	}

	public void send(Server server) {
		server.sendToAll(getData());
	}

	public byte[] getData() {
		return ("01" + username + "," + x + "," + y).getBytes();
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
