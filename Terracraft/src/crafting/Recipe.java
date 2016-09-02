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

	public static void craftItem(Recipe recipe) {
		boolean enough = false;
		int stack = 0;
		for (int i = 0; i < Game.player.Inventory.size(); i++) {
			if (stack >= recipe.amount) {

			} else if (Game.player.Inventory.get(i).equals(recipe.block)
					&& Game.player.Inventory_amount[i] >= recipe.amount) {
				Game.player.Inventory_amount[i] -= recipe.amount;
				enough = true;
			} else if (Game.player.Inventory.get(i).equals(recipe.block)) {
				stack+=recipe.amount;
			}
		}
		if (enough) {
			for (int i = 0; i < Game.player.Inventory_amount.length; i++) {
				if (Game.player.Inventory.get(i) == Id.Empty) {
					Game.player.Inventory.set(i, Id.toId(recipe.name));
					Game.player.Inventory_amount[i] = 1;
					break;
				} else if (Game.player.Inventory.get(i) == Id.toId(recipe.name)) {
					Game.player.Inventory.set(i, Id.toId(recipe.name));
					Game.player.Inventory_amount[i] += 1;
					break;
				}
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
