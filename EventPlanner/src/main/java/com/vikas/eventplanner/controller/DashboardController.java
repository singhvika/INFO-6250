package com.vikas.eventplanner.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import javax.xml.stream.util.EventReaderDelegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vikas.eventplanner.dao.EventDAO;
import com.vikas.eventplanner.dao.InviteDAO;
import com.vikas.eventplanner.dao.UserDAO;
import com.vikas.eventplanner.pojo.Event;
import com.vikas.eventplanner.pojo.Invite;
import com.vikas.eventplanner.pojo.User;
import com.vikas.eventplanner.utils.RedirectionUtil;
import com.vikas.eventplanner.utils.SessionChecker;

@Controller
public class DashboardController {

	@Autowired
	UserDAO userDao;

	@Autowired
	EventDAO eventDao;
	
	@Autowired
	InviteDAO inviteDao;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView showDashboard(HttpServletRequest request, HttpServletResponse respnose) {
		if (!SessionChecker.checkForUserSession(request)) {
			System.out.println("not logged in redirecting to login:");
			return RedirectionUtil.redirectToLogin(request, respnose);
			
		} else {

						
			User user = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", "vikas");
			

			map.put("eventList", user.getParticipatingEvents());
			System.out.println("events: "+map.get("eventList"));
			
			ModelAndView mv = new ModelAndView("dashboard", "map", map);
			
			return mv;

		}

	}

	@RequestMapping(value = "/dashboard/createEvent.htm", method = RequestMethod.GET)
	public ModelAndView createEventShow(HttpServletRequest request, HttpServletResponse response) {
		if (!SessionChecker.checkForUserSession(request)) {
			System.out.println("not logged in redirecting to login:");
			return RedirectionUtil.redirectToLogin(request, response);
		} else {
			request.setAttribute("event", new Event());
			return new ModelAndView("createEvent");
		}
	}

	@RequestMapping(value = "/dashboard/createEvent.htm", method = RequestMethod.POST)
	public ModelAndView createEventProcess(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("event") Event event, BindingResult bindingResult) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(request.getParameter("fromDate"));
		if (bindingResult.hasErrors()) {

			return new ModelAndView("createEvent");

		}

		System.out.println("Event Object is: " + event);
		String userEmail = (String) request.getSession().getAttribute("user");
		System.out.println("userEmail: " + userEmail);
		User user = userDao.getUserByEmail(userEmail);
		event.setCreatedByUser(user);
		if (user.addEvent(event)==false && event.addUserToEvent(user)==false) {
			System.out.println("UPDATED USER: "+user);
			System.out.println("UPDATED EVENT: "+event );
			System.out.println("user events and event user have been added in pojo");
			map.put("eventId", event.getId());
			map.put("eventName", event.getEventName());
			userDao.mergeUser(user);

			System.out.println("user: " + user);
			ModelAndView mv = new ModelAndView("eventAddSuccess");
			mv.addObject("map", map);
			return mv;

		} else {
			map.put("addError", "Event with same name already exists");
			ModelAndView mv = new ModelAndView("createEvent");
			mv.addObject("map", map);
			return mv;
		}

	}

	@RequestMapping(value = "/dashboard/event.htm", method = RequestMethod.GET)
	public ModelAndView manageEvent(HttpServletRequest request, HttpServletResponse response) {
		if (request.getSession().getAttribute("user") != null) {
			System.out.println("user is logged in");
			if (request.getParameter("id") != null) {
				String eventId = request.getParameter("id");
				System.out.println("event id present: "+eventId);
				User user = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
				

				Event event = eventDao.getEventById(eventId);
				System.out.println("event:"+event);
				int isUserAdminOrParticipant = event.checkAdmin(user);
				if (isUserAdminOrParticipant!=0) {
					System.out.println("user is admin or participant");
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("event", event);
					
					if (isUserAdminOrParticipant==1)
					{
						System.out.println("user is admin");
						map.put("isUserAdmin", "true");
					}
					else
					{
						System.out.println("user is participant");
					}
					
					request.setAttribute("newUserForEvent", new User());
					return new ModelAndView("eventDetails", "map", map);
					
				} else {
					// show error message here.. for now m redirect to dashboard
					return new ModelAndView("redirect:/dashboard.htm");
				}

			} else {
				return new ModelAndView("redirect:/dashboard.htm");
			}

		}

		else {
			return RedirectionUtil.redirectToLogin(request, response);
		}

	}

	@RequestMapping(value = "/dashboard/event/addUser.htm", method = RequestMethod.POST)
	public ModelAndView addUserToEvent(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("newUserForEvent") User newUserForEvent, BindingResult bindingResult, Invite invite,
			RedirectAttributes redirectAttributes) {
		System.out.println("attempting to send invite");
		if (request.getSession().getAttribute("user") != null) {
			
			if (bindingResult.hasErrors())
			{
				System.out.println("new user to event bind eror");
				System.out.println(bindingResult.getAllErrors());
				System.out.println("UserInviteError");
				return new ModelAndView("redirect:/dashboard/event.htm?id="+request.getParameter("eventId"));
			}
			System.out.println("no binding errors");
			User loggedInUser = userDao.getUserByEmail((String)request.getSession().getAttribute("user"));
			if (loggedInUser != null) {
				System.out.println("sending out invite link");
				invite.setInviteFromUser(loggedInUser);
				System.out.println("invite for:" +newUserForEvent.getEmail());
				User inviteForUser = userDao.getUserByUserObjectWithEmail(newUserForEvent);
				Event event = eventDao.getEventById((String)request.getParameter("eventId"));
				invite.setInviteForEvent(event);
				invite.setCreatedDate(new Date());;
				invite.setInviteForUser(inviteForUser);
				inviteDao.saveInvite(invite);
				System.out.println("invite sent");
				return new ModelAndView("redirect:/dashboard/event.htm?id="+request.getParameter("eventId"));
				 
			}
			else
			{
				return new ModelAndView("redirect:/dashboard/event.htm?id="+request.getParameter("eventId"));
			}

		} else {
			return RedirectionUtil.redirectToLogin(request, response);
		}

	}

}
