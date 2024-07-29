package com.hibernate.dao.impl;

import java.util.ArrayList;


import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Produit;
import com.rms.model.Utilisateur;

import org.hibernate.Session;
import org.hibernate.Transaction;


import jakarta.persistence.Query;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



public class UtilisateurHbnDaoImpl implements IDao<Utilisateur> {
	public boolean authenticate(Utilisateur utilisateur) {
	    String loginField = utilisateur.getLogin();
	    String passwordField = utilisateur.getPassword();
	    
	    try {
	        Session session = HibernateConnection.getInstance().getSession();
	        String hql = "FROM T_Utilisateurs WHERE login = :login AND password = :password";
	        Utilisateur result = session.createQuery(hql, Utilisateur.class)
	            .setParameter("login", loginField)
	            .setParameter("password", passwordField)
	            .uniqueResult();
	        
	        if (result != null) {
	            utilisateur.setId(result.getId());
	            utilisateur.setNom(result.getNom());
	            utilisateur.setPrenom(result.getPrenom());
	            utilisateur.setEmail(result.getEmail());
	            utilisateur.setTelephone(result.getTelephone());
	            utilisateur.setRole(result.getRole());
	            return true;
	        } else {
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

    @Override
    public void create(Utilisateur entity) throws DAOException {
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

    @Override
    public Utilisateur read(int id) throws DAOException {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
        	
            return session.get(Utilisateur.class, id);

        } catch (Exception e) {
            throw new DAOException("Error reading entity with ID: " + id);
        }
    }

    @Override
    public List<Utilisateur> list() throws DAOException {
    	List<Utilisateur> utilisateurs = new ArrayList<>();
    	try {
    		Session session = HibernateConnection.getInstance().getSession();
    		Query query = session.createQuery("From T_Utilisateurs");
    		utilisateurs = query.getResultList();
    		} catch (Exception e) {
    		throw new DAOException("Error " + e.getClass() +":"+ e.getMessage());
    		}
    		//return users2 ;
    		return utilisateurs;
    	
    }

    @Override
    public void update(Utilisateur entity) throws DAOException {
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

            Utilisateur entity = session.get(Utilisateur.class, id);
            if (entity != null) {
                session.beginTransaction();
                session.delete(entity);
                session.getTransaction().commit();
            }

        } catch (Exception e) {
            throw new DAOException("Error deleting entity with ID: " + id);
        }
    }

	public boolean isUserExists(String adminLogin) throws DAOException {
		
		    try {
		        Session session = HibernateConnection.getInstance().getSession();
		        String hql = "SELECT COUNT(*) FROM T_Utilisateurs WHERE login = :login";
		        Long count = (Long) session.createQuery(hql)
		            .setParameter("login", adminLogin)
		            .getSingleResult();
		        
		        return count > 0;
		    } catch (Exception e) {
		        throw new DAOException("Error checking if user exists: " + e.getMessage());
		    }
		}

}
