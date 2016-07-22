package Terracraft;

import Tile.*;

public enum Id {
	player, TestTile, grass ;

	public String toString() {
		switch (this) {
		case player:
			return null;
		case TestTile:
			return "TestTile";
		case grass:
			return "grass";
		}
		return null;
	}

	public static Tile getTile(String tileId, int x, int y) {
		switch (tileId) {
		case "player":
			return null;
		case "TestTile":
			return new TestTile(x, y, 64, 64, Id.TestTile);
		case "grass":
			return new Grass(x, y, 64, 64, Id.grass);
		}
		return null;
	}

}
