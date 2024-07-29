package com.hibernate.factories;

import com.hibernate.dao.impl.AdministrateurHbnDaoImpl;
import com.hibernate.dao.impl.ProduitHbnDaoImpl;

public class ProduitFactory extends AbstractFactory {
	
	public ProduitFactory() {}

	@Override
	public ProduitHbnDaoImpl getProduitDao(Class<? extends ProduitHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == ProduitHbnDaoImpl.class) {
			return new ProduitHbnDaoImpl();
		} 
		
		return null;
	}
}
