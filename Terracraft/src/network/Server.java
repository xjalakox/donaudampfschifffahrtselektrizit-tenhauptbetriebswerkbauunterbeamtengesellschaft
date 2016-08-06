package network;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Entity.NetPlayer;
import Entity.Player;
import Terracraft.Handler;
import Terracraft.Id;
import Terracraft.Utils;
import Tile.source.Tile;
import Worldgen.Generator;
import network.abstracts.NetServer;
import network.abstracts.NetUser;
import network.mysql.MySQL;
import network.packets.Packet;
import network.packets.Packet.PacketTypes;
import network.packets.Packet00Login;
import network.packets.Packet01Disconnect;
import network.packets.Packet02Move;
import network.packets.Packet03MySQL_Login;
import network.packets.Packet04MySQL_Register;
import network.packets.Packet05Spawn;
import network.packets.Packet06Message;
import network.packets.Packet07AddTile;
import network.packets.Packet10RemoveTile;
import network.packets.Packet11Mine;

public class Server extends NetServer {

	private Map<NetUser, NetPlayer> players;
	public static MySQL mysql;
	private static int id;
	private ArrayList<Tile> LoadingTilesIntoList = new ArrayList<Tile>();
	public static Handler handler = new Handler("Server");
	public static int queryanzahl = 0, tileanzahl = 0;
	public static JFrame frame;
	public static JLabel playeranzahl, querylabel, tilelabel;
	public static boolean noframe;

	public Server(int port, int packetSize) {
		super(port, packetSize);
		super.start();
	}

	@Override
	protected void init(long time) {

		if (!noframe) {

			frame = new JFrame();
			frame.setTitle("Server");
			frame.setSize(240, 180);
			frame.setLocationRelativeTo(null);
			frame.addWindowListener(new WindowInput(this));

			playeranzahl = new JLabel("Spieleranzahl : 0");
			playeranzahl.setVisible(true);
			playeranzahl.setSize(100, 50);

			querylabel = new JLabel("Querys : 0");
			querylabel.setVisible(true);
			querylabel.setSize(100, 100);

			tilelabel = new JLabel("Tiles : 0");
			tilelabel.setVisible(true);
			tilelabel.setSize(100, 150);

			frame.add(playeranzahl);
			frame.add(querylabel);
			frame.add(tilelabel);
		}

		mysql.deleteTiles();

		players = new HashMap<NetUser, NetPlayer>();
		LoadingTilesIntoList = mysql.LoadMap();
		for (Tile ti : LoadingTilesIntoList) {
			handler.addTile(ti);
		}
		if (!noframe)
			tilelabel.setText("Tiles : " + handler.tile.size());

		System.out.println("[SERVER] Loaded " + handler.tile.size() + " Tiles");
		System.out.println("[SERVER] READY TO ACCEPT CONNECTIONS");
		System.out.println("[SERVER] Time needed to start : " + (System.currentTimeMillis() - time) + " ms");
		if (!noframe)
			frame.setVisible(true);
		
		Generator gen = new Generator(10, 20);
		
		gen.generateWorld();
		
		for(Tile ti : gen.tile){
			handler.addTile(ti);
		}

	}

	public static void main(String[] args) {
		System.out.println("[SERVER] STARTING");
		mysql = new MySQL();
		id = mysql.getId();

		if (args.length != 0) {
			if (args[0].equalsIgnoreCase("noframe")) {
				noframe = true;
			}
		}
		new Server(1337, 64);

	}

	protected void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
			Packet00Login packet00 = new Packet00Login(data);
			// System.out.println("[" + address.getHostAddress() + ":" + ":" +
			// port + "] " + packet00.getUsername() + " ist verbunden.");
			for (NetUser u : users) {
				if (u.getUsername().equalsIgnoreCase(packet00.getUsername())) {
					users.remove(u);
					break;
				}
			}
			NetUser user = new NetUser(packet00.getUsername(), address, port);
			NetPlayer player = new NetPlayer(packet00.getUsername(), packet00.getX(), packet00.getY(), 24, 24,
					Terracraft.Id.Player);
			users.add(user);
			players.put(user, player);
			packet00.send(this);
			for (Tile ti : handler.tile) {
				super.send(new Packet07AddTile(ti.getX(), ti.getY(), ti.getId().toString()).getData(), user);
			}
			for (NetUser u : users) {
				if (players.get(u) != null && !u.equals(user)) {
					super.send(new Packet00Login(players.get(u).getUsername(), players.get(u).getX(),
							players.get(u).getY()).getData(), user);
				}
			}
			if (!noframe)
				playeranzahl.setText("Spieleranzahl : " + users.size());
			break;
		case DISCONNECT:
			Packet01Disconnect packet01 = new Packet01Disconnect(data);
			for (NetUser u : users) {
				if (u.getUsername().equalsIgnoreCase(packet01.getUsername())) {
					int x = packet01.getX();
					int y = packet01.getY();

					mysql.setCoordinates(packet01.getUsername(), x, y);
					users.remove(u);
					break;
				}
			}
			packet01.send(this);
			if (!noframe)
				playeranzahl.setText("Spieleranzahl : " + users.size());
			break;
		case MOVE:
			Packet02Move packet02 = new Packet02Move(data);
			packet02.send(this);
			break;
		case MYSQL_LOGIN:
			Packet03MySQL_Login packet03 = new Packet03MySQL_Login(data);
			NetUser temporary_user = new NetUser(packet03.getUsername(), address, port);
			if (mysql.checkIfUserRegistered(packet03.getUsername(), packet03.getPassword())) {
				int x = mysql.getX(packet03.getUsername());
				int y = mysql.getY(packet03.getUsername());
				super.send(new Packet05Spawn(x, y).getData(), temporary_user);
				super.send(new Packet06Message("Du hast dich erfolgreich eingeloggt!").getData(), temporary_user);
			} else {
				super.send(new Packet06Message("Benutzername oder Passwort falsch!").getData(), temporary_user);
			}
			break;
		case MYSQL_REGISTER:
			Packet04MySQL_Register packet04 = new Packet04MySQL_Register(data);
			NetUser temporary_user2 = new NetUser(packet04.getUsername(), address, port);
			if (mysql.checkIfUsernameTaken(packet04.getUsername())) {
				mysql.registerAccount(packet04.getUsername(), packet04.getPassword());
				super.send(new Packet06Message("Du hast dich erfolgreich registriert und kannst dich nun anmelden!")
						.getData(), temporary_user2);
			} else {
				super.send(
						new Packet06Message("Der angegebene Username ist bereits benutzt! Suche dir einen anderen aus.")
								.getData(),
						temporary_user2);
			}
			break;
		case ADDTILE:
			Packet07AddTile packet07 = new Packet07AddTile(data);
			Tile tile = Id.getTile(packet07.getType(), packet07.getX(), packet07.getY());
			handler.addTile(tile);
			mysql.addTile(tile);
			for (NetUser u : users) {
				super.send(new Packet07AddTile(packet07.getX(), packet07.getY(), tile.getId().toString()).getData(), u);
			}

			if (!noframe)
				tilelabel.setText("Tiles : " + tileanzahl++);
			break;
		case REMOVETILE:
			Packet10RemoveTile packet10 = new Packet10RemoveTile(data);
			for (Tile ti : handler.tile) {
				if (ti.getX() == packet10.getX() && ti.getY() == packet10.getY()) {
					ti.setAsRemoved();
					mysql.removeTile(packet10.getX(), packet10.getY());
					break;
				}
			}
			for (NetUser u : users) {
				super.send(new Packet10RemoveTile(packet10.getX(), packet10.getY()).getData(), u);
			}
			if (!noframe)
				tilelabel.setText("Tiles : " + tileanzahl--);
			break;
		case MINE:
			Packet11Mine packet11 = new Packet11Mine(data);
			for (NetUser u : users) {
				super.send(
						new Packet11Mine(packet11.getClick(), packet11.getClicked(), packet11.getUsername()).getData(),
						u);
			}
			break;
		}
	}

	@Override
	public void tick() {
	}

	@Override
	protected void shutdown() {
		for (NetUser user : users) {
			new Packet01Disconnect(user.getUsername()).send(this);
		}
	}

	private class WindowInput implements WindowListener {

		private NetServer server;

		public WindowInput(NetServer server) {
			this.server = server;
		}

		@Override
		public void windowActivated(WindowEvent e) {

		}

		@Override
		public void windowClosed(WindowEvent e) {

		}

		@Override
		public void windowClosing(WindowEvent e) {
			server.stop();
		}

		@Override
		public void windowDeactivated(WindowEvent e) {

		}

		@Override
		public void windowDeiconified(WindowEvent e) {

		}

		@Override
		public void windowIconified(WindowEvent e) {

		}

		@Override
		public void windowOpened(WindowEvent e) {

		}
	}
}
