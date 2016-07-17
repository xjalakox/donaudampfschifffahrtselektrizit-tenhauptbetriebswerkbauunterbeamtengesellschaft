package network.packets;

import network.Client;
import network.Server;

public class Packet03MySQL_Login extends Packet {

	private String username, password;

	public Packet03MySQL_Login(byte[] data) {
		super(PacketTypes.LOGIN.getId());
		String[] message = readData(data).split(",");
		username = message[0];
		password = message[1];
	}

	public Packet03MySQL_Login(String username, String password) {
		super(PacketTypes.LOGIN.getId());
		this.username = username;
		this.password = password;
	}

	public void send(Client client) {
		client.send(getData());
	}

	public void send(Server server) {
		server.sendToAll(getData());
	}

	public byte[] getData() {
		return ("03" + username + "," + password).getBytes();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
