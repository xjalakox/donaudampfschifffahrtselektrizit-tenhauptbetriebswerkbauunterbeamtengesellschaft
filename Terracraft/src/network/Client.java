package network;

import java.net.InetAddress;

import javax.swing.JFrame;

import network.mysql.*;
import Entity.Entity;
import Entity.NetPlayer;
import Entity.Player;


import Terracraft.Game;
import Terracraft.Id;
import Tile.source.Tile;
import network.abstracts.NetClient;
import network.packets.Packet;
import network.packets.Packet.PacketTypes;
import network.packets.Packet00Login;
import network.packets.Packet01Disconnect;
import network.packets.Packet02Move;
import network.packets.Packet05Spawn;
import network.packets.Packet06Message;
import network.packets.Packet07AddTile;
import network.packets.Packet10RemoveTile;
import network.packets.Packet11Mine;
import network.packets.Packet12InventoryData;

public class Client extends NetClient {

	private Game terracraft = null;

	public Client(Terracraft.Game game, int packetSize) {
		super(game, packetSize);
	}

	public Client(int packetSize) {
		super(packetSize);
	}

	protected void init() {
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
			if (!username.equals(packet00.getUsername())) {
				Game.handler.addEntity(
						new NetPlayer(packet00.getUsername(), packet00.getX(), packet00.getY(), 46, 96, Id.NetPlayer));
			}
			break;
		case DISCONNECT:
			Packet01Disconnect packet01 = new Packet01Disconnect(data);
			Game.handler.removePlayer(packet01.getUsername());
			break;
		case MOVE:
			Packet02Move packet02 = new Packet02Move(data);
			Game.handler.setPlayerPosition(packet02.getUsername(), packet02.getX(), packet02.getY(),
					packet02.getTool());
			break;
		case SPAWN:
			Packet05Spawn packet05 = new Packet05Spawn(data);

			JFrame frame = new JFrame("TerraCraft");
			terracraft = new Game(packet05.getX(), packet05.getY(), this, frame);
			frame.add(terracraft);
			frame.pack();
			frame.setBounds(0, 0, 320 * 4, 180 * 4);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setVisible(true);
			frame.setAlwaysOnTop(true);
			terracraft.start();
			break;
		case MESSAGE:
			Packet06Message packet06 = new Packet06Message(data);
			if (packet06.getText()
					.equalsIgnoreCase("Du hast dich erfolgreich registriert und kannst dich nun anmelden!")) {
				Register.setStatus(packet06.getText());
			} else if (packet06.getText()
					.equalsIgnoreCase("Der angegebene Username ist bereits benutzt! Suche dir einen anderen aus.")) {
				Register.setStatus(packet06.getText());
			} else if (packet06.getText().equalsIgnoreCase("Benutzername oder Passwort falsch!")) {
				Login.setStatus(packet06.getText());
			} else if (packet06.getText().equalsIgnoreCase("Du hast dich erfolgreich eingeloggt!")) {
				Login.setStatus(packet06.getText());
			} else {
				System.out.println("[FEHLER] MELDUNG SOLLTE AN CLIENT GESENDET WERDEN: " + packet06.getText());
			}
			break;
		case ADDTILE:
			Packet07AddTile packet07 = new Packet07AddTile(data);

			Tile ti = Id.getTile(packet07.getType(), packet07.getX(), packet07.getY());
			terracraft.handler.addTile(ti);
			break;
		case REMOVETILE:
			Packet10RemoveTile packet10 = new Packet10RemoveTile(data);

			for (Tile tile : terracraft.handler.tile2) {
				if (tile.getX() == packet10.getX() && tile.getY() == packet10.getY()) {
					terracraft.handler.setToBeRemoved(tile);
					break;
				}
			}
			break;
		case MINE:
			Packet11Mine packet11 = new Packet11Mine(data);
			for (Entity en : terracraft.handler.entity) {
				if (en.getId() == Id.NetPlayer) {
					if (((NetPlayer) en).getUsername().equalsIgnoreCase(packet11.getUsername())) {
						if (packet11.getClick() == 0) {
							en.setClick(false);
						} else {
							en.setClick(true);
						}
						if (packet11.getClicked() == 0) {
							en.setClicked(false);
						} else {
							en.setClicked(true);
						}
					} else {
					}
				}
			}
			break;
		case INVENTORY:
			Packet12InventoryData packet12 = new Packet12InventoryData(data);
			
		}

	}

	@Override
	protected void shutdown() {

	}
}
