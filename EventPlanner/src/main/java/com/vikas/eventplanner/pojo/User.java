package com.vikas.eventplanner.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MXBean;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "User")
@Table(name = "USER_TABLE")
public class User {

	@Column(name = "pwdHash", unique = true, nullable = false)
	String pwdHash;

	@Column(name = "email", unique = true, nullable = false)
	@NotEmpty
	@Email
	String email;
	
	@Column(name="firstName",nullable=false)
	@NotEmpty
	String firstName;
	
	@Column(name="lastName", nullable=true)
	String lastName;

	@Transient
	String pwd;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="participatingUsers")	
	Collection<Event> participatingEvents = new ArrayList<Event>();

	public User() {
		super();
		
	}

	public String getPwdHash() {
		return pwdHash;
	}

	public void setPwdHash(String pwdHash) {
		this.pwdHash = pwdHash;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
	

	public Collection<Event> getParticipatingEvents() {
		return participatingEvents;
	}

	public void setParticipatingEvents(Collection<Event> participatingEvents) {
		this.participatingEvents = participatingEvents;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public boolean addEvent(Event event) {
		boolean duplicateEvent = false;
		System.out.println("Checking user Events of size: " + this.getParticipatingEvents().size());
		
		for (Event even : this.getParticipatingEvents())
		{
			if (even.getEventName().equals(event.getEventName()))
			{
				duplicateEvent = true;
				
				break;
			}
		}
		
		if (duplicateEvent==false)
		{
			this.getParticipatingEvents().add(event);
		}
		return duplicateEvent;
	}

	@Override
	public String toString() {
		return "User [pwdHash=" + pwdHash + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", pwd=" + pwd + ", id=" + id + ", participatingEvents=" + participatingEvents + "]";
	}

	

}
