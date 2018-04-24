package com.vikas.eventplanner.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.vikas.eventplanner.pojo.Event;
import com.vikas.eventplanner.pojo.Item;
import com.vikas.eventplanner.pojo.User;

@Component
public class ItemDAO extends DAO {

	public Item getItemById(String id) {
		Session session = null;
		try {
			session = getSession();
			Criteria cr = session.createCriteria(Item.class);
			long itemId = (long) Long.parseLong(id.trim());
			cr.add(Restrictions.idEq(itemId));
			Item item = (Item) cr.uniqueResult();
			return item;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (session != null)
				session.close();
		}

	}

	public boolean mergeItem(Item item)

	{
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession();
			tx = session.getTransaction();
			tx.begin();
			session.merge(item);
			tx.commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
			return false;
		} finally {
			if (session != null)
				session.close();
		}

	}

	public List<Item> getItemsPageByEvent(Event event, int page) {
		Session session = null;
		Transaction tx = null;
		try {
			System.out.println("getting participating items for: " + event.getId());
			session = getSession();
			Criteria cr = session.createCriteria(Item.class);
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) ;
			cr.add(Restrictions.eq("participatingEvent", event.getId()));
			cr.setMaxResults(5);
			cr.setFirstResult(page * 5 - 5);
			System.out.println("FOUND LIST: " + cr.list());

			return (ArrayList<Item>) cr.list();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;

		} finally {
			if (session != null)
				session.close();
		}
	}

	public List<Item> getUserItemsForEvent(User user, Event event) {
		Session session = null;
		try {

			session = getSession();
			Criteria cr = session.createCriteria(Item.class);
			cr.createAlias("participatingEvent", "itemEvent");
			cr.createAlias("fullfilledByUser", "itemUser");
			cr.add(Restrictions.eq("itemUser.id", user.getId()));
			cr.add(Restrictions.eq("itemEvent.id", event.getId()));
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
			return cr.list();

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}
	
	
	public List<Item> getClaimedItems(Event event) {
		Session session = null;
		
		try {
			session = getSession();
			Criteria cr = session.createCriteria(Item.class);
			cr.createAlias("participatingEvent", "itemEvent");
			cr.add(Restrictions.eq("itemEvent.id", event.getId()));
			return cr.list();
				
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		finally {
			if(session!=null)
				session.close();
		}
	}

}
