package com.vikas.eventplanner.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.vikas.eventplanner.pojo.User;

public class UserDAO {

	

	
	public UserDAO() {
		
		// TODO Auto-generated constructor stub
	}

	public Session getSession() {
		Configuration cfg = new Configuration().configure("hibernate.cfg.xml");;
		SessionFactory sf = cfg.buildSessionFactory();
		System.out.println("gettin session");
		return sf.openSession();

	}

	public Boolean saveUser(User user) {
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession();
			tx = session.beginTransaction();
			session.save(user);
			System.out.println("able to save user");
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

	public User getUserByEmail(User user) {
		System.out.println("getting user by email: " +user.getEmail());
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession();
			System.out.println("Session: "+session);
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.like("email", "%" + user.getEmail() + "%"));
			User foundUser = (User) cr.uniqueResult();
			System.out.println("foundUser: "+foundUser);
			return foundUser;

		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
