package com.rms.view.chefcuisinier;

import com.rms.RMSApplication;
import com.rms.model.Categorie;
import com.rms.model.Produit;
import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeChefCuisinierUIController {
	@FXML
	private Label labelUtilisateur;
	RMSApplication mainUI  ;
	@FXML
	private void initialize() {
		this.mainUI = RMSApplication.getInstance();
		labelUtilisateur.setText(RMSApplication.getInstance().getUser().getNomComplet());
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
	@FXML
	public void disconnect() {
		RMSApplication.getInstance().disconnect();

	}
	@FXML
	public void cancel(){
		RMSApplication.getInstance().Quitter();
	}

	@FXML void handleOnActionHome() {
	
		RMSApplication.getInstance().showHomeRestaurateurUI();
	}
	@FXML void handleOnActionListProduits() {
		RMSApplication.getInstance().showListProduitsChefCuisinierUI();
	}
}
