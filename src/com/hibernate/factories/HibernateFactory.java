package com.hibernate.factories;

import com.hibernate.dao.impl.ObjectHbnDaoImpl;

public class HibernateFactory extends AbstractFactory {
	
	public HibernateFactory() {}

	@Override
	public ObjectHbnDaoImpl getObjectHibernateDao (Class<? extends ObjectHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == ObjectHbnDaoImpl.class) {
			return new ObjectHbnDaoImpl ();
		} 
		
		return null;
	}
}
