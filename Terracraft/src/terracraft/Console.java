package terracraft;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entity.Player;

public class Console {

	private Player player;
	public String TextToDrawInConsole;
	public boolean consoleOpen;

	public Console(Player player) {
		this.player = player;
		TextToDrawInConsole = "";
	}

	public void render(Graphics g) {
		// TODO Ein Int den man gut nutzen kann um nur bei bestimmten Ticks
		// etwas zu zeichnen etc.
		if (consoleOpen) {
			renderConsole(g);
			if (TextToDrawInConsole != null) {
				String without = Utils.removeFirstChar(TextToDrawInConsole);
				if (!TextToDrawInConsole.contains("/") && !without.contains("/")) {
					renderKeyInput(g, TextToDrawInConsole);

				} else {
					renderKeyInput(g, "Console : " + TextToDrawInConsole);
				}
			}
			if (TextToDrawInConsole.length() == 0) {
				g.drawLine(320 + player.getX(), 210 + player.getY(), 320 + player.getX(), 771);
			}
		} else {
			player.renderLookingBlock(g);
		}
	}

	private void renderConsole(Graphics g) {
		Color ConsoleColor = new Color(200, 0, 200, 50);
		g.setColor(ConsoleColor);
		g.fillRect(315 + player.getX(), 10 + player.getY(), 300, 200);
		Color WritingText = new Color(0, 0, 0, 100);
		g.setColor(WritingText);
		g.drawRect(315 + player.getX(), 210 + player.getY(), 300, 30);
	}

	private void renderKeyInput(Graphics g, String keyToDraw) {
		g.setColor(Color.BLUE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(keyToDraw, player.getX() + 320, player.getY() + 230);

	}

	public void drawKeyInput(String keyText) {
		if (keyText.equalsIgnoreCase("backspace")) {
			Utils.removeLastChar(TextToDrawInConsole);
			TextToDrawInConsole = Utils.removeLastChar(TextToDrawInConsole);
		} else {
			TextToDrawInConsole = TextToDrawInConsole + keyText;
		}
	}

	public void executeCommand(String[] args) {
		if (args[0].equalsIgnoreCase("fly")) {
			player.fly = true;
		}
		if (args[0].equalsIgnoreCase("give")) {
			boolean breakout = false;
			for (int i = 0; i < args.length; i++) {
				if (Utils.isNotNull(args)) {
					// mininghandler.scrollbarTiles
					for (int j = 0; j < 39; j++) {
						if (player.Inventory_amount[j] == 0) {
							player.Inventory.add(j, Id.toId(args[1]));
							player.Inventory_amount[j] = Utils.toInt(args[2]);
							breakout = true;
						}
						if (breakout)
							break;
					}
					// player.Inventory.set(mininghandler.equippedTool,
					// Id.toId(SplitInventoryData[0]));
					// player.Inventory_amount[InventoryPlace] =
					// Utils.toInt(SplitInventoryData[1]);
				}
			}
			// 2 Arg Block
			// 3 Arg Anzahl
		}

	}

}
