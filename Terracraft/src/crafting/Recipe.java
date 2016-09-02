package crafting;

import Terracraft.Game;
import Terracraft.Id;

public enum Recipe {

	Workbench("Workbench", Id.Grass, 4);
	private String name;
	private Id block;
	private Id[] blocks;
	private int amount;
	private int[] amounts;

	Recipe(String name, Id[] blocks, int[] amounts) {
		this.name = name;
		this.blocks = blocks;
		this.amounts = amounts;
	}

	Recipe(String name, Id block, int amount) {
		this.name = name;
		this.block = block;
		this.amount = amount;
	}

	public static void craftItem(Id block, int amount) {
		for (int i = 0; i < Game.player.Inventory.size(); i++) {
			if (Game.player.Inventory.get(i).equals(block) && Game.player.Inventory_amount[i] >= amount) {
				Game.player.Inventory_amount[i] -= amount;
				for (int j = 0; j < Game.player.Inventory_amount.length; j++) {
					if (Game.player.Inventory.get(j).equals(Id.Empty)) {
						System.out.println(Game.player.Inventory.get(j));
						Game.player.Inventory.add(block);
						Game.player.Inventory_amount[j] = amount;
					} else {
						System.out.println(Game.player.Inventory.get(j));
					}
				}
				break;
			}
		}
		
		
	}

	public void craftItem(Id[] blocks, int[] amounts) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Id getBlock() {
		return block;
	}

	public void setBlock(Id block) {
		this.block = block;
	}

	public Id[] getBlocks() {
		return blocks;
	}

	public void setBlocks(Id[] blocks) {
		this.blocks = blocks;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int[] getAmounts() {
		return amounts;
	}

	public void setAmounts(int[] amounts) {
		this.amounts = amounts;
	}

}
