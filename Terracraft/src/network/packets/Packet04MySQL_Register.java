package network.packets;

import network.Client;
import network.Server;

public class Packet04MySQL_Register extends Packet {

	private String username, password;

	public Packet04MySQL_Register(byte[] data) {
		super(PacketTypes.MYSQL_REGISTER.getId());
		String[] message = readData(data).split(",");
		username = message[0];
		password = message[1];
	}

	public Packet04MySQL_Register(String username, String password) {
		super(PacketTypes.MYSQL_REGISTER.getId());
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
		return ("04" + username + "," + password).getBytes();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}


