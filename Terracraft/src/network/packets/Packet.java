package network.packets;

import network.Client;
import network.Server;

public abstract class Packet {

	public static enum PacketTypes {
		INVALID			(-1),
		LOGIN			(00),
		DISCONNECT		(01),
		MOVE			(02),
		MYSQL_LOGIN		(03),
		MYSQL_REGISTER	(04),
		SPAWN			(05),
		MESSAGE			(06), 
		ADDTILE			(07);

		private int packetId;

		private PacketTypes(int packetId) {
			this.packetId = packetId;
		}

		public int getId() {
			return packetId;
		}
	}

	public byte packetId;

	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}

	public abstract void send(Client client);

	public abstract void send(Server server);

	public String readData(byte[] data) {
		String message = new String(data).trim();
		return message.substring(2);
	}

	public abstract byte[] getData();

	public static PacketTypes lookupPacket(String packetId) {
		try {
			return lookupPacket(Integer.parseInt(packetId));
		} catch (NumberFormatException e) {
			return PacketTypes.INVALID;
		}
	}

	public static PacketTypes lookupPacket(int string) {
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getId() == string) {
				return p;
			}
		}
		return PacketTypes.INVALID;
	}

}
