package com.hibernate.factories;

import com.hibernate.dao.impl.ChefCuisinierHbnDaoImpl;

public class ChefCuisinierFactory extends AbstractFactory {
	
	public ChefCuisinierFactory() {}

	@Override
	public ChefCuisinierHbnDaoImpl getChefCuisinierDao (Class<? extends ChefCuisinierHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		
		if (typeDao == ChefCuisinierHbnDaoImpl.class) {
			return new ChefCuisinierHbnDaoImpl ();
		} 
		
		return null;
	}
}
