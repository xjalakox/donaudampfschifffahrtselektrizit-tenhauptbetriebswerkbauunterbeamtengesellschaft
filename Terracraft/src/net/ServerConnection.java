package net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import entity.NetPlayer;
import net.Network.*;
import terracraft.Handler;
import terracraft.Id;
import terracraft.Utils;
import tile.source.Tile;

public class ServerConnection {

	private static MySQL mysql;
	private static Map<NetUser, NetPlayer> players;
	private static List<NetUser> users;
	private static List<Connection> connections;
	private static Handler handler;
	private static ServerTick tick;
	private static ArrayList<Tile> LoadingTilesIntoList = new ArrayList<Tile>();
	public static boolean killServer;

	public static void main(String[] args) throws IOException {
		Server server = new Server(131072, 16384);
		server.start();
		try {
			server.bind(54555, 54777);
		} catch (Exception e) {
			killServer = true;
		}

		if (killServer) {
			server.stop();
			System.exit(0);
		}

		mysql = new MySQL();

		if (killServer) {
			server.stop();
			System.exit(0);
		}

		Utils.startTimerMillis();

		Network.register(server);
		handler = new Handler("Server");
		tick = new ServerTick(handler);
		tick.start();
		players = new HashMap<NetUser, NetPlayer>();
		users = new ArrayList<NetUser>();
		connections = new ArrayList<Connection>();
		LoadingTilesIntoList = mysql.LoadMap();
		for (Tile ti : LoadingTilesIntoList) {
			handler.addTile(ti);
		}

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				// Was hier passiert brauch euch im Prinzip nicht jucken

				
				if (object instanceof LoginRequest) {
					LoginRequest request = (LoginRequest) object;
					String requesttext[] = request.text.split(",");
					LoginResponse response = new LoginResponse();
					if (requesttext.length == 2) {
						if (mysql.checkIfUserRegistered(requesttext[0], requesttext[1])) {
							for (NetUser u : users) {
								if (u.getUsername().equalsIgnoreCase(requesttext[0])) {
									response.text = "Spieler ist bereits eingeloggt!";
									connection.sendTCP(response);
									return;
								}
							}
							response.text = "Erfolgreich eingeloggt";
							connection.sendTCP(response);
							NetUser user = new NetUser(requesttext[0], connection.getRemoteAddressTCP().getAddress(),
									connection.getRemoteAddressTCP().getPort(), connection);
							NetPlayer player = new NetPlayer(requesttext[0], mysql.getX(requesttext[0]),
									mysql.getY(requesttext[0]), 24, 24, terracraft.Id.NetPlayer);
							connections.add(connection);
							users.add(user);
							players.put(user, player);
							SpawnResponse spawnResponse = new SpawnResponse();
							spawnResponse.x = player.getX();
							spawnResponse.y = player.getY();
							spawnResponse.username = player.getUsername();
							connection.sendTCP(spawnResponse);

						} else {
							response.text = "Passwort oder Benutzername falsch!";
							connection.sendTCP(response);
						}
					} else {
						response.text = "Kein Passwort oder Benutzername angegeben, \",\" sind auch nicht zugleassen!";
						connection.sendTCP(response);
					}
				}
				/**
				 * Alles was nach dem Laden erst gesendet werden darf muss hier
				 * gesendet werden.
				 */
				if (object instanceof FinishedLoading) {
					FinishedLoading response = (FinishedLoading) object;

					// Sende alle User
					for (NetUser u : users) {
						if (players.get(u) != null && !response.username.equalsIgnoreCase(u.getUsername())) {
							NetUserSpawnResponse netUserSpawnResponse = new NetUserSpawnResponse();
							netUserSpawnResponse.x = players.get(u).getX();
							netUserSpawnResponse.y = players.get(u).getX();
							netUserSpawnResponse.username = players.get(u).getUsername();
							connection.sendTCP(netUserSpawnResponse);
						}
					}

					for (Tile ti : handler.tile) {
						AddTile tile = new AddTile();
						tile.x = ti.getX();
						tile.y = ti.getY();
						tile.type = ti.getId().toString();
						connection.sendTCP(tile);
					}

					// User ist jetzt "komplett" connected
					for (NetUser u : users) {
						if (u.getConnection().equals(connection)) {
							u.setConnected(true);
						}
					}

					Inventory invresponse = new Inventory();
					invresponse.itemids = mysql.loadInventory(response.username);

					connection.sendTCP(invresponse);
				}
				// Allen Usern wird der neue Spieler gesendet
				if (object instanceof NetUserSpawnResponse) {
					NetUserSpawnResponse response = (NetUserSpawnResponse) object;

					for (NetUser u : users) {
						if (!response.username.equalsIgnoreCase(u.getUsername())) {
							u.getConnection().sendTCP(response);

						}
					}
				}
				if (object instanceof AddTile) {
					AddTile response = (AddTile) object;
					Tile tile = Id.getTile(response.type);
					tile.setX(response.x);
					tile.setY(response.y);
					handler.addTile(tile);
					mysql.addTile(tile);
					for (NetUser u : users) {
						if (u.isConnected() && connection.getID() != u.getConnection().getID()) {
							u.getConnection().sendTCP(response);
						}
					}
				}
				// Allen Usern werden alle Koordinaten gesendet, k�nnte ab
				// ner
				// bestimmten Zahl hart laggen.
				if (object instanceof SendCoordinates) {
					SendCoordinates response = (SendCoordinates) object;

					for (NetUser u : users) {
						if (!response.username.equalsIgnoreCase(u.getUsername()) && u.isConnected()) {
							u.getConnection().sendUDP(response);
						}
					}
				}
				if (object instanceof RemoveTile) {
					RemoveTile response = (RemoveTile) object;
					mysql.removeTile(response.x, response.y);
					handler.setToBeRemoved(response.x, response.y);

					for (NetUser u : users) {
						if (u.isConnected() && connection.getID() != u.getConnection().getID()) {
							u.getConnection().sendTCP(response);
						}
					}
				}
				if (object instanceof HittingBlock) {
					HittingBlock response = (HittingBlock) object;
					for (NetUser u : users) {
						if (u.isConnected() && !response.username.equalsIgnoreCase(u.getUsername())) {
							u.getConnection().sendTCP(response);
						}
					}
				}
				if (object instanceof Inventory) {
					Inventory response = (Inventory) object;

					for (NetUser u : users) {
						if (u.getAddress().equals(connection.getRemoteAddressTCP().getAddress())
								&& u.getPort() == connection.getRemoteAddressTCP().getPort()) {
							mysql.saveInventory(response.itemids, u.getUsername());
							KillClient request = new KillClient();
							u.getConnection().sendTCP(request);
						}
					}

				}
				
				if (object instanceof RegisterRequest) {
					RegisterRequest request = (RegisterRequest) object;
					String requesttext[] = request.text.split(",");
					RegisterResponse response = new RegisterResponse();
					if (mysql.checkIfUsernameTaken(requesttext[0])) {
						System.out.println(requesttext[0] + "   " + requesttext[1]);
						mysql.registerAccount(requesttext[0], requesttext[1]);
						response.text = "Erfolgreich eingeloggt";
					} else {
						response.text = "Der Username ist bereits vergeben. W�hle einen anderen!";
					}
					connection.sendTCP(response);
				}

			}

			/**
			 * Player wird removed aus der connections Liste users Liste und bei
			 * allen anderen auch removed
			 */
			public void disconnected(Connection connection) {
				RemovePlayer response = new RemovePlayer();
				for (NetUser u : users) {
					if (connection.equals(u.getConnection())) {
						connections.remove(connection);
						users.remove(u);
						response.username = u.getUsername();
						if (u.isConnected())
							server.sendToAllTCP(response);
						break;
					}
				}
			}
		});

		System.out.println(Utils.getTimerMillis() + " um den Server zu starten");
	}
}
