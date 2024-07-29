package com.rms.view.chefcuisinier;


	


	import com.rms.RMSApplication;
import com.rms.model.Categorie;
import com.rms.model.Produit;
	import com.rms.model.Utilisateur;
	import com.rms.utilitaire.Utilitaire;

	import javafx.fxml.FXML;

	public class MainChefCuisinierUIController {
		RMSApplication mainUI  ;
		@FXML
		private void initialize() {
			this.mainUI = RMSApplication.getInstance();
		}
		@FXML
		private void handleOnActionNouveau() {
			boolean validerClicked = RMSApplication.getInstance().showFormProduitUI(new Produit());
		}
		
		@FXML void handleOnActionHome() {
			RMSApplication.getInstance().showHomeChefCuisinierUI();
		}
		@FXML
		private void handleOnActionNouveauProduit() {
			Produit produit = new Produit();
			
			boolean validerClicked =  RMSApplication.getInstance().showFormProduitUI(produit);
			
	    	if (validerClicked) {
	    		Utilitaire.showAlert(null, produit.toString());
	    	}
		}
		@FXML
		private void handleOnActionCategroie() {
			System.out.println("Click on action on ");
			Categorie categorie = new Categorie();
			boolean validerClicked =  RMSApplication.getInstance().showFormCategorieUI(categorie);
			if (validerClicked) {
	    		Utilitaire.showAlert(null, categorie.toString());
	    	}
		}
		@FXML void handleOnActionCreerCategorie() {
			//RMSApplication.getInstance().showFormCategorieChefCuisinierUI();
		}
		@FXML void handleOnActionListProduits() {
			RMSApplication.getInstance().showListProduitsChefCuisinierUI();
		}
		@FXML void handleOnActionDeconnexion() {
		RMSApplication.getInstance().disconnect();
		}
		@FXML
		void handleOnActionQuitter() {
			RMSApplication.getInstance().Quitter();
		}
	}


