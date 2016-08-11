package network.packets;

import network.Client;
import network.Server;

public class Packet12InventoryData extends Packet {
	private String itemid;

	public Packet12InventoryData(byte[] data) {
		super(PacketTypes.INVENTORY.getId());
		String[] message = readData(data).split(",");
		itemid = message.toString();

	}

	public Packet12InventoryData(String itemid) {
		super(PacketTypes.INVENTORY.getId());
		this.itemid = itemid;

	}

	@Override
	public void send(Client client) {
		client.send(getData());
	}

	public String getItemId(){
		return itemid;
	}

	@Override
	public void send(Server server) {
		server.sendToAll(getData());
	}

	@Override
	public byte[] getData() {
		return ("12" + itemid).getBytes();
	}

}
