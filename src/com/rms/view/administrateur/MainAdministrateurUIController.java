package com.rms.view.administrateur;

import com.rms.RMSApplication;
import com.rms.model.Produit;
import com.rms.model.Role;
import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;

import javafx.fxml.FXML;

public class MainAdministrateurUIController {
	RMSApplication mainUI  ;
	@FXML
	private void initialize() {
		this.mainUI = RMSApplication.getInstance();
		
	}
	@FXML
	private void handleOnActionNouveau() {
		Utilisateur utilisateur = new Utilisateur() ;
		boolean validerClicked = RMSApplication.getInstance().showFormUtilisateurUI(utilisateur);
	}
	@FXML
	private void handleOnActionListUtilisateurs() {
		System.out.println("On click list utilisateurs");
		RMSApplication.getInstance().showListUtilisateur();
	}
	@FXML void handleOnActionHome() {
		System.out.println("dddd");
		RMSApplication.getInstance().showHomeAdministrateurUI();
	}
	@FXML void handleOnActionDeconnexion() {
		boolean confirmer = Utilitaire.showConfirmationDialog("Confirmation", "Voulez vous vraiment  vous déconnecter?");
	   if(confirmer) { 
		   RMSApplication.getInstance().showLoginUI();
	 }
	   }
	
	@FXML
	public void quitter(){
		boolean isCancel = Utilitaire.showConfirmationDialog("Quitter Application", "Voulez-vous vraiment quitter l'application?");
		if(isCancel) {
			System.exit(0);
		}
	}
}
