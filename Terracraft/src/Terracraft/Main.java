package Terracraft;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		Game pokemon = new Game();
		JFrame frame = new JFrame("Pokemon");
		frame.add(pokemon);
		frame.pack();
		frame.setBounds(0,0,1280,790);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		pokemon.start();
	}

}
