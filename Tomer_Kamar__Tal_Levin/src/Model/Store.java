package Model;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import ModelCommands.ShowProductCommand;

public class Store {
	private TreeMap<String, Product> allProducts;
	private ShowProductCommand showProdCommand;
	
	public Store() {
		this.allProducts = new TreeMap<>(new ascendingComparator());
	}
	
	public TreeMap<String, Product> getAllProducts(){
		return this.allProducts;
	}
	
	public ShowProductCommand getShowProdCommand() {
		return this.showProdCommand = new ShowProductCommand(allProducts);
	}
	
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
