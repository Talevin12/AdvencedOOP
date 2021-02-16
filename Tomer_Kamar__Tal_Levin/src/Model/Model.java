package Model;

import java.util.Map.Entry;
import java.util.TreeMap;
import ModelCommands.ModelCommands;

public class Model {
	private Store store;
	private ModelCommands modelCommands;
	private StoreMemento memento;
	
	public Model() {
		this.store = new Store();
		this.modelCommands = new ModelCommands(this);
		this.memento = this.store.createMemento();
	}
	
	public ModelCommands getModelCommands() {
		return this.modelCommands;
	}
	
	public Store getStore() {
		return this.store;
	}
	
	public void addProduct(String catalog, String pName, int storePrice, int custPrice, String custName, String phoneNum, boolean promotions) {
		Product product = new Product(pName, storePrice, custPrice, new Customer(custName, phoneNum, promotions));
		
		this.memento = this.store.createMemento();
		
		this.store.getAllProducts().put(catalog, product);
	}
	
	public void undoInsert() {
		this.store.setMemento(this.memento);
	}
	
	public String[] showAllProducts() {
		TreeMap<String, Product> map = this.store.getAllProducts();
		String[] str = new String[map.size()];
		int i = 0;
		for(Entry<String, Product> entry : map.entrySet()) {
			str[i] = "Catalog number: "+ entry.getKey() +" | "+ entry.getValue().toString();
			i++;
		}
		
		return str;
	}
	
	public String searchProduct(String catalog) {
		Product product = this.store.getAllProducts().get(catalog);
		if(product == null)
			return "No such product was found :(";
		return product.toString();
			
	}
	
	public String[] showProfit() {
		TreeMap<String, Product> map = this.store.getAllProducts();
		String[] str = new String[map.size()+1];
		int sum = 0;
		int i = 0;
		int currProfit;
		
		for(Entry<String, Product> entry : map.entrySet()) {
			currProfit = ((Product)entry.getValue()).getProfit();
			sum += currProfit;
			str[i] = "Product: "+ entry.getKey() +" | Name: "+ entry.getValue().getName() +" | Profit: "+ currProfit;
			i++;
		}
		
		str[map.size()] = "Total Profit: "+ sum;
		
		return str;
	}
	
//	public boolean deleteProduct(String catalog) {
//		return this.store.getAllProducts().remove(catalog) != null;
//	}
}
