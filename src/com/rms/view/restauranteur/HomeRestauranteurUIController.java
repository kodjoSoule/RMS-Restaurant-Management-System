package com.rms.view.restauranteur;


import com.rms.RMSApplication;
import com.rms.model.Commande;
import com.rms.model.Paiement;
import com.rms.utilitaire.Utilitaire;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeRestauranteurUIController {
	@FXML
	private Label labelUtilisateur ;
	RMSApplication mainUI  ;
	
	
	@FXML
	private void initialize() {
		this.mainUI = RMSApplication.getInstance();
		if(RMSApplication.getInstance().getUtilisateur()!= null)
		this.labelUtilisateur.setText(RMSApplication.getInstance().getUtilisateur().getNomComplet());
	}
	
	@FXML
	private void handleOnActionCommande() {
		System.out.println("Click on action on ");
		
		Commande commande = new Commande();
		
		boolean validerClicked =  RMSApplication.getInstance().showFormCommandeUI(commande, null, null);
		
		if (validerClicked) {
    		Utilitaire.showAlert(null, commande.toString());
    	}
	}
	@FXML
	public void showListProduitsChefCuisinierUI() {
		
		
		RMSApplication.getInstance().showListProduitsRestauranteur(null);
	}
	@FXML public void showListPaiementUI() {
		
        RMSApplication.getInstance().showListPaiementUI();;
	}
	
	@FXML
	private void handleOnActionPaiement() {
		
		Paiement paiement = new Paiement();
		
		RMSApplication.getInstance().showFormPaiementUI(paiement);
		
//		if (validerClicked) {
//    		Utilitaires.showAlert(null, paiement.toString());
//    	}
	}
	@FXML void handleOnActionHome() {
	
		RMSApplication.getInstance().showHomeRestaurateurUI();
	}
	@FXML void handleOnActionDisconnect() {
		RMSApplication.getInstance().disconnect();
	}
	@FXML void handleOnActionQuitter() {
		RMSApplication.getInstance().Quitter();
	}
	@FXML void handleOnActionListProduits() {
		RMSApplication.getInstance().showListProduitsChefCuisinierUI();
	}
}
