package com.vikas.eventplanner.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RedirectionUtil {
	
	public static ModelAndView redirectToLogin(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("redirect:/login.htm");

	}

}
