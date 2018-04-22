package com.vikas.eventplanner.dao;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.util.UserDataDocumentFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vikas.eventplanner.pojo.Invite;
import com.vikas.eventplanner.pojo.User;

@Component
public class InviteDAO extends DAO {
	Session session = null;
	Transaction tx = null;
	@Autowired
	UserDAO udao;

	@Autowired
	EventDAO eventDAO;
	
	public boolean saveInvite(Invite invite) {
		try {

			session = getSession();
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(Invite.class);
			/*
			 * cr.add(Restrictions.eqProperty("inviteStatus", "true"));
			 * cr.add(Restrictions.like("inviteForUser",
			 * invite.getInviteForUser().getId()));
			 */
			System.out.println("attempting to save invite: " + invite);
			session.save(invite);
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	private List<Invite> getStaleInvites(long eventId, String forUserEmail) {
		try {

			session = getSession();
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(Invite.class);
			cr.add(Restrictions.eq("id", eventId));
			User user = (User)udao.getUserByEmail(forUserEmail);
			cr.add(Restrictions.eq("id", user.getId()));
			List<Invite> staleEvents = cr.list();
			System.out.println("stale events:  " + staleEvents);
			tx.commit();
			return staleEvents;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	
	public Invite getInvite(String inviteUid)
	{
		try {
			session = getSession();
			tx = session.beginTransaction();
			
			
			Criteria cr = session.createCriteria(Invite.class);
			cr.add(Restrictions.like("uniqueId", inviteUid));
			Invite invite = (Invite)cr.uniqueResult();
			
			tx.commit();
			return invite;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			
			}
			return null;
		} finally {

			if (session != null) {
				session.close();
			}
		}

	}

	public Boolean invalidateStaleEvents(long eventId, String forUserEmail) {

		try {
			session = getSession();
			tx = session.beginTransaction();
			
			
			List<Invite> staleInvites = getStaleInvites(eventId, forUserEmail);
			for (Invite invite : staleInvites) {
				invite.setInviteStatus(Boolean.FALSE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {

			if (session != null) {
				session.close();
			}
		}

		

		return true;

	}
	
	
	public boolean mergeInvite(Invite invite)
	{
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession();
			tx = session.beginTransaction();
			session.merge(invite);
			System.out.println("able to merge invite");
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
