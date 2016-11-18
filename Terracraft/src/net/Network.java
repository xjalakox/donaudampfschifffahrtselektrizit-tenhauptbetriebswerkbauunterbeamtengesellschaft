package net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;

import terracraft.Id;

public class Network {

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		kryo.register(String.class);
		kryo.register(int.class);
		kryo.register(String[].class);
		kryo.register(boolean.class);

		kryo.register(Id.class);

		kryo.register(LoginRequest.class);
		kryo.register(LoginResponse.class);
		kryo.register(RegisterRequest.class);
		kryo.register(RegisterResponse.class);
		kryo.register(SpawnResponse.class);
		kryo.register(NetUserSpawnResponse.class);
		kryo.register(SendCoordinates.class);
		kryo.register(FinishedLoading.class);
		kryo.register(RemovePlayer.class);
		kryo.register(AddTile.class);
		kryo.register(HittingBlock.class);
		kryo.register(RemoveTile.class);
		kryo.register(Inventory.class);
		kryo.register(KillClient.class);
		kryo.register(DeleteMapRequest.class);
		kryo.register(openDoor.class);
	}

	static public class LoginRequest {
		public String text;
	}

	static public class LoginResponse {
		public String text;
	}

	static public class RegisterRequest {
		public String text;
	}

	static public class RegisterResponse {
		public String text;
	}

	static public class SpawnResponse {
		public int x;
		public int y;
		public String username;
	}

	static public class NetUserSpawnResponse {
		public int x;
		public int y;
		public String username;
	}

	static public class SendCoordinates {
		public int x;
		public int y;
		public String username;
		public Id tool;
	}

	static public class FinishedLoading {
		public String username;
	}

	static public class RemovePlayer {
		public String username;
	}

	static public class AddTile {
		public int x, y;
		public String type;
	}

	static public class RemoveTile {
		public int x, y;
	}

	static public class HittingBlock {
		public boolean click, clicked;
		public String username;
	}

	static public class Inventory {
		public String[] itemids;
	}

	static public class KillClient {

	}
	
	static public class DeleteMapRequest {
		public String username;
	}
	
	static public class openDoor {
		public int x,y;
	}

	public static void sendCoordinates(Client client, int x, int y, String username, Id tool, String type) {
		SendCoordinates coordinates = new SendCoordinates();
		coordinates.x = x;
		coordinates.y = y;
		coordinates.username = username;
		coordinates.tool = tool;
		if (type.equalsIgnoreCase("udp")) {
			client.sendUDP(coordinates);
		} else if (type.equalsIgnoreCase("tcp")) {
			client.sendTCP(coordinates);
		} else {
			System.out.println("Packet konnte nicht übertragen werden nur TCP oder UDP zulässig.");
		}
	}
}
