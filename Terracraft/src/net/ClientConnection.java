package net;

import java.io.IOException;

import javax.swing.JFrame;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import entity.Entity;
import entity.NetPlayer;
import entity.Player;
import net.Network.*;
import net.registerlogin.Login;
import terracraft.Game;
import terracraft.Id;
import terracraft.Utils;
import tile.source.Tile;

public class ClientConnection {
	private static Game terracraft = null;
	private boolean logged = false;

	public ClientConnection(Client client) {
		client.start();
		// Log.TRACE();

		try {
			client.connect(5000, "localhost", 54555, 54777);
		} catch (IOException e) {
			System.out.println("Keine Verbindung zum Server möglich. Snipe Client vom Hochhaus");
			Login.killClient = true;
			System.exit(0);
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
					logged = true;
				}
				if (object instanceof NetUserSpawnResponse && logged) {
					NetUserSpawnResponse response = (NetUserSpawnResponse) object;
					NetPlayer netplayer = new NetPlayer(response.username, response.x, response.y, 46, 96,
							Id.NetPlayer);
					Game.handler.addEntity(netplayer);
				}
				if (object instanceof AddTile && logged) {
					AddTile tile = (AddTile) object;
					Tile ti = Id.getTile(tile.type);
					ti.setX(tile.x);
					ti.setY(tile.y);
					Game.handler.addTile(ti);
				}
				if (object instanceof RemovePlayer && logged) {
					RemovePlayer response = (RemovePlayer) object;
					Game.handler.removePlayer(response.username);
				}
				if (object instanceof SendCoordinates && logged) {
					SendCoordinates response = (SendCoordinates) object;
					Game.handler.setPlayerPosition(response.username, response.x, response.y, response.tool);
				}
				if (object instanceof RemoveTile && logged) {
					RemoveTile response = (RemoveTile) object;
					Game.handler.setToBeRemoved(response.x, response.y);
				}
				if (object instanceof HittingBlock && logged) {
					HittingBlock response = (HittingBlock) object;
					for (Entity e : Game.handler.entity) {
						if (e.getId() == Id.NetPlayer
								&& (((NetPlayer) e).getUsername()).equalsIgnoreCase(response.username)) {
							e.click = response.click;
							e.clicked = response.clicked;
						}
					}
				}
				if (object instanceof Inventory) {
					Inventory response = (Inventory) object;

					for (int i = 0; i < response.itemids.length; i++) {
						if (Utils.isNotNull(response.itemids[i])) {
							String[] SplitInventoryData = response.itemids[i].split(",");
							Game.player.Inventory.set(i, Id.toId(SplitInventoryData[0]));
							Game.player.Inventory_amount[i] = Utils.toInt(SplitInventoryData[1]);
						}
					}
				}
				if (object instanceof KillClient) {
					System.exit(0);
				}
			}

			public void disconnected(Connection connection) {
				System.exit(0);
			}
		});
	}
}
