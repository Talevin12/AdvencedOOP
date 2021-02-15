package ModelCommands;
import java.util.Map;
import java.util.TreeMap;

import Model.*;
import javafx.scene.control.ListView;

public class ShowProductCommand implements Command {
	private TreeMap<String, Product> allProducts;
	private ListView<String> productsListView;
	
	public ShowProductCommand(TreeMap<String, Product> allProducts) {
		this.allProducts = allProducts;
	}
	
	public void setListView(ListView<String> listView) {
		this.productsListView = listView;
	}
	
	@Override
	public void execute() {
		for (Map.Entry<String, Product> entry : allProducts.entrySet()) {
			String value = ((Product)entry.getValue()).toString();
			this.productsListView.getItems().add(value);
		}
	}

}
