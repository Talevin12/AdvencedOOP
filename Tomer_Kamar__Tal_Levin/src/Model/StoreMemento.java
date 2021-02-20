package Model;

import java.util.HashMap;
import java.util.TreeMap;

public class StoreMemento {
	private TreeMap<String, Product> memento;
	private HashMap<Customer, String> customersMemento;
	
	public StoreMemento(TreeMap<String, Product> products, HashMap<Customer, String> customersMemento) {
		this.memento = new TreeMap<>(products);
		this.customersMemento = new HashMap<Customer, String>(customersMemento);
	}
	
	public StoreMemento(TreeMap<String, Product> products) {
		this.memento = new TreeMap<>(products);
	}
	
	public TreeMap<String, Product> getMemento() {
		return this.memento;
	}

	public HashMap<Customer, String> getCustomersMemento() {
		return customersMemento;
	}
	
	public void removeObserver(Customer c) {
		this.customersMemento.remove(c);
	}
	
	
}
