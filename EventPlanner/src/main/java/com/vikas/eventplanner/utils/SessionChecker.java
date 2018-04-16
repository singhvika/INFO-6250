package com.vikas.eventplanner.utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionChecker {

	public static  Boolean checkForUserSession(HttpServletRequest request)
	{
		if(request.getSession(false)!=null)
		{
			System.out.println("session exists");
			
			if(request.getSession().getAttribute("user")!=null)
			{
				System.out.println("session exists for user");
				return true;
			}
			System.out.println("but no user in session");
			return false;
			
				
		}
		
		System.out.println(" no user in session");
		
		return false;
	}
	
	public static HttpSession getSessionForUser(HttpServletRequest request, String username)
	{
		if(checkForUserSession(request)==false)
		{
			HttpSession session = request.getSession();
			session.setAttribute("user", username);
			System.out.println("set session for : "+username);
			return session;
		}
		else
		{
			return request.getSession();
		}
	}
}
