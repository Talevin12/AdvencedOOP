package Model;

public class Product {
	private String name;
	private int storePrice;
	private int customerPrice;
	private Customer customer;
	
	public Product(String name, int storePrice, int customerPrice, Customer customer) {
		this.name = name;
		this.storePrice = storePrice;
		this.customerPrice = customerPrice;
		this.customer = customer;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getProfit() {
		return this.customerPrice-this.storePrice;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public String toString() {
		String str = "Product name: "+ this.name +" | Bought in: "+ this.storePrice +" | Sold for: "+ this.customerPrice +
					 " | To customer : "+ this.customer.toString() +"\n";
		
		return str;
	}
}
