package ModelCommands;

import Model.Model;
import javafx.scene.control.ListView;

public class ModelCommands extends Thread implements Command {

	private Model model;
	private int currNameIndex;
	
	public ModelCommands(Model model) {
		this.model = model;
	}
	
	@Override
	public void addProductCommand(String catalog, String pName, int storePrice, int custPrice, String custName, String phoneNum, boolean promotions) {
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
	public String searchProduct(String catalog) {
		return this.model.searchProduct(catalog);
	}

	@Override
	public String[] showProfit() {
		return this.model.showProfit();
	}

//	@Override
//	public boolean deleteProduct(String catalog) {
//		return this.model.deleteProduct(catalog);
//	}

	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return false;
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
