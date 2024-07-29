package com.hibernate.factories;

import com.hibernate.dao.impl.CommandeHbnDaoImpl;
import com.hibernate.dao.impl.UtilisateurHbnDaoImpl;

public class UtilisateurFactory extends AbstractFactory {
	@Override
	public UtilisateurHbnDaoImpl getUtilisateurDao(Class<? extends UtilisateurHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		if(typeDao == UtilisateurHbnDaoImpl.class ) {
			return new UtilisateurHbnDaoImpl();
		}
		return null;
		
	}
}
