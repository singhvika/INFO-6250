package com.vikas.eventplanner.pojo;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity(name = "Item")
@Table(name = "ITEM_TABLE")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@NotNull
	String name;
	
	double totalPrice;
	
	@Min(1)
	int requestedQuantity;
	
	int fullFulledQuantity;
	
	@OneToOne
	User fullfilledByUser;
	
	@ManyToOne
	Event participatingEvent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Event getParticipatingEvent() {
		return participatingEvent;
	}

	public void setParticipatingEvent(Event participatingEvent) {
		this.participatingEvent = participatingEvent;
	}

	

	public int getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(int requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public int getFullFulledQuantity() {
		return fullFulledQuantity;
	}

	public void setFullFulledQuantity(int fullFulledQuantity) {
		this.fullFulledQuantity = fullFulledQuantity;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", totalPrice=" + totalPrice + ", requestedQuantity="
				+ requestedQuantity + ", fullFulledQuantity=" + fullFulledQuantity + ", fullfilledByUser="
				+ fullfilledByUser + ", participatingEvent=" + participatingEvent + "]";
	}
	
	

}
