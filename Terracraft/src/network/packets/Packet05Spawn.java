package network.packets;

import network.Client;
import network.Server;

public class Packet05Spawn extends Packet {

	private int x, y;

	public Packet05Spawn(byte[] data) {
		super(PacketTypes.SPAWN.getId());
		String[] message = readData(data).split(",");
		x = Integer.parseInt(message[0]);
		y = Integer.parseInt(message[1]);
	}

	public Packet05Spawn(int x, int y) {
		super(PacketTypes.SPAWN.getId());
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
		return ("05" + x + "," + y).getBytes();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
