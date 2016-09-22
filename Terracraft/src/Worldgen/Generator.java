package worldgen;


public class Generator {

	/**
	 * Ich denke mal der Code ist Müll und wir werden nen neuen machen. Evtl.
	 * können wir uns auch hier dran orientieren
	 * 
	 */

	/*
	 * public static void main(String[] args) { Generator gen = new
	 * Generator(80, 20); gen.generateWorld(); }
	 * 
	 * private int dirttiles; private int dirtscale; private int stonescale;
	 * private String[] scale = new String[100]; private ArrayList<String>
	 * tileString = new ArrayList<String>(); public ArrayList<Tile> tile = new
	 * ArrayList<Tile>();
	 * 
	 * public Generator(int dirtscale, int stonescale) { if (dirtscale > 100) {
	 * System.out.println("Fehler"); } else { this.dirtscale = dirtscale / 2; }
	 * if (stonescale > 100) { System.out.println("Fehler"); } else {
	 * this.stonescale = stonescale / 2; } for (int i = 0; i < this.dirtscale;
	 * i++) { scale[i] = "dirt"; } }
	 * 
	 * public void generateWorld() { int x = 0, y = 320; for (int i = 0; i <
	 * 10000; i++) { int a = Utils.RandomInt(scale.length); if
	 * (Utils.isNotNull(scale[a]) && scale[a].equalsIgnoreCase("dirt")) {
	 * tileString.add("Grass"); } } dirttiles = tileString.size(); while
	 * (dirttiles >= 100) { System.out.println(dirttiles); generateDirtHill(x,
	 * y); x += 960; } // System.out.println(tileString.size()); //
	 * System.out.println(dirttiles); }
	 * 
	 * private void generateDirtHill(int x, int y) { int usedBlocks = 0; int
	 * layers[] = new int[10]; for (int i = 0; i < layers.length; i++) {
	 * layers[i] = Utils.RandomInt(i * 5 + 1); if (i > 0) { layers[i] = layers[i
	 * - 1] + Utils.RandomInt(5); } } for (int i = 0; i < layers.length; i++) {
	 * for (int j = 0; j < layers[i]; j++) { usedBlocks++; tile.add(new Grass(j
	 * * 32 + x, y + i * 32, 32, 32, Id.Grass)); } } for (int i = 0; i <
	 * layers.length; i++) { for (int j = 0; j < layers[i]; j++) { usedBlocks++;
	 * tile.add(new Grass(j - (j*2) * 32 + x, y + i * 32, 32, 32, Id.Grass)); }
	 * } dirttiles -= usedBlocks; }
	 */

}
