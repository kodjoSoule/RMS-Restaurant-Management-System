package com.hibernate.factories;


import com.hibernate.dao.impl.PaiementHbnDaoImpl;

public class PaiementFactory extends AbstractFactory {
	@Override
	public PaiementHbnDaoImpl getPaiementDao(Class<? extends PaiementHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
            return null;
        }
        if(typeDao == PaiementHbnDaoImpl.class ) {
            return new PaiementHbnDaoImpl();
        }
        return null;
	}
	
}
