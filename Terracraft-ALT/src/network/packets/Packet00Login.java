package network.packets;

import Terracraft.Utils;
import network.Client;
import network.NetUser;
import network.Server;

public class Packet00Login extends Packet {

	private String username;
	private int x, y;
	private NetUser user;

	public Packet00Login(byte[] data) {
		super(PacketTypes.LOGIN.getId());
		String[] message = readData(data).split(",");
		username = message[0];
		x = Utils.toInt(message[1]);
		y = Utils.toInt(message[2]);
	}

	public Packet00Login(String username, int x, int y) {
		super(PacketTypes.LOGIN.getId());
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
		return ("00" + username + "," + x + "," + y).getBytes();
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
