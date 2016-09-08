package crafting;

import java.util.Arrays;
import java.util.List;

import Terracraft.Game;
import Terracraft.Id;
import Terracraft.Utils;
import sun.security.x509.IssuerAlternativeNameExtension;

public enum Recipe {

	Workbench("Workbench", "Grass", 4), Test("Grass", "Workbench", 2);
	private String name;
	private String block;
	private String[] blocks;
	private int amount;
	private int[] amounts;
	private String type;
	static List<Recipe> recipeamount = Arrays.asList(Recipe.values());

	Recipe(String name, String[] blocks, int[] amounts) {
		this.name = name;
		this.blocks = blocks;
		this.amounts = amounts;
		this.type = "multi";
	}

	Recipe(String name, String block, int amount) {
		this.name = name;
		this.block = block;
		this.amount = amount;
		this.type = "single";
	}

	public static Recipe[] getRecipes() {
		Recipe[] recipes = new Recipe[recipeamount.size()];
		int i = 0;
		for (Recipe e : recipeamount) {
			recipes[i] = e;
			i++;
		}
		return recipes;
	}

	public static Recipe[] getCraftableRecipes() {
		Recipe[] recipes = new Recipe[recipeamount.size()];
		boolean addItem = true;
		int i = 0;

		/**
		 * Um es kurz zu fassen: Das dieser scheiß Code hier funktioniert grenz
		 * an ein Wunder. Wenn ihr nen Fehler habt beim Inventar öffnen wird es
		 * zu 99,9% hierdran liegen Viel Spaß beim Fehler beheben
		 * 
		 */

		for (Recipe e : recipeamount) {
			if (e.getType().equalsIgnoreCase("single")) {
				for (int j = 0; j < Game.player.Inventory.size(); j++) {
					if (Game.player.Inventory.get(j) == Id.toId(e.getBlock())
							&& Game.player.Inventory_amount[j] >= e.getAmount()) {

						// Wenn das Item noch nicht in der Liste ist von den
						// anzuzeigenden Items wird es zur Liste geaddet
						for (int k = 0; k < recipes.length; k++) {
							if (recipes[k] == e) {
								addItem = false;
							}
						}
						if (addItem) {
							recipes[i] = e;
							i++;
						}
					}
				}
			} else {
				System.out.println("Diese multi scheiße wird erst in der Version 1.33.7 unterstützt");
			}
		}
		return recipes;
	}

	public static void craftItem(Recipe recipe) {
		boolean enough = false;
		int stack = 0;
		for (int i = 0; i < Game.player.Inventory.size(); i++) {
			if (stack >= recipe.amount) {
				// Hier müsste dann von den einzelnen Slots was entfernt werden,
				// ist mir aber zu komplex atm
			} else if (Game.player.Inventory.get(i).equals(recipe.block)
					&& Game.player.Inventory_amount[i] >= recipe.amount) {
				Game.player.Inventory_amount[i] -= recipe.amount;
				enough = true;
				break;
			} else if (Game.player.Inventory.get(i).equals(recipe.block)) {
				stack += recipe.amount;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String[] getBlocks() {
		return blocks;
	}

	public void setBlocks(String[] blocks) {
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

	public String getType() {
		return this.type;
	}

}
