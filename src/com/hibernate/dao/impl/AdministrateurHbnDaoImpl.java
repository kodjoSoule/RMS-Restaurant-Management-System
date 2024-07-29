package com.hibernate.dao.impl;

import java.util.ArrayList;



import java.util.List;

import org.hibernate.Session;


import org.hibernate.Transaction;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Administrateur;
import org.hibernate.query.*;


public class AdministrateurHbnDaoImpl implements IDao<Administrateur> {

	@Override
	public void create(Administrateur entity) throws DAOException {
		
		try {
			
			Session session = HibernateConnection.getInstance().getSession();
			Transaction transaction = session. beginTransaction();
			session.save(entity);
			//session.persist(entity);
			// Transaction Is Committed To Database
			transaction.commit();
			} catch (Exception e) {
			throw new DAOException("ERROR:"+ e.getClass() + e.getMessage());
			}

	}
	public Administrateur getByUsername(String username) throws DAOException {
        Administrateur administrateur = null;
        try {
            Session session = HibernateConnection.getInstance().getSession();

            Query<Administrateur> query = session.createQuery("FROM Administrateur WHERE username = :username", Administrateur.class);
            query.setParameter("username", username);
            
            administrateur = query.uniqueResult();
        } catch (Exception e) {
            throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
        }
        return administrateur;
    }

	@Override
	public Administrateur read(int id) throws DAOException {
		// TODO Auto-generated method stub
		Administrateur enseignant = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			enseignant = session.find(Administrateur.class, id);
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return enseignant;
	}

	@Override
	public List<Administrateur> list() throws DAOException {
		// TODO Auto-generated method stub
		List<Administrateur> enseignants = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();

			org.hibernate.query.Query query = session.createQuery("From T_Administrateurs");
			enseignants = query.getResultList();
			//HibernateConnection.getInstance().closeSession();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return enseignants;
	}

	@Override
	public void update(Administrateur entity) throws DAOException {
		// TODO Auto-generated method stub
		try {
			Session session = HibernateConnection.getInstance().getSession();
			
			Transaction transaction	= session.beginTransaction();
			session.update(entity);
			transaction.commit();
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}

	@Override
	public void delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		try {
			Session session = HibernateConnection.getInstance().getSession();
			
			Transaction transaction	= session.beginTransaction();
			
			Administrateur enseignant = read(id);
			if(enseignant != null) {
				session.delete(enseignant);
			}
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}

}
