package network.packets;

import Terracraft.Utils;
import network.Client;
import network.Server;

public class Packet10RemoveTile extends Packet {

	private int x,y;

	public Packet10RemoveTile(byte[] data) {
		super(PacketTypes.REMOVETILE.getId());
		String[] message = readData(data).split(",");

		x = Utils.toInt(message[0]);
		y = Utils.toInt(message[1]);
	}

	public Packet10RemoveTile(int x, int y) {
		super(PacketTypes.REMOVETILE.getId());
		
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
		return ("10" + x + "," + y).getBytes();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
