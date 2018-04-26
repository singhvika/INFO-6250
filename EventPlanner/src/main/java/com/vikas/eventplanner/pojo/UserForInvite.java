package com.vikas.eventplanner.pojo;

import org.hibernate.validator.constraints.Email;
import org.springframework.stereotype.Component;


@Component
public class UserForInvite {

	@Email
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
