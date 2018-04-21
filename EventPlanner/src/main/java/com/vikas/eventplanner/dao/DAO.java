package com.vikas.eventplanner.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAO {
	
	public Session getSession()
	{
		Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
		;
		SessionFactory sf = cfg.buildSessionFactory();
		System.out.println("gettin session");
		return sf.openSession();	
	}

}
