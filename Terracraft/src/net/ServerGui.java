package net;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import tile.source.Tile;

public class ServerGui extends JFrame implements ActionListener {

	private JButton stop_button;
	private JButton start_button;
	private JButton restart_button;
	private JButton switch_to_local_button;
	private JButton switch_to_online_button;

	private JLabel players;
	private JLabel status;
	private JLabel traffic_incomming_sec;
	private JLabel traffic_outgoing_sec;
	private JLabel traffic_incomming;
	private JLabel traffic_outgoing;

	private boolean starting, started, stopping, stopped, restarting, restarted;
	private boolean connecting, connected, disconnecting, disconnected;

	private Font font;

	public ServerGui(ServerConnection conn) {
		super.setSize(415, 500);
		super.setVisible(true);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setLocationRelativeTo(null);
		super.setLayout(null);
		super.setTitle("( ͡° ͜ʖ ͡°) Server GUI ( ͡° ͜ʖ ͡°)");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		font = new Font("Arial", Font.PLAIN, 28);

		stop_button = new JButton("Stop");
		stop_button.setVisible(true);
		stop_button.setBounds(300, 0, 100, 50);
		stop_button.setFont(font);
		stop_button.setBackground(Color.RED);
		stop_button.addActionListener(this);

		start_button = new JButton("Start");
		start_button.setVisible(true);
		start_button.setBounds(0, 0, 100, 50);
		start_button.setFont(font);
		start_button.setBackground(Color.GRAY);
		start_button.setEnabled(false);
		start_button.addActionListener(this);

		restart_button = new JButton("Restart");
		restart_button.setVisible(true);
		restart_button.setBounds(100, 0, 200, 50);
		restart_button.setFont(font);
		restart_button.setBackground(Color.YELLOW);
		restart_button.addActionListener(this);

		switch_to_local_button = new JButton("Lokale DB");
		switch_to_local_button.setVisible(true);
		switch_to_local_button.setBounds(0, 60, 200, 50);
		switch_to_local_button.setFont(font);
		switch_to_local_button.setBackground(Color.GRAY);
		switch_to_local_button.addActionListener(this);
		switch_to_local_button.setEnabled(false);

		switch_to_online_button = new JButton("Online DB");
		switch_to_online_button.setVisible(true);
		switch_to_online_button.setBounds(200, 60, 200, 50);
		switch_to_online_button.setBackground(new Color(120, 170, 64));
		switch_to_online_button.setFont(font);
		switch_to_online_button.addActionListener(this);

		players = new JLabel("Spieleranzahl : ");
		players.setVisible(true);
		players.setBounds(50, 120, 400, 50);
		players.setFont(font);

		status = new JLabel("Server gestartet : ");
		status.setVisible(true);
		status.setBounds(50, 180, 400, 50);
		status.setFont(font);

		traffic_incomming_sec = new JLabel("inc. Traffic/sec : ");
		traffic_incomming_sec.setVisible(true);
		traffic_incomming_sec.setBounds(50, 240, 400, 50);
		traffic_incomming_sec.setFont(font);

		traffic_outgoing_sec = new JLabel("out. Traffic/sec : ");
		traffic_outgoing_sec.setVisible(true);
		traffic_outgoing_sec.setBounds(50, 300, 400, 50);
		traffic_outgoing_sec.setFont(font);

		traffic_incomming = new JLabel("inc. Traffic : ");
		traffic_incomming.setVisible(true);
		traffic_incomming.setBounds(50, 360, 400, 50);
		traffic_incomming.setFont(font);

		traffic_outgoing = new JLabel("out. Traffic : ");
		traffic_outgoing.setVisible(true);
		traffic_outgoing.setBounds(50, 420, 400, 50);
		traffic_outgoing.setFont(font);

		super.add(stop_button);
		super.add(start_button);
		super.add(restart_button);
		super.add(switch_to_local_button);
		super.add(switch_to_online_button);
		super.add(players);
		super.add(status);
		super.add(traffic_incomming_sec);
		super.add(traffic_outgoing_sec);
		super.add(traffic_incomming);
		super.add(traffic_outgoing);
	}

	public void stopServer() {
		stopping = true;

		stop_button.setEnabled(false);
		stop_button.setBackground(Color.GRAY);
		start_button.setEnabled(true);
		start_button.setBackground(Color.GREEN);

		stopping = false;
		stopped = true;
	}

	public void startServer() {
		starting = true;

		start_button.setEnabled(false);
		start_button.setBackground(Color.GRAY);
		stop_button.setEnabled(true);
		stop_button.setBackground(Color.RED);

		starting = false;
		started = true;
	}

	public void changeToOfflineDB() {
		disconnecting = true;

		switch_to_online_button.setEnabled(true);
		switch_to_online_button.setBackground(new Color(120, 170, 64));
		switch_to_local_button.setEnabled(false);
		switch_to_local_button.setBackground(Color.GRAY);

		disconnecting = false;
		disconnected = true;

	}

	public void changeToOnlineDB() {
		connecting = true;

		switch_to_local_button.setEnabled(true);
		switch_to_local_button.setBackground(new Color(16, 78, 229));
		switch_to_online_button.setEnabled(false);
		switch_to_online_button.setBackground(Color.GRAY);

		connecting = false;
		connected = true;
	}
	
	public void clearTileList(){
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == stop_button) {
			if (!stopping) {
				stopServer();
				return;
			}
		}
		if (e.getSource() == start_button) {
			if (!starting) {
				startServer();
				return;
			}
		}
		if (e.getSource() == restart_button) {
			stopServer();
			startServer();
			return;
		}
		if (e.getSource() == switch_to_local_button) {
			if (!disconnecting) {
				changeToOfflineDB();
				return;
			}
		}
		if (e.getSource() == switch_to_online_button) {
			if (!connecting) {
				changeToOnlineDB();
				return;
			}
		}
	}

}
