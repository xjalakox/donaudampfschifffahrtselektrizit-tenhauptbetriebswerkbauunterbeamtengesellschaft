package crafting;

import java.util.Arrays;
import java.util.List;

import terracraft.Game;
import terracraft.Id;

public enum Recipe {
	/**
	 * Recipes die nur einen Block benötigen erstellt ihr wie bei Workbench, mir
	 * ist nach dem programmieren aufgefallen, dass es unnötig ist einmal nen
	 * Konstruktor zu haben für nur einen Block und einal für mehrere aber jetzt
	 * lass ich es so... -.- Wenn es euch zu kompliziert ist wie bei dem Recipe
	 * "Test" das so mit den Arrays zu machen könnt ihr das ganze auch in der
	 * initRecipes() machen. Habe dort ausgeklammert wie ihr es machen müsstet.
	 * Ist vielleicht einfacher aber man sieht dann nicht auf den ersten Blick
	 * was man für das Recipe braucht. Die craft Funktion habe ich noch nicht
	 * gemacht für Multiblöcke aber da ich darauf warte, dass der dicke Timo mir
	 * das macht wie bei Terraria mit dem craften hat das ganze noch etwas Zeit.
	 */

	Workbench("Workbench", "Grass", 4), Test("Grass", new String[] { "Grass", "Workbench" }, new int[] { 4, 2 });

	private String name;
	private String block;
	private String[] blocks;
	private int amount;
	private int[] amounts;
	private String type;
	static List<Recipe> recipeamount = Arrays.asList(Recipe.values());

	Recipe(String name) {
		this.name = name;
	}

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

	public static void initRecipes() {
		// Test Block

		// Test.setType("multi");
		// int[] TestAmounts = new int[2];
		// TestAmounts[0] = 4;
		// TestAmounts[1] = 2;
		// Test.setAmounts(TestAmounts);
		// String[] TestBlocks = new String[2];
		// TestBlocks[0] = "Grass";
		// TestBlocks[1] = "Workbench";
		// Test.setBlocks(TestBlocks);

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
		boolean[] parts;
		boolean allBlocksAvailabe = true;

		/**
		 * Um es kurz zu fassen: Das dieser scheiß Code hier funktioniert grenzt
		 * an ein Wunder. Wenn ihr nen Fehler habt beim Inventar öffnen wird es
		 * zu 99,9% hierdran liegen Viel Spaß beim Fehler beheben MfG Jannik
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
				parts = new boolean[e.blocks.length];
				for (int j = 0; j < Game.player.Inventory.size(); j++) {
					for (int l = 0; l < e.blocks.length; l++) {
						if (Game.player.Inventory.get(j) == Id.toId(e.getBlocks(l))
								&& Game.player.Inventory_amount[j] >= e.getAmounts(l)) {
							parts[l] = true;
						}
					}
				}
				// Wenn alle Blöcke die benötigt werden fürs Rezept im Inventar
				// sind und das Rezept noch nicht "aktiviert" ist dann wird es
				// nach den beiden for Schleifen geaddet
				for (int k = 0; k < recipes.length; k++) {
					if (recipes[k] == e)
						addItem = false;
				}
				for (int m = 0; m < parts.length; m++) {
					if (parts[m] == false) {
						allBlocksAvailabe = false;
					}
				}
				if (addItem && allBlocksAvailabe) {
					recipes[i] = e;
					i++;
				}

			}
		}
		return recipes;

	}

	public static void craftItem(Recipe recipe) {
		boolean enough = false;
		int stack = 0;
		for (int i = 0; i < Game.player.Inventory.size(); i++) {
			if (stack >= recipe.amount) {
				enough = true;
				// Hier müsste dann von den einzelnen Slots was entfernt werden,
				// ist mir aber zu komplex atm
			} else if (Game.player.Inventory.get(i).toString().equalsIgnoreCase(recipe.getBlock())) {
				if (Game.player.Inventory_amount[i] >= recipe.amount) {
					Game.player.Inventory_amount[i] -= recipe.amount;
					enough = true;
					break;
				}
			} else if (Game.player.Inventory.get(i).toString().equalsIgnoreCase(recipe.block)) {
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

	public String getBlocks(int place) {
		return blocks[place];
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

	public int getAmounts(int place) {
		return amounts[place];
	}

	public void setAmounts(int[] amounts) {
		this.amounts = amounts;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
