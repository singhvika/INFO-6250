package com.vikas.eventplanner.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "Event")
@Table(name = "EVENTS_TABLE")
public class Event {

	@NotEmpty
	@NotNull
	@Column
	String eventName;

	@NotNull
	@Future
	Date fromDate;

	@NotNull
	@Future
	Date toDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	User createdByUser;

	@Transient
	Collection<User> adminsUsers = new ArrayList<User>();

	@ManyToMany()
	List<User> participatingUsers = new ArrayList<User>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Embedded
	Collection<Item> itemList = new ArrayList<Item>();

	public Collection<User> getAdminsUsers() {
		return adminsUsers;
	}

	public void setAdminsUsers(Collection<User> adminsUsers) {
		this.adminsUsers = adminsUsers;
	}

	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<User> getParticipatingUsers() {
		return participatingUsers;
	}

	public void setParticipatingUsers(List<User> participatingUsers) {
		this.participatingUsers = participatingUsers;
	}

	public Collection<Item> getItemList() {
		return itemList;
	}

	public void setItemList(Collection<Item> itemList) {
		this.itemList = itemList;
	}

	public Event() {
		super();
	}

	@Override
	public String toString() {
		return "Event [eventName=" + eventName + ", fromDate=" + fromDate + ", toDate=" + toDate + ", id=" + id + "]";
	}

	public Boolean addUserToEvent(User participant) {
		Boolean userAlreadyExists = false;
		
		for (User user : getParticipatingUsers())
		{
			if (user.getEmail().equals(participant.getEmail()))
			{
				userAlreadyExists = true;
				System.out.println("duplicate user found");
				break;
			}
				
			
		}
		
		if (userAlreadyExists==false)
		{
			this.getParticipatingUsers().add(participant);
		}
		
		return userAlreadyExists;

		

	}

	public int checkAdminOrParticipant(User user) {
		if (this.getCreatedByUser().getId()==user.getId())
			return 1;
		
		for(User u : this.getParticipatingUsers())
		{
			if (u.getId()==user.getId())
				return 2;
				break;
		}
		
		return 0;
	}

	public int checkAdmin(User user) {
		System.out.println("checkin user is admin:");
		if (this.getCreatedByUser().getId()==user.getId()) {
			System.out.println("user is admin");
			return 1;
			
		} else {
			return 0;
		}
	}

}
