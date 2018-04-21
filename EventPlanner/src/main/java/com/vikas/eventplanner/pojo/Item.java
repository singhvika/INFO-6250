package com.vikas.eventplanner.pojo;

import javax.persistence.Embeddable;

@Embeddable
public class Item {

	  
	String name;
	double unitPrice;
	int requestedQuanntity;
	int fullFulledQuantity;
	User fullfilledByUser;
	Event participatingEvent;
	
	
	public User getFullfilledByUser() {
		return fullfilledByUser;
	}
	public void setFullfilledByUser(User fullfilledByUser) {
		this.fullfilledByUser = fullfilledByUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getRequestedQuanntity() {
		return requestedQuanntity;
	}
	public void setRequestedQuanntity(int requestedQuanntity) {
		this.requestedQuanntity = requestedQuanntity;
	}
	public int getFullFulledQuantity() {
		return fullFulledQuantity;
	}
	public void setFullFulledQuantity(int fullFulledQuantity) {
		this.fullFulledQuantity = fullFulledQuantity;
	}
	
	
	
	
}
