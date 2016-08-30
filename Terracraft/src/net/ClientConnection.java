package net;

import java.io.IOException;

import javax.swing.JFrame;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import Entity.NetPlayer;
import Terracraft.Game;
import Terracraft.Id;
import Tile.source.Tile;
import net.Network.AddTile;
import net.Network.LoginRequest;
import net.Network.LoginResponse;
import net.Network.NetUserSpawnResponse;
import net.Network.RemovePlayer;
import net.Network.SendCoordinates;
import net.Network.SpawnResponse;
import net.registerlogin.Login;

public class ClientConnection {
	private static Game terracraft = null;

	public ClientConnection(Client client) {
		client.start();

		try {
			client.connect(5000, "localhost", 54555, 54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Network.register(client);

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof LoginResponse) {
					LoginResponse response = (LoginResponse) object;
					Login.setStatus(response.text);
				}
				if (object instanceof SpawnResponse) {
					SpawnResponse response = (SpawnResponse) object;
					JFrame frame = new JFrame("TerraCraft");
					terracraft = new Game(response.x, response.y, response.username, frame, client);
					frame.add(terracraft);
					frame.pack();
					frame.setBounds(0, 0, 320 * 4, 180 * 4);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					frame.setAlwaysOnTop(true);
					terracraft.start();
				}
				if (object instanceof NetUserSpawnResponse) {
					NetUserSpawnResponse response = (NetUserSpawnResponse) object;
					NetPlayer netplayer = new NetPlayer(response.username, response.x, response.y, 46, 96,
							Id.NetPlayer);
					terracraft.handler.addEntity(netplayer);
				}
				if (object instanceof AddTile) {
					AddTile tile = (AddTile) object;
					Tile ti = Id.getTile(tile.type, tile.x, tile.y);
					terracraft.handler.addTile(ti);
				}
				if (object instanceof RemovePlayer) {
					RemovePlayer response = (RemovePlayer) object;
					terracraft.handler.removePlayer(response.username);
				}
				if (object instanceof SendCoordinates) {
					SendCoordinates response = (SendCoordinates) object;
					terracraft.handler.setPlayerPosition(response.username, response.x, response.y, response.tool);
				}
			}

			public void disconnected(Connection connection) {
				System.exit(0);
			}
		});
	}
}
