package network.mysql;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Terracraft.Game;
import network.Client;
import network.packets.Packet00Login;
import network.packets.Packet03MySQL_Login;
import network.packets.Packet04MySQL_Register;

public class Login implements ActionListener {

	private JTextField username, password;
	public static JFrame frame;
	private JPanel panel;
	private JButton login;
	private static JLabel statuslabel;
	private boolean started_client = false;
	public static Client client;

	public Login() {
		Game game = new Game();
		client = new Client(game, 128);

		frame = new JFrame();
		frame.setTitle("Login");
		frame.setSize(1000, 100);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		frame.add(panel);

		username = new JTextField();
		Font bigFont = username.getFont().deriveFont(Font.PLAIN, 20f);
		username.setFont(bigFont);
		username.addActionListener(this);

		password = new JTextField();
		password.setFont(bigFont);

		login = new JButton("Einloggen");
		login.addActionListener(this);

		statuslabel = new JLabel("test");

		panel.add(username);
		panel.add(password);
		panel.add(login);
		panel.add(statuslabel);

		panel.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if (!started_client) {
			client = new Client(64);
			client.start();
			started_client = true;
		}
		String testusername = username.getText();
		String testpassword = password.getText();
		if (!testusername.equalsIgnoreCase("")) {
			if (!testpassword.equalsIgnoreCase("")) {
				client.setConnection(username.getText(), "localhost:1337");
				new Packet03MySQL_Login(username.getText(), password.getText()).send(client);

			} else {
				setStatus("Wie willst du Idiot dich ohne Passwort anmelden?!");
			}
		} else {
			setStatus("Wie willst du Idiot dich ohne Username anmelden?!");
		}
	}

	public static void setStatus(String text) {
		statuslabel.setText(text);
	}

	private String getStatus() {
		return statuslabel.getText();
	}

}
