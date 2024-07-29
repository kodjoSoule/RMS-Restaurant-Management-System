package com.hibernate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Commande;

import jakarta.persistence.Query;

public class CommandeHbnDaoImpl implements IDao<Commande>{
	
	public List<Commande> listAvecProduits() throws DAOException {
        List<Commande> commandesAvecProduits = null;
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            commandesAvecProduits = session.createQuery("SELECT DISTINCT c FROM T_Commandes c JOIN FETCH c.produits", Commande.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
        }
        return commandesAvecProduits;
    }
	//
	
	@Override
	public void create(Commande entity) throws DAOException {
		try {
			Session session = HibernateConnection.getInstance().getSession();
			Transaction transaction = session. beginTransaction();
			session.save(entity);
			//session.persist(entity);
			// Transaction Is Committed To Database
			transaction.commit();
			} catch (Exception e) {
			throw new DAOException("ERROR:"+ e.getClass() + e.getMessage());
			}
	}

	@Override
	public Commande read(int id) throws DAOException {
		Commande Commande = null;
		try {
		Session session = HibernateConnection.getInstance() .getSession();
		Commande = session.find(Commande.class, id);
		} catch (Exception e) {
			throw new DAOException( "ERROR:" + e.getMessage());
		}
		return Commande;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Commande> list() throws DAOException {
		List<Commande> Commandes = new ArrayList<>();
		try {
		Session session = HibernateConnection.getInstance().getSession();
		Query query = session.createQuery("From T_Commandes");
		//Query query = session.createQuery("From User");
		Commandes = query.getResultList();
		
		} catch (Exception e) {
		throw new DAOException("Error " + e.getClass() +":"+ e.getMessage());
		}
		return Commandes;
		
	}
	//hk listByClasse
	
//    public List<Commande> listByClasse(Classe classe) throws DAOException {
//		System.out.println("Liste des étudiants pour la classe : " + classe.getLibelle());
//        // Utilisation d'une requête HQL pour récupérer les étudiants associés à la classe spécifiée
//		Session session = HibernateConnection.getInstance().getSession();
//		String hql = "FROM T_Commandes e WHERE e.classe = :classe";
//        Query query = session.createQuery(hql).setParameter("classe", classe);;
//        List<Commande> etudiants = null ; 
//        etudiants = query.getResultList();
//        
//        
//        
//	    
//        return etudiants;
//        
//		
//		
//	}
//	
//	public List<Commande> getCommandesByClasse(Classe classe) throws DAOException {
//	    List<Commande> etudiants = new ArrayList<>();
//	    try {
//	        Session session = HibernateConnection.getInstance().getSession();
//	        //Query query = session.createQuery("SELECT e FROM T_Commandes e WHERE e.classe = :classe");
//	        Query query = session.createQuery("FROM T_Commandes e WHERE e.classe = :classe");
//	        query.setParameter("classe", classe);
//	        etudiants = query.getResultList();
//	    } catch (Exception e) {
//	        throw new DAOException("Error: " + e.getClass() + ": " + e.getMessage());
//	    }
//	    
//	    return etudiants;
//	}
	@Override
	public void update(Commande entity) throws DAOException {
		try {
			Session session = HibernateConnection.getInstance().getSession();
			//Creating Transaction Object
			Transaction transaction = session.beginTransaction();
			
			session.update(entity);
			// Transaction Is Committed To Database
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
			Commande etudiant = session.get(Commande.class, id);
			session.delete(etudiant);
			
			if (etudiant != null) session.delete(etudiant);
			// Transaction Is Committed To Database
			transaction.commit();
			} catch (Exception e) {
			throw new DAOException("ERROR:"+ e.getClass() + ": " + e.getMessage());
			}
		
	}
	
	public Commande create1(Commande entity) throws DAOException {
	    try {
	        Session session = HibernateConnection.getInstance().getSession();
	        Transaction transaction = session.beginTransaction();
	        session.save(entity);
	        //session.persist(entity);
	        // Transaction Is Committed To Database
	        transaction.commit();
	        return entity;
	    } catch (Exception e) {
	        throw new DAOException("ERROR:" + e.getClass() + e.getMessage());
	    }
	}


}
