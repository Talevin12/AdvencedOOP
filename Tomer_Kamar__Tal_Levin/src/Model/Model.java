package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.text.html.HTMLDocument.Iterator;

import Model.FileIterator.ascendingComparator;
import Model.FileIterator.descendingComparator;
import ModelCommands.ModelCommands;

public class Model {
	private ModelCommands modelCommands;
	private StoreMemento memento;
//	private File productsFile;
	private FileIterator iterator;

	public Model() throws FileNotFoundException, IOException {
		this.modelCommands = new ModelCommands(this);
//		this.memento = Store.getInstance().createMemento();
//		this.productsFile = new File("products.txt");
		this.iterator = new FileIterator();
	}

	public ModelCommands getModelCommands() {
		return this.modelCommands;
	}

	public Store getStore() {
		return Store.getInstance();
	}
	
	public FileIterator getIterator() {
		return this.iterator;
	}
	
//	public void writeSortOption() throws FileNotFoundException, IOException {
//		Comparator<String> comparator = (Comparator<String>) getStore().getAllProducts().comparator();
//		
//		if(comparator.getClass() == ascendingComparator.class)
//			this.iterator.writeComparator(1);
//		else if(comparator.getClass() == descendingComparator.class)
//			this.iterator.writeComparator(2);
//		else
//			this.iterator.writeComparator(3);
//	}

	public void addProduct(String catalog, String pName, int storePrice, int custPrice, String custName, String phoneNum, boolean promotions) throws IOException {
		Product product = new Product(pName, storePrice, custPrice, new Customer(custName, phoneNum, promotions));

		this.memento = getStore().createMemento();

		getStore().getAllProducts().put(catalog, product);
		
		this.iterator.writeProducts(getStore().getAllProducts());
	}
	
	public boolean undoInsert() {
		if(this.memento != null && !getStore().getAllProducts().equals(this.memento.getMemento())) {
			getStore().setMemento(this.memento);
			return true;
		}
		else 
			return false;
	}

	public String[] showAllProducts() {
		TreeMap<String, Product> map = getStore().getAllProducts();
		String[] str = new String[map.size()];
		int i = 0;
		for(Entry<String, Product> entry : map.entrySet()) {
			str[i] = "Catalog number: "+ entry.getKey() +" | "+ entry.getValue().toString();
			i++;
		}

		return str;
	}

	public Product searchProduct(String catalog) {
		Product product = getStore().getAllProducts().get(catalog);
		if(product == null)
			return null;
		return product;

	}
	
	public boolean deleteProduct(String catalog) throws FileNotFoundException, IOException, ClassNotFoundException {
		boolean res = this.iterator.deleteProduct(catalog);
		updateMapFromFile();
		return res;
	}
	
	public void deleteAllProducts() throws FileNotFoundException, IOException, ClassNotFoundException {
		this.iterator.deleteAllContent();
		updateMapFromFile();
	}
	
	public boolean updateMapFromFile() throws ClassNotFoundException, IOException {
		TreeMap<String, Product> temp = iterator.readAllProducts();
		
		if(temp == null)
			return false;
		getStore().setAllProducts(temp);
		return true;
	}

	public String[] showProfit() {
		TreeMap<String, Product> map = getStore().getAllProducts();
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

	public void sendPromotions() {
		getStore().sendPromotions();
	}

	public String[] getAcceptedPrompotionCustomers() {
		String[] custStr = {"No customer accepted the promotion or no promotion was sent"};
		if(getStore().getObservers().size() != 0) {
			custStr = new String[getStore().getObservers().size()];
			int i = 0;
			for(Entry<Customer, String> entry : getStore().getObservers().entrySet()) {
				custStr[i] = (String)entry.getValue();
				i++;
			}
		}

		return custStr;
	}
}
