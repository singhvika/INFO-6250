package com.vikas.eventplanner.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Entity(name = "Invite")
@Table(name = "INVITE_TABLE")
public class Invite {

	@OneToOne
	@NotNull
	User inviteFromUser;

	@OneToOne
	@NotNull
	User inviteForUser;

	@Column(name = "inviteStatus")
	Boolean inviteStatus = true;

	
	@NotNull
	@OneToOne
	Event inviteForEvent;

	@Column(name = "createdDate")
	Date createdDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	int ttl = 86400;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getInviteFromUser() {
		return inviteFromUser;
	}

	public void setInviteFromUser(User inviteFromUser) {
		this.inviteFromUser = inviteFromUser;
	}

	public User getInviteForUser() {
		return inviteForUser;
	}

	public void setInviteForUser(User inviteForUser) {
		this.inviteForUser = inviteForUser;
	}

	public Boolean getInviteStatus() {
		return inviteStatus;
	}

	public void setInviteStatus(Boolean inviteStatus) {
		this.inviteStatus = inviteStatus;
	}

	public Event getInviteForEvent() {
		return inviteForEvent;
	}

	public void setInviteForEvent(Event inviteForEvent) {
		this.inviteForEvent = inviteForEvent;
	}

}
