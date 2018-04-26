package com.vikas.eventplanner.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.vikas.eventplanner.dao.EventDAO;
import com.vikas.eventplanner.dao.ItemDAO;
import com.vikas.eventplanner.dao.UserDAO;

import com.vikas.eventplanner.pojo.Event;
import com.vikas.eventplanner.pojo.User;
import com.vikas.eventplanner.utils.RedirectionUtil;
import com.vikas.eventplanner.utils.SessionChecker;

@Component
@Controller
public class PDFController {

	@Autowired
	UserDAO udao;

	@Autowired
	EventDAO eventDao;

	@Autowired
	ItemDAO itemDao;

	@RequestMapping(value = "/dashboard/event/downloadPDF", method = RequestMethod.POST)
	public ModelAndView downloadPDF(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("eventId") String eventId, ModelMap map) {

		// create some sample data
		if (SessionChecker.checkForUserSession(request)) {
			User loggedInUser = udao.getUserByEmail((String) request.getSession().getAttribute("user"));
			Event event = eventDao.getEventById(eventId);
			if (event.checkAdminOrParticipant(loggedInUser) != 0) {
				map.put("event", event);
				map.put("user", loggedInUser);
				map.addAttribute("userDao", udao);
				map.addAttribute("itemDao", itemDao);
				return new ModelAndView("pdfView", "map", map);

			}
			map.addAttribute("error", "Unauthorized");
			map.addAttribute("redirec", "/dashboard.htm");
			return new ModelAndView("eventError", "map", map);
		}
		return RedirectionUtil.redirectToLogin(request, response);

	}

	@RequestMapping(value = "/dashboard/event/downloadPDF")
	public ModelAndView showEventSummaryPDF(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("eventId") String eventId, ModelMap map) {
		if (SessionChecker.checkForUserSession(request)) {
			User loggedInUser = udao.getUserByEmail((String) request.getSession().getAttribute("user"));
			Event event = eventDao.getEventById(eventId);
			if (event.checkAdminOrParticipant(loggedInUser) != 0) {
				map.put("event", event);
				map.put("user", loggedInUser);
				return new ModelAndView("pdfView", "map", map);

			}
			map.addAttribute("error", "Unauthorized");
			map.addAttribute("redirec", "/dashboard.htm");
			return new ModelAndView("eventError", "map", map);
		}
		return RedirectionUtil.redirectToLogin(request, response);
	}
}
