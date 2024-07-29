package com.hibernate.factories;



import com.hibernate.dao.impl.AdministrateurHbnDaoImpl;


public class AdministrateurFactory extends AbstractFactory {
	@Override
	public AdministrateurHbnDaoImpl getAdministrateurDao(Class<? extends AdministrateurHbnDaoImpl> typeDao) {
		if(typeDao == null) {
			return null;
		}
		if(typeDao == AdministrateurHbnDaoImpl.class) {
			return new AdministrateurHbnDaoImpl();
		}
		return null;
		
	}
}
