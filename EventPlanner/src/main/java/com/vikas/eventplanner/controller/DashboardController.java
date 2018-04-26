package com.vikas.eventplanner.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import javax.xml.stream.util.EventReaderDelegate;

import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vikas.eventplanner.dao.EventDAO;
import com.vikas.eventplanner.dao.InviteDAO;
import com.vikas.eventplanner.dao.ItemDAO;
import com.vikas.eventplanner.dao.UserDAO;
import com.vikas.eventplanner.pojo.Event;
import com.vikas.eventplanner.pojo.Invite;
import com.vikas.eventplanner.pojo.Item;
import com.vikas.eventplanner.pojo.User;
import com.vikas.eventplanner.utils.EmailUtil;
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

	@Autowired
	ItemDAO itemDao;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/dashboard.htm", method = RequestMethod.GET)
	public ModelAndView showDashboard(HttpServletRequest request, HttpServletResponse respnose, ModelMap map) {

		System.out.println("moving to user dashboard");
		if (!SessionChecker.checkForUserSession(request)) {
			System.out.println("not logged in redirecting to login:");

			return RedirectionUtil.redirectToLogin(request, respnose);

		} else {

			User user = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));

			map.addAttribute("totalPages", user.getTotalEventPagesByUser());
			if (request.getParameter("page") != null) {
				int page = 1;
				int newPage = 1;
				try {
					newPage = Integer.parseInt(request.getParameter("page"));
				} catch (Exception ex) {
					ex.printStackTrace();
					newPage = 1;
				}
				if (newPage <= user.getTotalEventPagesByUser()) {
					page = newPage;
					List<Event> eventList = eventDao.getUserEventPageByUser(user, page);
					System.out.println("retrieved list size: " + eventList.size());
					map.put("eventList", eventList);
				}

				else {

					return new ModelAndView("redirect:/dashboard.htm?page=" + page);
				}
			} else {
				map.put("eventList", eventDao.getUserEventPageByUser(user, 1));
			}

			System.out.println("eventlistsize in map: " + ((List<Event>) map.get("eventList")).size());
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
		if (user.addEvent(event) == false && event.addUserToEvent(user) == false) {
			System.out.println("UPDATED USER: " + user);
			System.out.println("UPDATED EVENT: " + event);
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

	@RequestMapping(value = "/dashboard/event/packup.htm", method = RequestMethod.POST)
	public ModelAndView closeEvent(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("eventId") String eventId, @RequestParam("action") String action, ModelMap map) {
		if (SessionChecker.checkForUserSession(request)) {
			Event event = eventDao.getEventById(eventId);
			User loggedInUser = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
			if (event.checkAdmin(loggedInUser) == 1) {
				System.out.println("EVENT STATUS: " + event.getActive());
				if (action.equalsIgnoreCase("deactivate")) {
					if (event.getActive() == true) {
						event.setActive(false);
						System.out.println("event has been disabled: " + event.getActive());
						eventDao.mergeEvent(event);
						return new ModelAndView("redirect:/dashboard/event.htm?id=" + eventId);
					} else {
						map.addAttribute("error", "The event is already inactive");
						map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
						return new ModelAndView("eventError", "map", map);
					}
				}
				if (action.equalsIgnoreCase("activate")) {
					if (event.getActive() == false) {
						event.setActive(true);
						System.out.println("event has been enabled: " + event.getActive());
						eventDao.mergeEvent(event);
						return new ModelAndView("redirect:/dashboard/event.htm?id=" + eventId);
					} else {
						map.addAttribute("error", "The event is already active");
						map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
						return new ModelAndView("eventError", "map", map);
					}
				}
			}

		}

		return new RedirectionUtil().redirectToDashboard(request, response);
	}

	@RequestMapping(value = "/dashboard/event.htm", method = RequestMethod.GET)
	public ModelAndView manageEvent(HttpServletRequest request, HttpServletResponse response, Item item, ModelMap map) {
		if (request.getSession().getAttribute("user") != null) {
			System.out.println("user is logged in");
			if (request.getParameter("id") != null) {
				String eventId = request.getParameter("id");
				System.out.println("event id present: " + eventId);
				User user = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
				System.out.println("USERID:" + user.getId());
				Event event = eventDao.getEventById(eventId);
				System.out.println("eventId:" + event.getId());
				int isUserAdminOrParticipant = event.checkAdminOrParticipant(user);
				System.out.println("adminOrParticipant: " + isUserAdminOrParticipant);
				;
				if (isUserAdminOrParticipant != 0) {
					System.out.println("user is admin or participant");

					map.put("event", event);

					if (isUserAdminOrParticipant == 1) {
						System.out.println("user is admin");
						map.put("isUserAdmin", "true");
					} else {
						System.out.println("user is participant");
					}

					request.setAttribute("newUserForEvent", new User());
					request.setAttribute("eventItem", item);
					return new ModelAndView("eventDetails", "map", map);

				} else {
					// show error message here.. for now m redirect to dashboard
					map.addAttribute("error", "unauthorised");
					map.addAttribute("redirect", "/dashboard.htm");
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

	@RequestMapping(value = "/dashboard/event/addUser.htm", method = RequestMethod.GET)
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, User user, ModelMap map,
			@RequestParam("eventId") String eventId) {
		if (SessionChecker.checkForUserSession(request)) {
			User loggedInUser = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
			System.out.println("loggedInUser: " + loggedInUser.getEmail());
			Event event = eventDao.getEventById(eventId);
			int isUserAdminOrParticipant = event.checkAdminOrParticipant(loggedInUser);
			System.out.println("user admin or participant: " + isUserAdminOrParticipant);
			if (isUserAdminOrParticipant != 1) {
				System.out.println("not authorised");
				map.addAttribute("error", "not Authorised");
				map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
				return new ModelAndView("addUserError", "map", map);
			}
			if (event.getActive() != true) {
				map.addAttribute("error", "event you are trying to modify is inactive");
				map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
				return new ModelAndView("eventError", "map", map);
			}
			request.setAttribute("newUserForEvent", new User());
			map.addAttribute("newUserForEvent", new User());
			map.addAttribute("event", event);
			map.addAttribute("isUserAdmin", isUserAdminOrParticipant);
			return new ModelAndView("addUser", "map", map);
		}
		return RedirectionUtil.redirectToDashboard(request, response);
	}

	@RequestMapping(value = "/dashboard/event/addUser.htm", method = RequestMethod.POST)
	public ModelAndView addUserToEvent(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("newUserForEvent") User newUserForEvent, BindingResult bindingResult, Invite invite,
			RedirectAttributes redirectAttributes, ModelMap map, @RequestParam("eventId") String eventId) {
		System.out.println("attempting to send invite");
		System.out.println("for email: " + newUserForEvent.getEmail());

		if (bindingResult.hasErrors()) {
			System.out.println("new user to event bind eror");
			System.out.println(bindingResult.getAllErrors());
			System.out.println("UserInviteError");
			return new ModelAndView("addUser", "map", map);
		}

		if (request.getSession().getAttribute("user") != null) {

			System.out.println("no binding errors");
			Event event = eventDao.getEventById((String) request.getParameter("eventId"));

			if (event.getActive() == true) {

				User loggedInUser = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
				if (loggedInUser != null) {
					System.out.println("sending out invite link");
					invite.setInviteFromUser(loggedInUser);
					System.out.println("invite for:" + newUserForEvent.getEmail());
					User inviteForUser = userDao.getUserByUserObjectWithEmail(newUserForEvent);
					if (inviteForUser != null) {
						System.out.println("checking for user in event");
						if (event.checkAdminOrParticipant(inviteForUser) != 0) {

							map.addAttribute("error", "user already in event");
							map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
							return new ModelAndView("eventError", "map", map);
						}
						invite.setInviteForEvent(event);
						invite.setCreatedDate(new Date());
						invite.setInviteForUser(inviteForUser);
						invite.setActive(Boolean.TRUE);
						inviteDao.invalidateStaleEvents(event.getId(), inviteForUser.getEmail());

						String uniqueID = UUID.randomUUID().toString();
						invite.setUniqueId(uniqueID);
						inviteDao.saveInvite(invite);

						String inviteURLAccept = "http://localhost:8080/eventplanner/dashboard/user/invite.htm?uid="
								+ uniqueID;
						System.out.println("INVITE URL: " + inviteURLAccept);
						String emailBody = "Hello, " + inviteForUser.getFirstName()
								+ "\n I'd like to invite you to the Event I am Hosting: \n" + event.getEventName()
								+ "\n" + "Click the Below Link to join \n" + inviteURLAccept;
						EmailUtil.sendEmail(loggedInUser.getEmail(), inviteForUser.getEmail(), emailBody,
								"Event invite from " + loggedInUser);
						System.out.println("invite sent");
						map.addAttribute("success", "invite has been sent ");
						map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
						return new ModelAndView("addUserSuccess", "map", map);
					}
					System.out.println("invite could not be sent as user does not exists");
					map.addAttribute("success", "Make sure the user is on this platform to receive the invite");
					map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
					return new ModelAndView("addUserSuccess", "map", map);

				} else {
					return new ModelAndView("redirect:/dashboard/event.htm?id=" + request.getParameter("eventId"));
				}
			} else {
				map.addAttribute("error", "The Event You are trying to modify is inactive. Activate it first");
				map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
				return new ModelAndView("eventError", "map", map);
			}

		} else {
			return RedirectionUtil.redirectToLogin(request, response);
		}

	}

	@RequestMapping(value = "/dashboard/event/addItem.htm", method = RequestMethod.POST)
	public ModelAndView addItem(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("eventItem") @Validated Item eventItem, BindingResult bindingResult,
			@RequestParam("eventId") String eventId, ModelMap map) {
		System.out.println("adding Item: ");
		if (SessionChecker.checkForUserSession(request) == true) {
			Event event = eventDao.getEventById(eventId);
			User loggedInUser = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
			if (event.getCreatedByUser().getEmail().equals(loggedInUser.getEmail())) {
				if (event.getActive() == true) {
					System.out.println("event is active... hence can add Item");
					if (bindingResult.hasErrors()) {
						System.out.println("error binding item object");
						map.addAttribute("error", "requested quantity cannot be 0");
						map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
						return new ModelAndView("eventError", "map", map);
					}
					if (event.addItem(eventItem) == true) {
						System.out.println("item does not exist in event hence addin");
						System.out.println("itemname:" + eventItem.getName());
						System.out.println("itemQ: " + eventItem.getRequestedQuantity());
						System.out.println("event Items: " + event.getItemList());
						eventItem.setParticipatingEvent(event);
						eventDao.mergeEvent(event);
						return new ModelAndView("redirect:/dashboard/event.htm?id=" + eventId);
					} else {
						map.addAttribute("itemError", "item with same name already exists");
						map.addAttribute("redirect", "/dashboard.htm");
						return new ModelAndView("itemError", "map", map);
					}
				} else {
					map.addAttribute("error", "the event you are trying to modify is not active, Activate it first");
					map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
					return new ModelAndView("eventError", "map", map);
				}
			}
		}
		return new ModelAndView("redirect:/dashboard.htm");
	}

	@RequestMapping(value = "/dashboard/event/item/deleteitem.htm", method = RequestMethod.POST)
	public ModelAndView deleteItem(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("itemId") String itemId, @RequestParam("eventId") String eventId, ModelMap map) {

		if (SessionChecker.checkForUserSession(request) == true) {
			User loggedInUser = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
			Event event = eventDao.getEventById(eventId);

			if (event.checkAdmin(loggedInUser) == 1) {
				if (event.getActive() == true) {
					itemDao.deleteItem(itemId);
					return new ModelAndView("redirect:/dashboard/event.htm?id=" + eventId);
				}
				map.addAttribute("error", "event is inactive");
				map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
				return new ModelAndView("eventError", "map", map);

			}
			map.addAttribute("error", "Unauthorised");
			map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
			return new ModelAndView("eventError", "map", map);

		}

		return new ModelAndView("redirect:/login.htm");
	}

	@RequestMapping(value="/dashboard/user/invite.htm" , method = RequestMethod.GET)
	public ModelAndView showInvite(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uid") String uid, ModelMap map) {
		if (SessionChecker.checkForUserSession(request) == false) {
			return RedirectionUtil.redirectToLogin(request, response);
		}

		if (uid.trim().length() != 0) {

			System.out.println("invite is proper");
			Invite invite = inviteDao.getInvite(uid);
			User user = userDao.getUserById(Long.toString(invite.getInviteForUser().getId()));
			if (request.getSession().getAttribute("user").equals(user.getEmail())) {
				map.addAttribute("invite", invite);
				return new ModelAndView("invite", "map", map);
			} else {
				map.addAttribute("error","invalid invite");
				map.addAttribute("redirect","/dashboard.htm");
				return new ModelAndView("inviteError");
			}
		}
		return new ModelAndView("redirect:/dashboard.htm");

	}

	@RequestMapping(value = "/dashboard/user/acceptInvite.htm", method = RequestMethod.GET)
	public ModelAndView acceptInvitation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uid") String uid, @RequestParam("answer") String answer, ModelMap map) {

		if (SessionChecker.checkForUserSession(request)) {
			if (uid.trim().length() == 0 || answer.trim().length() == 0)
				return RedirectionUtil.redirectToLogin(request, response);
			else {

				Invite invite = inviteDao.getInvite(uid);
				User forUserTest = userDao.getUserById(Long.toString(invite.getInviteForUser().getId()));
				if (invite.getActive() == Boolean.TRUE
						&& forUserTest.getEmail().equals(request.getSession().getAttribute("user"))) {
					invite.setActive(Boolean.FALSE);
					if (answer.equalsIgnoreCase("yes")) {

						Event event = eventDao.getEventById((Long.toString(invite.getInviteForEvent().getId())));
						if (event.getActive() == true) {
							User user = (User) userDao.getUserById(Long.toString(invite.getInviteForUser().getId()));
							event.addUserToEvent(user);
							user.addEvent(event);
							System.out.println("user events: " + user.getParticipatingEvents());
							userDao.mergeUser(user);
							inviteDao.mergeInvite(invite);
							System.out.println("invite has been accepted ");
							return new ModelAndView("redirect:/dashboard.htm");
						} else {
							map.addAttribute("error",
									"the event you tried to Join has inactive, ask the admin to activate it");
							map.addAttribute("redirect", "/dashboard.htm");
							return new ModelAndView("eventError", "map", map);
						}
					} else {
						invite.setActive(Boolean.FALSE);
						invite.setInviteStatus(Boolean.FALSE);
						System.out.println("invite has been rejected");
						return new ModelAndView("redirect:/dashboard.htm");
					}
				} else {
					System.out.println("invalid invite");
					map.addAttribute("error", "Invalid invite");
					map.addAttribute("redirect", "/dashboard.htm");
					return new ModelAndView("eventError", "map", map);
				}

			}
		} else {
			return RedirectionUtil.redirectToLogin(request, response);

		}

	}

	@RequestMapping(value = "/dashboard/event/item/claimItem.htm", method = RequestMethod.GET)
	public ModelAndView claimItem(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("eventId") String eventId, @RequestParam("itemId") String itemId, ModelMap map) {
		if (SessionChecker.checkForUserSession(request)) {
			Event event = eventDao.getEventById(eventId);
			Item item = itemDao.getItemById(itemId);
			User loggedInUser = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
			int isAdminOrParticipant = event.checkAdminOrParticipant(loggedInUser);
			if (isAdminOrParticipant != 0) {
				if (event.getActive() == true) {
					if (item.getFullfilledByUser() == null) {
						map.addAttribute("event", event);
						System.out.println("66666666666itemID: " + item.getId());
						request.setAttribute("eventItem", item);
						return new ModelAndView("claimItem", "map", map);
					}
					map.addAttribute("error", "Item AlreadyClaimed");
					map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
					return new ModelAndView("itemError", "map", map);
				}
				map.addAttribute("error", "Event is inactive");
				map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
				return new ModelAndView("eventError", "map", map);

			}
			map.addAttribute("error", "unauthorized");
			map.addAttribute("redirect", "/dashboard.htm");
			return new ModelAndView("eventError", "map", map);
		}
		return RedirectionUtil.redirectToLogin(request, response);
	}

	@RequestMapping(value = "/dashboard/event/item/claimItem.htm", method = RequestMethod.POST)
	public ModelAndView claimItem(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("eventItem") Item claimItem, BindingResult bindingResult,
			@RequestParam("eventId") String eventId, @RequestParam("itemId") String itemId, ModelMap map) {
		if (SessionChecker.checkForUserSession(request) == true) {
			System.out.println("7777777777 ITEM ID: " + itemId);
			if (bindingResult.hasErrors()) {
				return new ModelAndView("claimItem");
			}
			User loggedInUser = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
			System.out.println("eventid:" + eventId);
			System.out.println("itemId: " + itemId);
			Event event = eventDao.getEventById(eventId);
			if (event.getActive() == true) {

				if (event.checkAdminOrParticipant(loggedInUser) != 0) {
					if (event.checkAdminOrParticipant(loggedInUser) != 0) {
						Item item = itemDao.getItemById(itemId);
						System.out.println("ITEM: " + item.getName());
						item.setFullfilledByUser(loggedInUser);
						item.setFullFulledQuantity(claimItem.getFullFulledQuantity());
						item.setTotalPrice(claimItem.getTotalPrice());
						itemDao.mergeItem(item);
						return new ModelAndView("redirect:/dashboard/event.htm?id=" + eventId);
					} else {
						return RedirectionUtil.redirectToDashboard(request, response);
					}
				}
				map.addAttribute("error", "unauthorized");
				map.addAttribute("redirect", "/dashboard.htm");
				return new ModelAndView();
			} else {
				System.out.println("EVENT INACTIVE >> CANNOT CLAIM");
				map.addAttribute("error", "the event you are trying to participate in is inactive");
				map.addAttribute("redirect", "/dashboard/event/htm?id=" + eventId);
				return new ModelAndView("eventError", "map", map);
			}

		} else {
			return RedirectionUtil.redirectToDashboard(request, response);
		}
	}

	@RequestMapping(value = "/hidden/event.htm")
	public ModelAndView eventShow(HttpServletRequest request) {
		String id = request.getParameter("id").trim();
		System.out.println("######## Hidden: id: " + id);
		Event event = eventDao.getEventById(id.toString());
		System.out.println("########## hidden show");
		System.out.println(event.getParticipatingUsers());
		return null;
	}

	@RequestMapping(value = "/hidden/user.htm")
	public ModelAndView userShow(HttpServletRequest request) {
		String id = request.getParameter("id");
		User user = userDao.getUserById(id);
		System.out.println("##### HIDDEN SHOW");
		System.out.println(user.getParticipatingEvents());
		return null;
	}

	@RequestMapping(value = "/dashboard/event/summary.htm")
	public ModelAndView showSummary(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("eventId") String eventId, ModelMap map) {
		if (SessionChecker.checkForUserSession(request)) {
			User loggedInUser = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
			Event event = eventDao.getEventById(eventId);
			if (event.checkAdminOrParticipant(loggedInUser) != 0) {
				double userTotalExpense = 0;
				List<Item> userItems = itemDao.getUserItemsForEvent(loggedInUser, event);
				for (Item item : userItems) {
					userTotalExpense = userTotalExpense + item.getTotalPrice();

				}

				System.out.println("USER TOTAL EXPENSE: " + userTotalExpense);
				double eventTotalExpense = 0;

				List<Item> claimedItems = itemDao.getClaimedItems(event);
				for (Item item : claimedItems) {
					eventTotalExpense = eventTotalExpense + item.getTotalPrice();
				}

				System.out.println("EVENT TOTAL EXPENSE: " + eventTotalExpense);

				double creditOrDebit = userTotalExpense
						- (eventTotalExpense / (double) event.getParticipatingUsers().size());

				System.out.println("CREDIT / DEBIT: " + creditOrDebit);
				map.addAttribute("eventTotalExpense", eventTotalExpense);

				map.addAttribute("creditOrDebit", creditOrDebit);
				map.addAttribute("event", event);
				map.addAttribute("userTotalExpense", userTotalExpense);
				return new ModelAndView("summary", "map", map);
			}
			System.out.println("unauthorised");
			map.addAttribute("error", "unauthorised");
			map.addAttribute("redirect", "/dashboard.htm");
			return new ModelAndView("evenrError", "map", map);
		}

		return RedirectionUtil.redirectToLogin(request, response);
	}

	@RequestMapping(value = "/dashboard/event/user/delete.htm", method = RequestMethod.POST)
	public ModelAndView deleteUserFromEvent(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String userId, @RequestParam("eventId") String eventId, ModelMap map) {
		if (SessionChecker.checkForUserSession(request) == true) {
			User loggedInUser = userDao.getUserByEmail((String) request.getSession().getAttribute("user"));
			Event event = eventDao.getEventById(eventId);
			System.out.println("deleting user: "+userId);
			if (event.checkAdminOrParticipant(loggedInUser) == 1) {
				System.out.println("authorised to perform delete user Action");
				User removeUser = userDao.getUserById(userId);
				if (event.removeUserFromParticipants(removeUser) == true) {
					System.out.println("removed froom event: "+event.getId());
					ArrayList<Item> itemList = (ArrayList<Item>) itemDao.getUserItemsForEvent(removeUser, event);
					for (Item i : itemList) {
						i.setFullfilledByUser(null);
						i.setTotalPrice(0.0);
						i.setFullFulledQuantity(0);
						itemDao.mergeItem(i);
					}
					eventDao.mergeEvent(event);
					
					return new ModelAndView("redirect:/dashboard/event.htm?id=" + eventId);
				}
				map.addAttribute("error", "cannot remove user");
				map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
				return new ModelAndView("eventError", "map", map);

			}
			map.addAttribute("error", "Un-Authorised");
			map.addAttribute("redirect", "/dashboard/event.htm?id=" + eventId);
			return new ModelAndView("eventError", "map", map);
		}

		return new ModelAndView("redirect:/login.htm");
	}

	@RequestMapping(value="/dashboard/event/item/removeClaim.htm", method=RequestMethod.POST)
	public ModelAndView removeIemClaim(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("itemId")String itemId, @RequestParam("eventId") String eventId, ModelMap map)
	{
		
		if (SessionChecker.checkForUserSession(request)==true)
		{
			
			User loggedInUser = userDao.getUserByEmail((String)request.getSession().getAttribute("user"));
			Event event = eventDao.getEventById(eventId);
			
			if (event.checkAdminOrParticipant(loggedInUser)!=0)
			{
				if (event.getActive()==true)
				{
					Item item = itemDao.getItemById(itemId);
					if (item!=null)
					{
						item.setFullfilledByUser(null);
						item.setFullFulledQuantity(0);
						item.setTotalPrice(0.0);
						itemDao.mergeItem(item);
						return new ModelAndView("redirect:/dashboard/event.htm?id="+eventId);
					}
					map.addAttribute("error", "item does not exist");
					map.addAttribute("redirect", "/dashboard/event.htm?id="+eventId);
					return new ModelAndView("eventError","map",map);
				}
				map.addAttribute("error", "event is inactive");
				map.addAttribute("redirect", "/dashboard/event.htm?id="+eventId);
				return new ModelAndView("eventError","map",map);
				
			}
			map.addAttribute("error", "unAuthorised");
			map.addAttribute("redirect", "/dashboard.htm");
			return new ModelAndView("eventError","map",map);
		}
	return new ModelAndView("redirect:/login.htm");
	}
}
