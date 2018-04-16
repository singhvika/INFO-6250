package com.vikas.eventplanner.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vikas.eventplanner.utils.RedirectionUtil;
import com.vikas.eventplanner.utils.SessionChecker;

@Controller
public class DashboardController {

	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView showDashboard(HttpServletRequest request, HttpServletResponse respnose)
	{
		if (!SessionChecker.checkForUserSession(request))
		{
			System.out.println("not logged in redirecting to login:");
			RedirectionUtil.redirectToLogin(request,respnose);
		}
		else
		{
			
			return new ModelAndView("dashboard");
			
		}
		return null;
	}
	
}
