package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import Observer.StoreSender;

public class Store implements StoreSender{
	private TreeMap<String, Product> allProducts;
	private HashMap<Customer, String> observers;
	private static Store singleton = null;
	
	public Store() {
		this.allProducts = new TreeMap<>(new ascendingComparator());
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
	
	public HashMap<Customer, String> getObservers(){
		return this.observers;
	}
	
	public StoreMemento createMemento() {
		return new StoreMemento(getAllProducts());
	}
	
	public void setMemento(StoreMemento memento) {
		this.allProducts = memento.getMemento();
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
	
//	public String[] getAcceptedPrompotionCustomers() {
//		String[] custStr = new String[this.observers.size()];
//		int i = 0;
//		for(Entry<Customer, String> entry : this.observers.entrySet()) {
//			custStr[i] = entry.getValue();
//			i++;
//		}
//		
//		return custStr;
//	}
	
	private class ascendingComparator implements Comparator<String>{
		@Override
		public int compare(String catalog1, String catalog2) {
			return catalog1.compareTo(catalog2);
		}
	}
	
	private class descendingComparator implements Comparator<String>{
		@Override
		public int compare(String catalog1, String catalog2) {
			return catalog2.compareTo(catalog1);
		}
	}
	
	private class insertionOrder implements Comparator<String>{
		@Override
		public int compare(String catalog1, String catalog2) {
			return 1;
		}
	}
}
