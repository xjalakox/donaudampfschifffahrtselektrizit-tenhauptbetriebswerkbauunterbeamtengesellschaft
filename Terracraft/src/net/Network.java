package net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import Terracraft.Id;

public class Network {
	static public final int port = 54555;

	// This registers objects that are going to be sent over the network.
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(LoginRequest.class);
		kryo.register(LoginResponse.class);
		kryo.register(String.class);
		kryo.register(int.class);
		kryo.register(SpawnResponse.class);
		kryo.register(NetUserSpawnResponse.class);
		kryo.register(SendCoordinates.class);
		kryo.register(Id.class);
		kryo.register(FinishedLoading.class);
		kryo.register(RemovePlayer.class);
		kryo.register(AddTile.class);
		kryo.register(HittingBlock.class);
		kryo.register(RemoveTile.class);
		kryo.register(Inventory.class);

	}

	static public class LoginRequest {
		public String text;
	}

	static public class LoginResponse {
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
		public String itemid;
	}

}
