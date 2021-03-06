package com.vikas.eventplanner.services;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vikas.eventplanner.dao.UserDAO;
import com.vikas.eventplanner.pojo.LoginUser;
import com.vikas.eventplanner.pojo.User;

public class AuthenticationService {

	
	
	

	public AuthenticationService() {
		
	}



	public Boolean authenticate(LoginUser incomingUser) {
		
		
		

		User user = new UserDAO().getUserByEmail(incomingUser.getEmail());
		System.out.println("FOUND USER in authenticate:" +user);
		if (user == null) {
			return false;
		} else {
			//Boolean pwdCheck = BCrypt.checkpw(incomingUser.getPwd(), user.getPwdHash());
			System.out.println("user was found: "+user.getEmail());
			Boolean pwdCheck = false;
			System.out.println("now matching");
			System.out.println("INCOMING PWD: "+incomingUser.getPwd());
			System.out.println("DB PWD: "+user.getPwdHash());
			
			
			
			if (BCrypt.checkpw(incomingUser.getPwd(), user.getPwdHash()))
			{
				System.out.println("user matched");
				pwdCheck=true;
			}
			System.out.println("PWDCHECK: "+pwdCheck);
			

			return pwdCheck;

		}

	}
	

}
