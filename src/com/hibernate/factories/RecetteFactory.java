package com.hibernate.factories;


import com.hibernate.dao.impl.RecetteHbnDaoImpl;

public class RecetteFactory extends AbstractFactory {
	@Override
	public RecetteHbnDaoImpl getRecetteDao(Class<? extends RecetteHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
            return null;
        }
        if(typeDao == RecetteHbnDaoImpl.class ) {
            return new RecetteHbnDaoImpl();
        }
        return null;
	}
	
}
