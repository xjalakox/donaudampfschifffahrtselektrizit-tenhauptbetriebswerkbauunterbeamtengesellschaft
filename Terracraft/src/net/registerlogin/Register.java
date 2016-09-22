package net.registerlogin;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.esotericsoftware.kryonet.Client;

import net.ClientConnection;
import net.Network.RegisterRequest;

public class Register implements ActionListener {

	private JTextField username, password;
	private JFrame frame;
	private JPanel panel;
	private JButton login;
	private static JLabel statuslabel;
	public static Client client = new Client();
	public static boolean killClient;

	
	public static void main(String args[]) {
		new Register();
	}
	
	public Register() {
		new ClientConnection(client);
		if (killClient) {
			System.exit(0);
		}
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

		statuslabel = new JLabel("");

		panel.add(username);
		panel.add(password);
		panel.add(login);
		panel.add(statuslabel);

		panel.revalidate();
	}

	public void actionPerformed(ActionEvent a) {
		
		
		String checkLengthofUsername = username.getText();
		String checkLengthofPassword = password.getText();
		if (checkLengthofUsername.length() <= 12) {
			if (checkLengthofPassword.length() >= 4) {
				RegisterRequest request = new RegisterRequest();
				request.text = username.getText() + "," + password.getText();
				client.sendTCP(request);
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
