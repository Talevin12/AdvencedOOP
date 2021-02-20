package ModelCommands;

import java.io.FileNotFoundException;
import java.io.IOException;

import Model.Model;
import Model.Product;
import javafx.scene.control.ListView;

public class ModelCommands extends Thread implements Command {

	private Model model;
	private int currNameIndex;
	
	public ModelCommands(Model model) {
		this.model = model;
	}
	
	@Override
	public void addProductCommand(String catalog, String pName, int storePrice, int custPrice, String custName, String phoneNum, boolean promotions) throws FileNotFoundException, IOException {
		model.addProduct(catalog, pName, storePrice, custPrice, custName, phoneNum, promotions);
	}

	@Override
	public boolean undoInsert() {
		return model.undoInsert();
	}

	@Override
	public String[] showAllProducts() {
		return model.showAllProducts();
	}

	@Override
	public Product searchProduct(String catalog) {
		return this.model.searchProduct(catalog);
	}

	@Override
	public String[] showProfit() {
		return this.model.showProfit();
	}

	@Override
	public boolean deleteProduct(String catalog) {
		boolean res = false;
		try {
			res = this.model.deleteProduct(catalog);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return res;
	}

	@Override
	public void deleteAll() {
		try {
			this.model.deleteAllProducts();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void sendPromotion() {
		this.model.sendPromotions();

	}
	
	@Override
	public String showAcceptedCustomer() {
		String[] names = this.model.getAcceptedPrompotionCustomers();
		if(this.currNameIndex == names.length) {
			this.currNameIndex = 0;
			return null;
		}
//		run();
		return names[this.currNameIndex++];
	}

//	@Override
//	public void run() {
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
}
