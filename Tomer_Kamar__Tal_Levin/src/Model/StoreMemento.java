package Model;

import java.util.TreeMap;

public class StoreMemento extends Store{
	private TreeMap<String, Product> memento;
	
	public StoreMemento(TreeMap<String, Product> products) {
		this.memento = new TreeMap<>(products);
	}
	
	public TreeMap<String, Product> getMemento() {
		return this.memento;
	}
}
