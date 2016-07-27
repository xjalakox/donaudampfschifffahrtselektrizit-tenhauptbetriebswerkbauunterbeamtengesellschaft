package network.packets;

import Terracraft.Id;
import network.Client;
import network.Server;

public class Packet02Move extends Packet {

	private String username;
	private int x, y;
	private Id tool = Id.Pickaxe;

	public Packet02Move(byte[] data) {
		super(PacketTypes.MOVE.getId());
		String[] message = readData(data).split(",");
		username = message[0];
		x = Integer.parseInt(message[1]);
		y = Integer.parseInt(message[2]);
//		if (message.length>=5) {
			tool = Id.valueOf(message[3]);
//		}
	}

	public Packet02Move(String username, int x, int y, Id tool) {
		super(PacketTypes.MOVE.getId());
		this.username = username;
		this.x = x;
		this.y = y;
		this.tool = tool;
	}

	public void send(Client client) {
		client.send(getData());
	}

	public void send(Server server) {
		server.sendToAll(getData());
	}

	public byte[] getData() {
		return ("02" + username + "," + x + "," + y + "," + tool).getBytes();
	}

	public String getUsername() {
		return username;
	}

	public int getX() {
		return x;
	}

	public Id getTool() {
		return tool;
	}

	public int getY() {
		return y;
	}

}
