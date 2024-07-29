package com.hibernate.dao.impl;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;

import jakarta.persistence.Query;

public class ObjectHbnDaoImpl implements IDao<Object> {

	@Override
	public void create(Object entity) throws DAOException {
		// TODO Auto-generated method stub
		try {
			Session session = HibernateConnection.getInstance().getSession();
			
			Transaction transaction	= session.beginTransaction();
			
			session.persist(entity);
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + " : " + e.getMessage());
		}
	}

	@Override
	public Object read(int id) throws DAOException {
		// TODO Auto-generated method stub
		Object object = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			object = session.find(Object.class, id);
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return object;
	}

	@Override
	public List<Object> list() throws DAOException {
		// TODO Auto-generated method stub
		List<Object> objects = new ArrayList<>();
		try {
			Session session = HibernateConnection.getInstance().getSession();

			Query query = session.createQuery("From T_"+ Object.class.getName());
			objects = query.getResultList();
			//HibernateConnection.getInstance().closeSession();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return objects;
	}

	@Override
	public void update(Object entity) throws DAOException {
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
			
			Object object = read(id);
			if(object != null) {
				session.delete(object);
			}
			
			transaction.commit();
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
	}

	public void delete(int objectId, Class<?> entityClass) throws DAOException {
	    try {
	        Session session = HibernateConnection.getInstance().getSession();

	        Transaction transaction = session.beginTransaction();

	        Object object = session.get(entityClass, objectId);
	        if (object != null) {
	            session.delete(object);
	        }

	        transaction.commit();
	    } catch (Exception e) {
	        throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
	    }
	}
	public Object read(int id, Class<?> entityClass) throws DAOException {
		// TODO Auto-generated method stub
		Object object = null;
		try {
			Session session = HibernateConnection.getInstance().getSession();
			object = session.find(entityClass.getClass(), id);
			
		} catch (Exception e) {
			throw new DAOException("ERROR : " + e.getClass() + ":" + e.getMessage());
		}
		return object;
	}

}
