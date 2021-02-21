package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

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

	public void setMemento(StoreMemento memento) {
		this.memento = memento;
	}

	public void addProduct(String catalog, String pName, int storePrice, int custPrice, String custName, String phoneNum, boolean promotions) throws IOException {
		Product product = new Product(pName, storePrice, custPrice, new Customer(custName, phoneNum, promotions));

		this.memento = getStore().createMemento();

		getStore().getAllProducts().put(catalog, product);

		this.iterator.writeProducts(getStore().getAllProducts());
	}

	public boolean undoInsert() {
		try {
			if(this.memento != null && !getStore().getAllProducts().equals(this.memento.getMemento())) {
				getStore().setMemento(this.memento);
				this.iterator.writeProducts(getStore().getAllProducts());
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		if(res) {
			this.memento.getMemento().remove(catalog);
			this.memento.removeObserver(getStore().getAllProducts().get(catalog).getCustomer());
			//		getStore().getObservers().remove((Product)getStore().getAllProducts().get(catalog));
			updateMapFromFile();
		}		
		return res;
	}

	public void deleteAllProducts() throws FileNotFoundException, IOException, ClassNotFoundException {
		this.iterator.deleteAllContent();
		getStore().getObservers().clear();	
		this.memento.getMemento().clear();
		this.memento.getCustomersMemento().clear();
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
