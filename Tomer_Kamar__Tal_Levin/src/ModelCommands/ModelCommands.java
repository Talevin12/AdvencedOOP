package ModelCommands;

import Model.Model;

public class ModelCommands implements Command {

	private Model model;
	
	public ModelCommands(Model model) {
		this.model = model;
	}
	
	@Override
	public void addProductCommand(String catalog, String pName, int storePrice, int custPrice, String custName, String phoneNum, boolean promotions) {
		model.addProduct(catalog, pName, storePrice, custPrice, custName, phoneNum, promotions);
	}

	@Override
	public boolean undo() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub

	}

}
