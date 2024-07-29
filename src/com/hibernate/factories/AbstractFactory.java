package com.hibernate.factories;

import com.hibernate.dao.impl.ProduitHbnDaoImpl;

import com.hibernate.dao.impl.RecetteHbnDaoImpl;
import com.hibernate.dao.impl.RestaurateurHbnDaoImpl;
import com.hibernate.dao.impl.UtilisateurHbnDaoImpl;
import com.hibernate.dao.impl.AdministrateurHbnDaoImpl;
import com.hibernate.dao.impl.CategorieHbnDaoImpl;
import com.hibernate.dao.impl.CommandeHbnDaoImpl;
import com.hibernate.dao.impl.LigneCommandeHbnDaoImpl;
import com.hibernate.dao.impl.ChefCuisinierHbnDaoImpl;
import com.hibernate.dao.impl.ClientHbnDaoImpl;
import com.hibernate.dao.impl.PaiementHbnDaoImpl;
import com.hibernate.dao.impl.ObjectHbnDaoImpl;



public abstract class AbstractFactory {
	public RestaurateurHbnDaoImpl getRestaurateurDao(Class<? extends RestaurateurHbnDaoImpl> typeDao) {
		return null ;
	}
	public CommandeHbnDaoImpl getCommandeDao(Class<? extends CommandeHbnDaoImpl> typeDao) {
		return null ;
	}
	public ProduitHbnDaoImpl getProduitDao(Class<? extends ProduitHbnDaoImpl> typeDao) {
		return null ;
	}
	
	public PaiementHbnDaoImpl getPaiementDao(Class<? extends PaiementHbnDaoImpl> typeDao) {
        return null ;
    }
	public RecetteHbnDaoImpl getRecetteDao(Class<? extends RecetteHbnDaoImpl> typeDao) {
        return null ;
    }
	
	public AdministrateurHbnDaoImpl getAdministrateurDao(Class<? extends AdministrateurHbnDaoImpl> typeDao) {
		return null ;
	}
	//module
	public ChefCuisinierHbnDaoImpl getChefCuisinierDao(Class<? extends ChefCuisinierHbnDaoImpl> typeDao) {
        return null ;
    }
	public ObjectHbnDaoImpl getObjectHibernateDao (Class<? extends ObjectHbnDaoImpl> typeDao) {
		return null ;
	}
	//Categorie
	public CategorieHbnDaoImpl getCategorieDao(Class<? extends CategorieHbnDaoImpl> typeDao) {
        return null ;
    }
	//utilisateur
	public UtilisateurHbnDaoImpl getUtilisateurDao (Class<? extends UtilisateurHbnDaoImpl> typeDao) {
		return null ;
	}
	//Client
	public ClientHbnDaoImpl getClientDao (Class<? extends ClientHbnDaoImpl> typeDao) {
		return null ;
	}
	public LigneCommandeHbnDaoImpl getLigneCommandeDao (Class<? extends LigneCommandeHbnDaoImpl> typeDao) {
		return null ;
	}
}
