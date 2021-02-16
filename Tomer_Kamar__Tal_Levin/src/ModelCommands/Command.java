package ModelCommands;

public interface Command {
	
	void addProductCommand(String catalog, String pName, int storePrice, int custPrice, String custName, String phoneNum, boolean promotions);
	
	boolean undo();
	
	String[] showAllProducts();
	
	String searchProduct(String catalog);
	
	String[] showProfit();
	
//	boolean deleteProduct(String catalog);
	
	boolean deleteAll();
	
	void sendPromotion();
}
