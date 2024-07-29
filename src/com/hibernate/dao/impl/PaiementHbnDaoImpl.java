package com.hibernate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Paiement;
import com.rms.model.Produit;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;



public class PaiementHbnDaoImpl implements IDao<Paiement>{

	@Override
	public void create(Paiement entity) throws DAOException {
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
	
	
    
	@Override
	public Paiement read(int id) throws DAOException {
		Paiement Paiement = null;
		try {
		Session session = HibernateConnection.getInstance() .getSession();
		Paiement = session.find(Paiement.class, id);
		
		} catch (Exception e) {
		throw new DAOException( "ERROR:" + e.getMessage());
		}
		return Paiement;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Paiement> list() throws DAOException {
		List<Paiement> Paiements = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();
			Query query = session.createQuery("From T_Paiements");
			Paiements = query.getResultList();
		} catch (Exception e) {
			throw new DAOException("Error " + e.getClass() +":"+ e.getMessage());
		}
			return Paiements;
	}

	@Override
	public void update(Paiement entity) throws DAOException {
		try {
			Session session = HibernateConnection.getInstance().getSession();
			//Creating Transaction Object
			Transaction transaction = session.beginTransaction();
			
			session.update(entity);
			// Transaction Is Committed To Database
			transaction. commit() ;
			} catch (Exception e) {
			throw new DAOException("Error "+ e.getClass() +":" + e.getMessage());
			}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void delete(int id) throws DAOException {
		try {
			Session session = HibernateConnection.getInstance().getSession();
			// Creating Transaction Object
			Transaction transaction = session.beginTransaction();
			
			Paiement Paiement = read(id);
			
			if (Paiement != null) session.delete(Paiement);
			// Transaction Is Committed To Database
			transaction. commit() ;
			} catch (Exception e) {
			throw new DAOException("ERROR:"+ e.getClass() + ": " + e.getMessage());
			}
		
	}

	
}
