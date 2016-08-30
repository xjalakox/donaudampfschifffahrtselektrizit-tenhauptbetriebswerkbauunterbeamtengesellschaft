package net.registerlogin;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.ClientConnection;
import net.Network;
import net.Network.LoginRequest;
import net.Network.LoginResponse;
import net.Network.LoginRequest;
import net.Network.LoginResponse;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Login implements ActionListener {

	private JTextField username, password;
	public static JFrame frame;
	private JPanel panel;
	private JButton login;
	private static JLabel statuslabel;
	private boolean started_client = false;
	public static Client client = new Client();
	private ClientConnection clientConnection = new ClientConnection(client);

	public Login() {
		// Game game = new Game();

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

		statuslabel = new JLabel("Logge Dich bitte ein");

		panel.add(username);
		panel.add(password);
		panel.add(login);
		panel.add(statuslabel);

		panel.revalidate();

	}

	@Override
	public void actionPerformed(ActionEvent a) {
		LoginRequest request = new LoginRequest();
		request.text = username.getText() + "," + password.getText();
		client.sendTCP(request);
	}

	public static void setStatus(String text) {
		statuslabel.setText(text);
	}

	private String getStatus() {
		return statuslabel.getText();
	}

}
