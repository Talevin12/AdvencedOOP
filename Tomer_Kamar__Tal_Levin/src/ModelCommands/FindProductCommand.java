package ModelCommands;

import java.util.TreeMap;

import Model.Product;
import javafx.scene.text.Text;

public class FindProductCommand implements Command {
	private String catalog;
	private TreeMap<String, Product> allProducts;
	private Text foundProduct;
	
	public FindProductCommand(String catalog, TreeMap<String, Product> allProducts, Text foundProduct) {
		this.catalog = catalog;
		this.allProducts = allProducts;
		this.foundProduct = foundProduct;
	}
	
	@Override
	public void execute() {
		String strProduct = this.allProducts.get(this.catalog).toString();
		this.foundProduct.setText(strProduct);
	}

}
