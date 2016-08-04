package network.mysql;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Terracraft.Game;
import network.Client;
import network.packets.Packet04MySQL_Register;

public class Register implements ActionListener {

	private JTextField username, password;
	private JFrame frame;
	private JPanel panel;
	private JButton login;
	private static JLabel statuslabel;
	private boolean started_client = false;
	public static Client client;

	public Register() {
		Game game = new Game();
		client = new Client(game, 128);

		frame = new JFrame();
		frame.setTitle("Register");
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

		login = new JButton("Registrieren");
		login.addActionListener(this);

		statuslabel = new JLabel("test");

		panel.add(username);
		panel.add(password);
		panel.add(login);
		panel.add(statuslabel);

		panel.revalidate();
	}

	public void actionPerformed(ActionEvent a) {
		if (!started_client) {
			client = new Client(128);
			client.start();
			started_client = true;
		}
		String checkLengthofUsername = username.getText();
		String checkLengthofPassword = password.getText();
		if (checkLengthofUsername.length() <= 12) {
			if (checkLengthofPassword.length() >= 4) {
				client.setConnection(username.getText(), "localhost:1337");
				new Packet04MySQL_Register(username.getText(), password.getText()).send(client);
			}else{
				setStatus("Passwort zu kurz");
			}
		}else{
			setStatus("Username zu lang!");
		}
	}

	public static void setStatus(String text) {
		statuslabel.setText(text);
	}

}
