package com.vikas.eventplanner.controller;

import java.io.IOException;
import java.security.KeyStore.Entry.Attribute;
import java.util.HashMap;
import java.util.Map;

import com.vikas.eventplanner.dao.UserDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.vikas.eventplanner.dao.*;
import com.vikas.eventplanner.pojo.User;
import com.vikas.eventplanner.services.AuthenticationService;
import com.vikas.eventplanner.utils.SessionChecker;



@Controller
public class AuthenticationController {

	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public ModelAndView showLoginPage(HttpServletRequest req, HttpServletResponse res, User user) {
		HttpSession session = req.getSession(false);
		if (SessionChecker.checkForUserSession(req) == false) {
			ModelAndView mv = new ModelAndView("login");
			req.setAttribute("user", user);
			return mv;
		} else {
			return new ModelAndView("redirect:/");
		}
	}

	@RequestMapping(value = "/login.htm", method = RequestMethod.POST)
	public ModelAndView performLogin(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute("user") @Validated User user, BindingResult bindingResult, AuthenticationService authenticationService, UserDAO userDAO) {
		
		System.out.println("now attempting login: ");
		if (SessionChecker.checkForUserSession(req) == false) {
			System.out.println("beginning authentication:");
			if(authenticationService.authenticate(user)==true)
			{
				System.out.println("authentication successfull");
				SessionChecker.getSessionForUser(req, user.getEmail());
				return new ModelAndView("redirect:/");
				
			}
			else
			{
				System.out.println("could noe authenticate");
				ModelAndView mv = new ModelAndView("login");
				Map<Object, Object> map = new HashMap<Object,Object>();
				map.put("loginError", "Invalid User Credentials, Please Try again");
				return mv;
			}
			
			
			
		}

		return new ModelAndView("redirect:/");

	}
	
	

	@RequestMapping(value = "/register.htm", method = RequestMethod.GET)
	public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (session.getAttribute("user") != null)
				return new ModelAndView("redirect:/dashboard");
		}
		ModelAndView mv = new ModelAndView("register");
		mv.addObject("user", new User());
		request.setAttribute("user", new User());

		return new ModelAndView("register");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView processRegister(RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("user") @Validated User user, BindingResult bindingResult,
			ModelMap map, UserDAO userDao) {
		if (bindingResult.hasErrors() ){
			return new ModelAndView("register");

			}
		
		
			user.setPwdHash(request.getParameter("pwd"));
			if (userDao.saveUser(user))
			{
				System.out.println("SUCCSS REGISTER");
				return new ModelAndView("redirect:/");
			}
			
		
			ModelAndView mv = new ModelAndView("register");
			mv.addObject("registerError","Duplicates");
			return mv;
		
			
		

	//	String pwdHash = BCrypt.hashpw(request.getParameter("pwd"), BCrypt.gensalt());
		
		

		
	}
	
	@RequestMapping(value="/logout.htm")
	public ModelAndView logout(HttpServletRequest request)
	{
		request.getSession().invalidate();
		return new ModelAndView("redirect:/");
	}

}
