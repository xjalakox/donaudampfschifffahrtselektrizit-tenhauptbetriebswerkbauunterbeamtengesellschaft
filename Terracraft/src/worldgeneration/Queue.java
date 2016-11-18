package worldgeneration;
import java.awt.Color;

public class Queue {

	Element first, last;

	public void push(int value, int x, Color color) {
		if (first == null) {
			first = new Element(x, value, color);
			last = first;
		} else {
			if (first.next == null) {
				last = new Element(x, value, color);
				first.next = last;
			} else {
				Element oldlast = last;
				last = new Element(x, value, color);
				oldlast.next = last;
			}
		}
	}

	public void pop() {
		first = first.next;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public String front() {
		if (!isEmpty()) {
			return Integer.toString(first.getX());
		}
		return "Null";
	}
}
