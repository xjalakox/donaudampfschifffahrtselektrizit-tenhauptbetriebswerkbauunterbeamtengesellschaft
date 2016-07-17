package network.mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

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
		String pw = "7f7f1e6";
		try {
			myConn = DriverManager.getConnection(host, user, pw);
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
		Server.querylabel.setText("Querys : " + Server.queryanzahl );
		
	}
}
