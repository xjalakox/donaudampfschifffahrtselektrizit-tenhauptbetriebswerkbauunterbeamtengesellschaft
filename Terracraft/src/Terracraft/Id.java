package Terracraft;

import Tile.*;

public enum Id {
	player(00), TestTile(01), grass(02);

	private Id(int id) {

	}

	public String toString() {
		switch (this) {
		case player:
			return "noTile";
		case TestTile:
			return "TestTile";
		case grass:
			return "grass";
		}
		return null;
	}

	public Tile getTile(int x, int y) {
		switch (this) {
		case player:
			return null;
		case TestTile:
			return new TestTile(x, y, 64, 64, Id.TestTile);
		case grass:
			return "grass";
		}
		return null;
	}

}
