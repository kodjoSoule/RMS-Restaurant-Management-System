package com.rms.view.restauranteur;

import com.hibernate.dao.impl.ProduitHbnDaoImpl;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.ProduitFactory;
import com.rms.RMSApplication;
import com.rms.exceptions.DAOException;
import com.rms.model.Commande;
import com.rms.model.Paiement;
import com.rms.model.Produit;
import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;

import javafx.fxml.FXML;

public class MainRestauranteurUIController {
	RMSApplication mainUI  ;
	ProduitHbnDaoImpl produitDao ;
	@FXML
	private void initialize() {
		this.mainUI = RMSApplication.getInstance();
        this.produitDao = ConcreteFactory.getFactory(ProduitFactory.class).getProduitDao(ProduitHbnDaoImpl.class);
	}
	@FXML
	private void handleOnActionNouveau() {
		boolean validerClicked = RMSApplication.getInstance().showFormProduitUI(new Produit());
    	
	}
	
	@FXML void handleOnActionHome() {
		RMSApplication.getInstance().showHomeRestaurateurUI();
	}
	@FXML void handleOnActionSuiviRecette() {
		RMSApplication.getInstance().showSuiviRecetteUI();
	}
	@FXML void handleOnActionPrendCommande() {
		Commande commande = new Commande() ;
		try {
			commande.setProduits(produitDao.list());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RMSApplication.getInstance().showFormCommandeUI(commande, null, null);
	}
	@FXML void handleOnActionListCommande() {
		RMSApplication.getInstance().showListCommandeRestauranteurUI();
	}
	@FXML void handleOnActionPrendPaiement() {
		//RMSApplication.getInstance().showFormPaiementUI(new Paiement());
	}
	
	@FXML void handleOnActionListPaiement() {
		RMSApplication.getInstance().showListPaiementUI();
	}
	@FXML void handleOnActionDeconnexion() {
			boolean confirmer = Utilitaire.showConfirmationDialog("Confirmation", "Voulez vous vraiment  vous déconnecter?");
		   if(confirmer) { 
			   RMSApplication.getInstance().showLoginUI();
		   }
	}
	@FXML void handleOnActionListProduits() {
		RMSApplication.getInstance().showListProduitsRestauranteur(null);
	}
}
