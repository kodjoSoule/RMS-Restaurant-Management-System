package com.hibernate.factories;

import com.hibernate.dao.impl.LigneCommandeHbnDaoImpl;


public class LigneCommandeFactory extends AbstractFactory {
	@Override
	public LigneCommandeHbnDaoImpl getLigneCommandeDao(Class<? extends LigneCommandeHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		if(typeDao == LigneCommandeHbnDaoImpl.class ) {
			return new LigneCommandeHbnDaoImpl();
		}
		return null;
		
	}
}
