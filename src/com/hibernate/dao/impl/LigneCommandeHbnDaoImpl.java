package com.hibernate.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import com.rms.dao.IDao;
import com.rms.database.HibernateConnection;
import com.rms.exceptions.DAOException;
import com.rms.model.Commande;
import com.rms.model.LigneCommande;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import com.rms.model.Produit;
import com.rms.model.Utilisateur;

public class LigneCommandeHbnDaoImpl implements IDao<LigneCommande> {
	
	public LigneCommande getLigneCommandeByProduitAndCommande2(Produit produit, Commande commande) throws DAOException {
	    try {
	        Session session = HibernateConnection.getInstance().getSession();
	        LigneCommande ligneCommande = session.createQuery(
	            "SELECT lc FROM T_LignesCommande lc WHERE lc.produit = :produit AND lc.commande = :commande", LigneCommande.class)
	            .setParameter("produit", produit)
	            .setParameter("commande", commande)
	            .getSingleResult();

	        return ligneCommande; // Retourne la ligne de commande correspondante
	    } catch (NoResultException e) {
	        return null; // Aucun résultat trouvé, retourne null
	    } catch (Exception e) {
	        throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
	    }
	}

	public LigneCommande getLigneCommandeByProduitAndCommande(Produit produit, Commande commande) throws DAOException {
	    try {
	        Session session = HibernateConnection.getInstance().getSession();
	        LigneCommande ligneCommande = (LigneCommande) session.createQuery(
	            "SELECT lc FROM T_LignesCommande lc WHERE lc.produit = :produit AND lc.commande = :commande", LigneCommande.class)
	            .setParameter("produit", produit)
	            .setParameter("commande", commande)
	            .uniqueResult();

	        return ligneCommande; // Si ligneCommande est null, cela signifie que la ligne n'existe pas
	    } catch (Exception e) {
	        throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
	    }
	}

	public boolean doesLigneCommandeExist(Produit produit, Commande commande) throws DAOException {
	    try {
	        Session session = HibernateConnection.getInstance().getSession();
	        LigneCommande ligneCommande = (LigneCommande) session.createQuery(
	            "SELECT lc FROM T_LignesCommande lc WHERE lc.produit = :produit AND lc.commande = :commande", LigneCommande.class)
	            .setParameter("produit", produit)
	            .setParameter("commande", commande)
	            .uniqueResult();

	        return ligneCommande != null; // Si ligneCommande est null, cela signifie que la ligne n'existe pas
	    } catch (Exception e) {
	        throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
	    }
	}

	
	public int getQuantiteCommandee(Produit produit, Commande commande) throws DAOException {
	    try {
	        Session session = HibernateConnection.getInstance().getSession();
	        LigneCommande ligneCommande = (LigneCommande) session.createQuery(
	            "SELECT lc FROM T_LignesCommande lc WHERE lc.produit = :produit AND lc.commande = :commande", LigneCommande.class)
	            .setParameter("produit", produit)
	            .setParameter("commande", commande)
	            .uniqueResult();

	        if (ligneCommande != null) {
	            return ligneCommande.getQuantite();
	        } else {
	            return 0; // Le produit n'a pas été trouvé dans la commande
	        }
	    } catch (Exception e) {
	        throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
	    }
	}
	
	public List<Tuple> GetAllLinkedData() {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            String query = "SELECT c, p, lc FROM t_Commandes c " +
                           "JOIN c.lignesCommande lc " +
                           "JOIN lc.produit p";
            
            NativeQuery<Tuple> nativeQuery = session.createNativeQuery(query, Tuple.class);
            List<Tuple> linkedDataList = nativeQuery.getResultList();
            
            return linkedDataList;
        } catch (Exception e) {
            // Gérer l'exception si nécessaire
            return null;
        }
    }
	
    @Override
    public void create(LigneCommande entity) throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
        }
    }

    @Override
    public LigneCommande read(int id) throws DAOException {
        LigneCommande ligneCommande = null;
        try {
            Session session = HibernateConnection.getInstance().getSession();
            ligneCommande = session.find(LigneCommande.class, id);
        } catch (Exception e) {
            throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
        }
        return ligneCommande;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LigneCommande> list() throws DAOException {
        List<LigneCommande> lignesCommande = null;
        try {
            Session session = HibernateConnection.getInstance().getSession();
            lignesCommande = session.createQuery("FROM LigneCommande").getResultList();
        } catch (Exception e) {
            throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
        }
        return lignesCommande;
    }

    @Override
    public void update(LigneCommande entity) throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            LigneCommande ligneCommande = read(id);
            if (ligneCommande != null) {
                session.delete(ligneCommande);
            }
            transaction.commit();
        } catch (Exception e) {
            throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
        }
    }

    public List<LigneCommande> getLigneCommandeByCommande(Commande commande) {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            
            List<LigneCommande> ligneCommandes = session.createQuery(
                "SELECT lc FROM T_LignesCommande lc WHERE lc.commande = :commande", LigneCommande.class)
                .setParameter("commande", commande)
                .getResultList();

            return ligneCommandes;
        } catch (Exception e) {
            // Gérer l'exception si nécessaire
            return null;
        }
    }

    
    public LigneCommande create1(LigneCommande ligneCommande) throws DAOException {
        try {
            Session session = HibernateConnection.getInstance().getSession();
            session.beginTransaction();
            session.save(ligneCommande);
            session.getTransaction().commit();
            return ligneCommande;
        } catch (Exception e) {
            throw new DAOException("Erreur : " + e.getClass() + ": " + e.getMessage());
        }
    }


}
