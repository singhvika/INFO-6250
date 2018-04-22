package com.vikas.eventplanner.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.vikas.eventplanner.pojo.Event;
import com.vikas.eventplanner.pojo.User;

@Component
public class EventDAO extends DAO {
	
	Session session = null;
	Transaction tx = null;
	
	public ArrayList<Event> getParticipatingEvents(User participant)
	{
		session = getSession();
		Criteria cr = session.createCriteria(Event.class);
		return null;
	}
	
	
	public Event getEventById(String id)
	{
		System.out.println("getting by id:" +id);
		long eventId =  (long)Long.parseLong(id.trim());
		session = getSession();
		Criteria cr = session.createCriteria(Event.class);
		cr.add(Restrictions.eq("id", eventId));
		Event event =(Event) cr.uniqueResult();
		System.out.println("found event: "+event);
		return event;
		

	}
	
	
	public boolean mergeEvent(Event event)
	{
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession();
			tx = session.beginTransaction();
			session.merge(event);
			System.out.println("able to merge event");
			tx.commit();
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
