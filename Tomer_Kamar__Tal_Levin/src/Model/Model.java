package Model;

import java.util.Map.Entry;
import java.util.TreeMap;
import ModelCommands.ModelCommands;

public class Model {
	private ModelCommands modelCommands;
	private StoreMemento memento;

	public Model() {
		this.modelCommands = new ModelCommands(this);
		this.memento = Store.getInstance().createMemento();
	}

	public ModelCommands getModelCommands() {
		return this.modelCommands;
	}

	public Store getStore() {
		return Store.getInstance();
	}

	public void addProduct(String catalog, String pName, int storePrice, int custPrice, String custName, String phoneNum, boolean promotions) {
		Product product = new Product(pName, storePrice, custPrice, new Customer(custName, phoneNum, promotions));

		this.memento = getStore().createMemento();

		getStore().getAllProducts().put(catalog, product);
	}

	public boolean undoInsert() {
		if(!getStore().getAllProducts().equals(this.memento.getMemento())) {
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

	public String searchProduct(String catalog) {
		Product product = getStore().getAllProducts().get(catalog);
		if(product == null)
			return "No such product was found :(";
		return product.toString();

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
