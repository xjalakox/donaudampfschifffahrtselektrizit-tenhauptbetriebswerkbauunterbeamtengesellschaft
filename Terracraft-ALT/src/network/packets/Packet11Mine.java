package network.packets;

import Terracraft.Utils;
import network.Client;
import network.Server;

public class Packet11Mine extends Packet {
	private int click, clicked;
	private String username;

	public Packet11Mine(byte[] data) {
		super(PacketTypes.MINE.getId());
		String[] message = readData(data).split(",");
		click = Utils.toInt(message[0]);
		clicked = Utils.toInt(message[1]);
		username = Utils.toString(message[2]);

	}

	public Packet11Mine(int click, int clicked, String username) {
		super(PacketTypes.MINE.getId());
		this.click = click;
		this.clicked = clicked;
		this.username = username;

	}

	@Override
	public void send(Client client) {
		client.send(getData());
	}

	public int getClick() {
		return click;
	}

	public int getClicked() {
		return clicked;
	}
	
	public String getUsername() {
		return username;
	}

	@Override
	public void send(Server server) {
		server.sendToAll(getData());
	}

	@Override
	public byte[] getData() {
		return ("11" + click + "," + clicked + "," + username).getBytes();
	}

}
