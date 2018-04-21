package com.vikas.eventplanner.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.vikas.eventplanner.pojo.Invite;

@Component
public class InviteDAO extends DAO{
	Session session = null;
	Transaction tx = null;
	
	public boolean saveInvite(Invite invite)
	{
		try {
			
			session = getSession();
			tx = session.beginTransaction();
			Criteria cr = session.createCriteria(Invite.class);
/*			cr.add(Restrictions.eqProperty("inviteStatus", "true"));
			cr.add(Restrictions.like("inviteForUser", invite.getInviteForUser().getId()));*/
			System.out.println("attempting to save invite: "+invite);
			session.save(invite);
			tx.commit();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			if(tx!=null)
			{
				tx.rollback();
			}
			return false;
		}
		finally {
			if (session!=null)
			{
				session.close();
			}
		}
		
		
	}
	
}
