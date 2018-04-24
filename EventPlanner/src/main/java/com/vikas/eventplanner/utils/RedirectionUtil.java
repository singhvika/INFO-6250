package com.vikas.eventplanner.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RedirectionUtil {

	public static ModelAndView redirectToLogin(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("redirect:/login.htm");

	}

	public static ModelAndView redirectToDashboard(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("redirect:/dashboard.htm");
	}

	public static String getCurrentUrl(HttpServletRequest request) {
		String currentUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
		return currentUrl;
	}

	public ModelAndView redirectToNext(HttpServletRequest request, HttpServletResponse response, String redirectUrl) {
		return new ModelAndView("redirect:" + redirectUrl);
	}

}
