package Model;

import java.io.Serializable;

public class Product implements Serializable{
	static final int INT_SIZE = 4;

	private static final long serialVersionUID = 1L;
	private String name;
	private int storePrice;
	private int customerPrice;
	private Customer customer;
	private int byteSize;
	
	public Product(String name, int storePrice, int customerPrice, Customer customer) {
		this.name = name;
		this.storePrice = storePrice;
		this.customerPrice = customerPrice;
		this.customer = customer;
		this.byteSize = name.length() + INT_SIZE*2 + customer.getSize();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getStorePrice() {
		return this.customerPrice;
	}
	
	public int getCustomerPrice() {
		return this.storePrice;
	}
	
	public int getProfit() {
		return this.customerPrice-this.storePrice;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public int getSize() {
		return this.byteSize;
	}
	
	@Override
	public String toString() {
		String str = "Product name: "+ this.name +" | Bought in: "+ this.storePrice +" | Sold for: "+ this.customerPrice +
					 " | To customer : "+ this.customer.toString() +"\n";
		
		return str;
	}
}
