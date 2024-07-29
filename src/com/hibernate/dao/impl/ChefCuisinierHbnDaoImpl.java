package com.hibernate.dao.impl;

import java.util.ArrayList;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.ChefCuisinier;

import jakarta.persistence.Query;

public class ChefCuisinierHbnDaoImpl implements IDao<ChefCuisinier> {

	@Override
	public void create(ChefCuisinier chefCuisinier) throws DAOException {
		// TODO Auto-generated method stub
		try {
			Session session = HibernateConnection.getInstance().getSession();
			
			Transaction transaction	= session.beginTransaction();
			
			session.persist(chefCuisinier);
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + " : " + e.getMessage());
		}
	}

	@Override
	public ChefCuisinier read(int id) throws DAOException {
		// TODO Auto-generated method stub
		ChefCuisinier chefCuisinier = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			chefCuisinier = session.find(ChefCuisinier.class, id);
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return chefCuisinier;
	}

	@Override
	public List<ChefCuisinier> list() throws DAOException {
		// TODO Auto-generated method stub
		List<ChefCuisinier> chefCuisiniers = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();

			Query query = session.createQuery("From T_ChefCuisiniers");
			chefCuisiniers = query.getResultList();
			//HibernateConnection.getInstance().closeSession();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return chefCuisiniers;
	}

	@Override
	public void update(ChefCuisinier entity) throws DAOException {
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
			
			ChefCuisinier chefCuisinier = read(id);
			if(chefCuisinier != null) {
				session.delete(chefCuisinier);
			}
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}


}
