package com.hibernate.factories;

import com.hibernate.dao.impl.CategorieHbnDaoImpl;

public class CategorieFactory extends AbstractFactory {
	@Override
	public CategorieHbnDaoImpl getCategorieDao (Class<? extends CategorieHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		if(typeDao == CategorieHbnDaoImpl.class ) {
			return new CategorieHbnDaoImpl();
		}
		return null;
		
	}
}
