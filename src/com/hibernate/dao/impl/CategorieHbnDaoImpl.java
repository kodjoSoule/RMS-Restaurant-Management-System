package com.hibernate.dao.impl;

import java.util.ArrayList;


import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Categorie;
import jakarta.persistence.Query;

public class CategorieHbnDaoImpl implements IDao<Categorie>{

	@Override
	public void create(Categorie entity) throws DAOException {
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
	public Categorie read(int id) throws DAOException {
		Categorie categorie = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			categorie = session.find(Categorie.class, id);
			} catch (Exception e) {
					throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
			}
		return categorie;
		
	}

	@Override
	public List<Categorie> list() throws DAOException {
		List<Categorie> categories = new ArrayList<>();
		List<Categorie> categories2 = new ArrayList<>();
		categories2.add(new Categorie(1, "Fruits", "Fruits"));
		categories2.add(new Categorie(2, "Vegetables", "Vegetables"));
		categories2.add(new Categorie(3, "Meat", "Meat"));
		categories2.add(new Categorie(4, "Dairy", "Dairy"));
		
		try {
		Session session = HibernateConnection.getInstance().getSession();
		Query query = session.createQuery("From T_Categories");
		categories = query.getResultList();
		
		} catch (Exception e) {
		throw new DAOException("Error " + e.getClass() +":"+ e.getMessage());
		}
		return categories;

	}

	@Override
	public void update(Categorie entity) throws DAOException {
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
		try {
			Session session = HibernateConnection.getInstance().getSession();
			Transaction transaction	= session.beginTransaction();
			Categorie categorie = read(id);
			if(categorie != null) {
				session.delete(categorie);
			}
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}
	
}
