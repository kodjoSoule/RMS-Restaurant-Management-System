package com.hibernate.dao.impl;

import java.util.ArrayList;


import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Client;
import org.hibernate.Transaction;


import jakarta.persistence.Query;

public class ClientHbnDaoImpl implements IDao<Client> {
	@Override
    public void create(Client entity) throws DAOException {
    	try {
			Session session = HibernateConnection.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			session.save(entity);
			//session.persist(entity);
			// Transaction Is Committed To Database
			transaction.commit();
			} catch (Exception e) {
			throw new DAOException("ERROR:"+ e.getClass() + e.getMessage());
			}
    }
	public Client create1(Client entity) throws DAOException {
	    try {
	        Session session = HibernateConnection.getInstance().getSession();
	        Transaction transaction = session.beginTransaction();
	        session.save(entity);
	        transaction.commit();
	        return entity; // Renvoie le client après l'enregistrement
	    } catch (Exception e) {
	        throw new DAOException("ERROR: " + e.getClass() + e.getMessage());
	    }
	}

    @Override
    public Client read(int id) throws DAOException {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
        	
            return session.get(Client.class, id);

        } catch (Exception e) {
            throw new DAOException("Error reading entity with ID: " + id);
        }
    }

    @Override
    public List<Client> list() throws DAOException {
    	List<Client> utilisateurs = new ArrayList<>();
    	try {
    		Session session = HibernateConnection.getInstance().getSession();
    		Query query = session.createQuery("From T_Clients");
    		utilisateurs = query.getResultList();
    		} catch (Exception e) {
    		throw new DAOException("Error " + e.getClass() +":"+ e.getMessage());
    		}
    		//return users2 ;
    		return utilisateurs;
    	
    }

    @Override
    public void update(Client entity) throws DAOException {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();

        } catch (Exception e) {
            throw new DAOException("Error updating entity");
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            Client entity = session.get(Client.class, id);
            if (entity != null) {
                session.beginTransaction();
                session.delete(entity);
                session.getTransaction().commit();
            }

        } catch (Exception e) {
            throw new DAOException("Error deleting entity with ID: " + id);
        }
    }
}
