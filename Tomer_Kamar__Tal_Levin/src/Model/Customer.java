package Model;

import java.io.Serializable;

import Observer.CustomerReceiver;

public class Customer implements CustomerReceiver, Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String phoneNumber;
	private boolean isAcceptPromotions;
	private int byteSize;
	
	public Customer(String name, String phoneNumber, boolean isAcceptPromotions) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.isAcceptPromotions = isAcceptPromotions;
		this.byteSize = name.length() + phoneNumber.length() + 1;
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
	
	public int getSize() {
		return this.byteSize;
	}

	@Override
	public String toString() {
		return "Name: " + name + " | Phone number: " + phoneNumber;
	}

	@Override
	public String receivePromotion() {
		return "Customer name: "+ (this.name.isEmpty()? "No name" : this.name);
	}
	
	
}
