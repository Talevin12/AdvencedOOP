package Model;

public class Customer {
	private String name;
	private String phoneNumber;
	private boolean isAcceptPromotions;
	
	public Customer(String name, String phoneNumber, boolean isAcceptPromotions) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.isAcceptPromotions = isAcceptPromotions;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public boolean isAcceptPromotions() {
		return isAcceptPromotions;
	}

	@Override
	public String toString() {
		return "Name: " + name + " | Phone number: " + phoneNumber;
	}
	
	
}
