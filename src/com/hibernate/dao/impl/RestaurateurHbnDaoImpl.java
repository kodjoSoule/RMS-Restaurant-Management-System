package com.hibernate.dao.impl;

import java.util.ArrayList;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Restaurateur;

import jakarta.persistence.Query;

public class RestaurateurHbnDaoImpl implements IDao<Restaurateur> {

	@Override
	public void create(Restaurateur entity) throws DAOException {
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
	public Restaurateur read(int id) throws DAOException {
		Restaurateur Restaurateur = null;
		try {
		Session session = HibernateConnection.getInstance() .getSession();
		Restaurateur = session.find(Restaurateur.class, id);
		
		} catch (Exception e) {
		throw new DAOException( "ERROR:" + e.getMessage());
		}
		return Restaurateur;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Restaurateur> list() throws DAOException {
		List<Restaurateur> classes = new ArrayList<>();
		//
		//
		try {
		Session session = HibernateConnection.getInstance().getSession();
		Query query = session.createQuery("From t_classes");
		//Query query = session.createQuery("From User");
		classes = query.getResultList();
		
		} catch (Exception e) {
		throw new DAOException("Error " + e.getClass() +":"+ e.getMessage());
		}
		
		return classes;
		
		
	}

	@Override
	public void update(Restaurateur entity) throws DAOException {
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
			Restaurateur classe = session.get(Restaurateur.class, id);
			if (classe != null) session.delete(classe);
			//Transaction Is Committed To Database
			transaction.commit();
			}catch (Exception e) {
			throw new DAOException("ERROR:"+ e.getClass() + ": " + e.getMessage());
			}
		
	}

}
