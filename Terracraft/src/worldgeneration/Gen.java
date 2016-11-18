package worldgeneration;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gen {

	private Random r = new Random();
	private int first;
	private JFrame frame = new JFrame();
	private JPanel panel;
	private Queue queue = new Queue();
	private ArrayList<Element> list = new ArrayList<Element>();
	private ArrayList<Element> firstLayer = new ArrayList<Element>();
	private int xCoordinate, yCoordinate;
	private ArrayList<Element> copper = new ArrayList<Element>();
	private ArrayList<Element> emptyQueue = new ArrayList<Element>();
	public ArrayList<Element> newList = new ArrayList<Element>();

	public Gen() {
		frame.setVisible(true);
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				render(g);
			}
		};
		frame.add(panel);
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 1000, 1000);
		emptyQueue(g, 0, 70, 70, 100, 2, 25);
		renderList(g);
	}

	public void renderList(Graphics g) {
		makeList();
		for(int i=0;i<newList.size();i++){
			g.setColor(newList.get(i).color);
			g.fillRect(newList.get(i).getX(), newList.get(i).value, 5, 5);
		}
	}

	private void emptyQueue(Graphics g, int start, int intervall, int blocks,
			int hight, int fläche, int treeDensity) {
		g.setColor(Color.green);
		int nextValue = 0;
		if (start == 0) {
			nextValue = DataGeneration(hight) + 500;
		} else {
			nextValue = start;
		}
		int rest = nextValue % 5;
		nextValue -= rest;
		for (int i = 0; i < blocks; i++) {
			queue.push(nextValue, i * intervall, Color.green);
			Element e = new Element(nextValue, i * intervall,Color.blue);
			emptyQueue.add(e);
			nextValue = DataGeneration(hight) + 500;
			rest = nextValue % 5;
			nextValue -= rest;
		}
		nextPlace(g, intervall, blocks - 1, fläche, treeDensity);
	}

	private void nextPlace(Graphics g, int intervall, int blocks, int fläche,
			int treeDencity) {
		int nextValue = 0, oldValue = 0;
		for (int i = 0; i < blocks; i++) {
			int distance = i * intervall + 5;
			int first = queue.first.getX();
			queue.pop();
			int next = queue.first.getX();
			queue.push(first, i * intervall, Color.blue);
			oldValue = first;
			double tmp = next - first;
			while (distance < (i + 1) * intervall) {
				nextValue = 0;
				double steigung = ((tmp) / (((i + 1) * intervall) - (distance * intervall)))
						* distance;
				if (steigung > 0.1 && distance > i * intervall + (5 * fläche)
						&& steigung < 0.2) {
					nextValue = oldValue - 5;
				} else if (steigung < -0.1
						&& distance > i * intervall + (10 * fläche)
						&& steigung > -0.2) {
					nextValue = oldValue + 5;
				} else if (steigung > 0.2
						&& distance > i * intervall + (5 * fläche)) {
					nextValue = oldValue - 10;
				} else if (steigung < -0.2
						&& distance > i * intervall + (10 * fläche)) {
					nextValue = oldValue + 5;
				} else {
					nextValue = oldValue;
				}
				queue.push(nextValue, distance, Color.blue);
				Element e = new Element(nextValue, distance, Color.blue);
				firstLayer.add(e);
				distance += 5;
				tmp = next - nextValue;
				oldValue = nextValue;
			}
		}
		int tmp = queue.first.getX();
		queue.pop();
		queue.push(tmp, blocks * intervall, Color.blue);
		fill(g, intervall);
		plantTrees(g, treeDencity, blocks);
	}

	private void plantTrees(Graphics g, int treeDencity, int blocks) {
		int lastTree = 0;
		for (int i = 0; i < firstLayer.size() - 3; i++) {
			if (firstLayer.get(i).value == firstLayer.get(i + 1).value
					&& firstLayer.get(i).value == firstLayer.get(i + 3).value
					&& lastTree < firstLayer.get(i).getX() - treeDencity) {
				g.setColor(Color.black);
				g.fillRect(firstLayer.get(i + 1).getX(),
						firstLayer.get(i + 1).value - 5, 5, 5);
				lastTree = firstLayer.get(i).getX();
			}
		}
		int xCoordinate = 20; // r.nextInt(firstLayer.size());
		this.xCoordinate = firstLayer.get(xCoordinate).value;
		yCoordinate = firstLayer.get(xCoordinate).getX();
	}

	public void fill(Graphics g, int intervall) {
		int number = firstLayer.size();
		g.setColor(Color.green);
		for (int i = 0; i < number; i++) {
			g.setColor(Color.green);
			int y = queue.first.getX() + 5;
			while (y <= queue.first.getX() + r.nextInt(15) + 15) {
				queue.push(i * 5, y, Color.green);
				y += 5;
			}
			g.setColor(Color.gray);
			while (y < 900) {
				queue.push(i * 5, y, Color.gray);
				y += 5;
			}
			queue.pop();
		}

		convertToArray();
	}

	public int DataGeneration(int hight) {
		int y = first;
		int zufallszahl = r.nextInt(2);
		if (zufallszahl == 0) {
			y -= r.nextInt(hight) - hight / 1.5;
		} else {
			y += r.nextInt(hight) - hight / 1.5;
		}
		first = y;
		return y;
	}

	public void convertToArray() {
		while (!queue.isEmpty()) {
			list.add(queue.first);
			queue.pop();
		}
	}
	
	public void makeList(){
		for(int i=0;i<emptyQueue.size();i++){
			newList.add(emptyQueue.get(i));
		}
		for(int i=0;i<firstLayer.size();i++){
			newList.add(firstLayer.get(i));
		}
		for(int i=0;i<list.size();i++){
			newList.add(list.get(i));
		}
		Collections.sort(newList,new Comparator<Element>() {

			@Override
			public int compare(Element e2, Element e1){	
				Integer x1=e1.getX();
				Integer x2=e2.getX();

		        return  x1.compareTo(x2);
		    }
		} );
		
	}
}
