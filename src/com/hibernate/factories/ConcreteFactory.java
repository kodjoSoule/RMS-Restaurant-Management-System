package com.hibernate.factories;

import com.rms.model.LigneCommande;

public class ConcreteFactory {
	public static AbstractFactory getFactory(Class<? extends AbstractFactory> factory) {
		if (factory ==null) {
			return null;
		}
		
		if( factory == CommandeFactory.class ) {
            return new CommandeFactory();
        }
		if( factory == AdministrateurFactory.class ) {
            return new AdministrateurFactory();
        }
		if (factory == PaiementFactory.class ) {
			return new PaiementFactory();
		}
		if (factory == RecetteFactory.class ) {
			return new RecetteFactory();
		}
		if( factory == ProduitFactory.class ) {
			return new ProduitFactory();
		}
		if (factory == HibernateFactory.class) {
				return new HibernateFactory ();
		}
		if (factory == ChefCuisinierFactory.class) {
			return new ChefCuisinierFactory ();
		}
		
		if (factory == CategorieFactory.class) {
			return new CategorieFactory();
		}
		if (factory == UtilisateurFactory.class) {
			return new UtilisateurFactory();
		}
		if (factory == ClientFactory.class) {
			return new ClientFactory();
		}
		if (factory == LigneCommandeFactory.class) {
			return new LigneCommandeFactory();
		}
		
		return null;
	}
}
