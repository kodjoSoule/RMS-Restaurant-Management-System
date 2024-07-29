package com.hibernate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Commande;
import com.rms.model.Produit;
import jakarta.persistence.Query;
public class ProduitHbnDaoImpl implements IDao<Produit> {
	public List<Produit> listProduitsByCommande(Commande commande) throws DAOException {
	    List<Produit> produitsByCommande = null;
	    
	    try {
	    	Session session = HibernateConnection.getInstance().getSession();
	        Transaction transaction = session.beginTransaction();
	        produitsByCommande = session.createQuery("SELECT p FROM T_Produits p JOIN p.commandes c WHERE c = :commande", Produit.class)
	                                    .setParameter("commande", commande)
	                                    .getResultList();
	        transaction.commit();
	    } catch (Exception e) {
	    	
	        throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
	    }
	    
	    return produitsByCommande;
	}

	@Override
	public void create(Produit entity) throws DAOException {
		
		try {
			Session session = HibernateConnection.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			session.save(entity);
			//session.persist(entity);
			// Transaction Is Committed To Database
			transaction.commit();
			
			} catch (Exception e) {
				
			throw new DAOException("ERROR:"+ e.getClass() + e.getMessage());
			}
			
	}

	@Override
	public Produit read(int id) throws DAOException {
		Produit Produit = null;
		try {
		Session session = HibernateConnection.getInstance() .getSession();
		Produit = session.find(Produit.class, id);
		
		} catch (Exception e) {
		throw new DAOException( "ERROR:" + e.getMessage());
		}
		
		return Produit;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Produit> list() throws DAOException {
		List<Produit> produits = new ArrayList<>();
		try {
		Session session = HibernateConnection.getInstance().getSession();
		Query query = session.createQuery("From T_Produits");
		produits = query.getResultList();
		} catch (Exception e) {
		throw new DAOException("Error " + e.getClass() +":"+ e.getMessage());
		}
		return produits;

	}

	@Override
	public void update(Produit entity) throws DAOException {
		try {
			Session session = HibernateConnection.getInstance().getSession();
			//Creating Transaction Object
			Transaction transaction = session.beginTransaction();
			
			session.update(entity);
			// Transaction Is Committed To Database
			transaction. commit() ;
			} catch (Exception e) {
			throw new DAOException("Error "+ e.getClass() +":" + e.getMessage());
			}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void delete(int id) throws DAOException {
		try {
			Session session = HibernateConnection.getInstance().getSession();
			// Creating Transaction Object
			Transaction transaction = session.beginTransaction();
			
			Produit Produit = read(id);
			
			if (Produit != null) session.delete(Produit);
			// Transaction Is Committed To Database
			transaction. commit() ;
			} catch (Exception e) {
			throw new DAOException("ERROR:"+ e.getClass() + ": " + e.getMessage());
			}
		
	}

}
