package network.mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import Terracraft.Id;
import Tile.TestTile;
import Tile.source.Tile;
import network.Server;

public class MySQL {
	private java.sql.Connection myConn;
	private Statement query;

	public MySQL() {
		connect();
	}

	public void connect() {
		String host = "jdbc:mysql://80.82.219.161:3306/terra";
		String user = "terra";
		String pw = "qwZm5VAWbLPFs3Tc";
		try {
			System.out.println("[MySQL] Trying to connect to the MySQL Server");
			myConn = DriverManager.getConnection(host, user, pw);
			query = myConn.createStatement();
			System.out.println("[MySQL] Connected to the MySQL Server");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			myConn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkIfUserRegistered(String username, String password) {
		try {
			ResultSet myRs = query.executeQuery("select * from terra");
			updateQueryAmount();
			while (myRs.next()) {
				if (myRs.getString("username").equalsIgnoreCase(username)) {
					if (myRs.getString("password").equalsIgnoreCase(password)) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int getX(String username) {
		try {
			updateQueryAmount();
			ResultSet myRs = query.executeQuery("select * from terra");
			while (myRs.next()) {
				if (myRs.getString("username").equalsIgnoreCase(username)) {
					return myRs.getInt("x");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int getY(String username) {
		try {
			updateQueryAmount();
			ResultSet myRs = query.executeQuery("select * from terra");
			while (myRs.next()) {
				if (myRs.getString("username").equalsIgnoreCase(username)) {
					return myRs.getInt("y");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void setCoordinates(String username, int x, int y) {
		try {
			updateQueryAmount();
			query.executeUpdate(
					"UPDATE terra SET x = " + x + ", y = " + y + " WHERE username = " + "'" + username + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkIfUsernameTaken(String username) {
		try {
			ResultSet myRs = query.executeQuery("select * from terra");
			updateQueryAmount();
			while (myRs.next()) {
				if (myRs.getString("username").equalsIgnoreCase(username)) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void registerAccount(String username, String password) {
		try {
			query.executeUpdate(
					"INSERT INTO terra " + "VALUES (" + getId() + ", '" + username + "', '" + password + "', 0, 0) ");
			updateQueryAmount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		int id = 1;

		try {
			ResultSet myRs = query.executeQuery("select id from terra");
			while (myRs.next()) {
				id++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	private void updateQueryAmount() {
		Server.queryanzahl++;
		Server.querylabel.setText("Querys : " + Server.queryanzahl);

	}

	public ArrayList<Tile> LoadMap() {
		try {
			ArrayList<Tile> LoadingTilesIntoList = new ArrayList<Tile>();
			ResultSet myRs = query.executeQuery("select * from blocks");
			updateQueryAmount();
			while (myRs.next()) {
				Tile t = Id.getTile(myRs.getString("TileID"), myRs.getInt("x"), myRs.getInt("y"));
				LoadingTilesIntoList.add(t);
			}
			return LoadingTilesIntoList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * saveMap OUTDATED
	 * 
	 * 
	 */

	/*
	 * public void saveMap(LinkedList<Tile> list){ try { int id = 0;
	 * query.executeUpdate("DELETE FROM blocks"); for(Tile ti : list){ int x =
	 * ti.getX(); int y = ti.getY(); if(ti.getId()==Id.test){
	 * System.out.println("INSERT INTO blocks " + "VALUES (" + id +", " + x +
	 * ", " + y + ", " + "'TestTile')");
	 * query.executeUpdate("INSERT INTO blocks " + "VALUES (" + id +", " + x +
	 * ", " + y + ", " + "'TestTile')"); } } updateQueryAmount(); } catch
	 * (SQLException e) { e.printStackTrace(); } }
	 */

	public void addTile(Tile ti) {
		try {
			int x = ti.getX(); 
			int y = ti.getY();

			query.executeUpdate("INSERT INTO blocks " + "VALUES (" + 0 + ", " + x + ", " + y + ", " + "'"
					+ ti.getId().toString() + "')");

			updateQueryAmount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeTile(int x, int y) {
		try {

			
			//DELETE FROM `blocks` WHERE `x` = 640 AND `y` = 512
			query.executeUpdate("DELETE FROM `blocks` WHERE `x` = " + x + " AND `y` = " + y);
			updateQueryAmount();

			updateQueryAmount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isAdmin(String username) {
		try {
			ResultSet myRs = query.executeQuery("select 'isAdmin' from terra WHERE 'username' = " + username);
			updateQueryAmount();
			while (myRs.next()) {
				if (myRs.getInt("isAdmin") == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void deleteTiles(){
		try {
			query.executeUpdate("DELETE FROM `blocks`");
			updateQueryAmount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
