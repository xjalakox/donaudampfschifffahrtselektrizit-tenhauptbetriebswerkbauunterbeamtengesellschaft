package worldgeneration;
import java.awt.Color;


public class Element {

	private int x;
	int value;
	Element next;
	Color color;
	
	public Element(int x,int value, Color color){
		this.setX(value);
		this.value=x;
		this.color=color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	
}
