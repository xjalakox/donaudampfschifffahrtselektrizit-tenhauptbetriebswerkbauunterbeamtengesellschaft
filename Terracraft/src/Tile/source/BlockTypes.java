package Tile.source;

public enum BlockTypes {
	
	Stone("Pickaxe"), Dirt("shovel"), Pickaxe("Stone",4);
	
	private String tool,block;
	private int efficiency;
	
	BlockTypes(String tool){
		this.tool = tool;
	}
	
	BlockTypes(String block, int efficiency){
		this.block = block;
		this.efficiency = efficiency;
	}
	
	public String getTool(){
		return tool;
	}
	
	public String getBlock(){
		return block;
	}
	
	public int getEfficiency(){
		return efficiency;
	}
}