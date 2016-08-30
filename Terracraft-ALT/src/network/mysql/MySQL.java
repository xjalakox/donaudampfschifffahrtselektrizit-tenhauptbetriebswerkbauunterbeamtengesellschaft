package network.mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Terracraft.Id;
import Terracraft.Utils;
import Tile.source.Tile;

public class MySQL {
	private java.sql.Connection myConn;
	private Statement query;

	public MySQL() {
		connect();
	}

	public void connect() {
		String host = "jdbc:mysql://db4free.net:3306/jalako";
		String users = "jalako";
		String pw = "4r4r4r4r";
		try {
			System.out.println("[MySQL] Trying to connect to the MySQL Server");
			myConn = DriverManager.getConnection(host, users, pw);
			System.out.println("[MySQL] Connected to the MySQL Server");
			query = myConn.createStatement();
			
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
			ResultSet myRs = query.executeQuery("select * from users");
			;
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
			;
			ResultSet myRs = query.executeQuery("select * from users");
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
			;
			ResultSet myRs = query.executeQuery("select * from users");
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
			;
			query.executeUpdate(
					"UPDATE users SET x = " + x + ", y = " + y + " WHERE username = " + "'" + username + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkIfUsernameTaken(String username) {
		try {
			ResultSet myRs = query.executeQuery("select * from users");
			;
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
			query.executeUpdate("INSERT INTO users " + "VALUES (" + getId() + ", '" + username + "', '" + password
					+ "', 0, 0, 0) ");
			createInventory(getId());
			;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		int id = 1;

		try {
			ResultSet myRs = query.executeQuery("select id from users");
			while (myRs.next()) {
				id++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(id);
		return id;
	}

	public ArrayList<Tile> LoadMap() {
		try {
			ArrayList<Tile> LoadingTilesIntoList = new ArrayList<Tile>();
			ResultSet myRs = query.executeQuery("select * from blocks");
			;
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

	public void addTile(Tile ti) {
		try {
			int x = ti.getX();
			int y = ti.getY();

			query.executeUpdate("INSERT INTO blocks VALUES (" + 0 + ", " + x + ", " + y + ", " + "'"
					+ ti.getId().toString() + "')");
			x += 64;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isAdmin(String username) {
		try {
			ResultSet myRs = query.executeQuery("select 'isAdmin' from users WHERE 'username' = " + username);
			;
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

	public void deleteTiles() {
		try {
			query.executeUpdate("DELETE FROM `blocks`");
			;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeTile(int x, int y) {
		try {

			query.executeUpdate("DELETE FROM `blocks` WHERE `x` = " + x + " AND `y` = " + y);
			;

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createInventory(int id) {
		try {

			id -= 1;
			query.executeUpdate("INSERT INTO inventorys VALUES (" + 0 + ", " + id + ", " + 0 + ", " + 0 + ", " + 0
					+ ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0
					+ ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0
					+ ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0
					+ ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0
					+ ", " + 0 + ")");
			;

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String[] loadInventory(String username) {
		try {
			ResultSet myRs = query
					.executeQuery("select * from inventorys WHERE user_id = '" + (getUserIdByName(username) - 1) + "'");
			int itemid = 1;
			String items[] = new String[40];
			while (myRs.next()) {
				for (int i = 0; i < 40; i++) {
					System.out.println(items[i]);
					items[itemid - 1] = myRs.getString("slot" + Utils.toString(itemid));
					itemid++;
				}
			}

			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveInventory(String inventorydata, String username, int ArraySlot) {
		try {
			if (ArraySlot <= 1) {
				Utils.startTimerMillis();
			}
			;
			query.executeUpdate("UPDATE inventorys SET `slot" + ArraySlot + "` = " + "'" + inventorydata + "'"
					+ " WHERE `user_id` = '" + getUserIdByName(username) + "'");
			if (ArraySlot >= 40) {
				System.out.println(Utils.getTimerMillis() + " zum speichern des Inventars von " + username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getUserIdByName(String username) {
		try {
			ResultSet myRs = query.executeQuery("select id from users WHERE username = " + "'" + username + "'");
			;
			while (myRs.next()) {
				return myRs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
