package com.hibernate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Recette;
import jakarta.persistence.Query;

public class RecetteHbnDaoImpl implements IDao<Recette>{

	@Override
	public void create(Recette entity) throws DAOException {
		try {
			Session session = HibernateConnection.getInstance().getSession();
			Transaction transaction = session. beginTransaction();
			session.save(entity);
			// Transaction Is Committed To Database
			transaction.commit();
			} catch (Exception e) {
			throw new DAOException("ERROR:"+ e.getClass() + e.getMessage());
			}
	}

	@Override
	public Recette read(int id) throws DAOException {
		Recette Recette = null;
		try {
		Session session = HibernateConnection.getInstance() .getSession();
		Recette = session.find(Recette.class, id);
		
		} catch (Exception e) {
		throw new DAOException( "ERROR:" + e.getMessage());
		}
		return Recette;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Recette> list() throws DAOException {
		List<Recette> Recettes = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();
			Query query = session.createQuery("From T_Recettes");
			Recettes = query.getResultList();
		} catch (Exception e) {
			throw new DAOException("Error " + e.getClass() +":"+ e.getMessage());
		}
			return Recettes;
	}

	@Override
	public void update(Recette entity) throws DAOException {
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
			
			Recette Recette = read(id);
			
			if (Recette != null) session.delete(Recette);
			// Transaction Is Committed To Database
			transaction. commit() ;
			} catch (Exception e) {
			throw new DAOException("ERROR:"+ e.getClass() + ": " + e.getMessage());
			}
		
	}

}
