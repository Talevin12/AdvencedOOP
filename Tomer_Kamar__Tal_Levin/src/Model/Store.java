package Model;

import java.util.HashMap;
import java.util.TreeMap;

import java.util.Map.Entry;

import Observer.StoreSender;

public class Store implements StoreSender {
	private TreeMap<String, Product> allProducts;
	private HashMap<Customer, String> observers;
	private static Store singleton = null;
	
	public Store() {
		this.observers = new HashMap<>();
	}
	
	public static Store getInstance() 
    { 
        if (singleton == null) 
        	singleton = new Store(); 
  
        return singleton;
    } 
	
	public TreeMap<String, Product> getAllProducts() {
		return allProducts;
	}
	
	public void setAllProducts(TreeMap<String, Product> tm) {
		this.allProducts = tm;
	}
	
	public HashMap<Customer, String> getObservers(){
		return this.observers;
	}
	
	public StoreMemento createMemento() {
		return new StoreMemento(getAllProducts(), getObservers());
	}
	
	public void setMemento(StoreMemento memento) {
		this.allProducts = memento.getMemento();
		this.observers = memento.getCustomersMemento();
	}
	
	
	@Override
	public void sendPromotions() {
		Customer c;
		String name;
		for(Entry<String, Product> entry : this.allProducts.entrySet()) {
			c = entry.getValue().getCustomer();
			if(c.isAcceptPromotions()) {
				name = c.receivePromotion();
				this.observers.put(c, name);
			}
		}
	}
}
