package network.packets;

import network.Client;
import network.Server;

public class Packet06Message extends Packet {

	private String text;

	public Packet06Message(byte[] data) {
		super(PacketTypes.MESSAGE.getId());
		String[] message = readData(data).split(",");
		text = message[0];
	}

	public Packet06Message(String text) {
		super(PacketTypes.MESSAGE.getId());
		this.text = text;
	}

	public void send(Client client) {
		client.send(getData());
	}

	public void send(Server server) {
		server.sendToAll(getData());
	}

	public byte[] getData() {
		return ("06" + text).getBytes();
	}

	public String getText() {
		return text;
	}
}
