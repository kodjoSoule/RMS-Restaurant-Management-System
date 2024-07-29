package com.hibernate.factories;

import com.hibernate.dao.impl.CommandeHbnDaoImpl;

public class CommandeFactory extends AbstractFactory {
	@Override
	public CommandeHbnDaoImpl getCommandeDao(Class<? extends CommandeHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		if(typeDao == CommandeHbnDaoImpl.class ) {
			return new CommandeHbnDaoImpl();
		}
		return null;
		
	}
}
