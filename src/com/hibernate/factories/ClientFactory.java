package com.hibernate.factories;

import com.hibernate.dao.impl.ClientHbnDaoImpl;
import com.hibernate.dao.impl.CommandeHbnDaoImpl;
import com.hibernate.dao.impl.UtilisateurHbnDaoImpl;

public class ClientFactory extends AbstractFactory {
	@Override
	public ClientHbnDaoImpl getClientDao(Class<? extends ClientHbnDaoImpl> typeDao) {
		if ( typeDao == null ) {
			return null;
		}
		if(typeDao == ClientHbnDaoImpl.class ) {
			return new ClientHbnDaoImpl();
		}
		return null;
		
	}
}
