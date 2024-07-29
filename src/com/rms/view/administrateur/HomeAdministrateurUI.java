package com.rms.view.administrateur;

import com.rms.RMSApplication;
import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeAdministrateurUI {
	@FXML
	private Label labelUtilisateur;
	
	@FXML
    private void initialize() {
        labelUtilisateur.setText(RMSApplication.getInstance().getUser().getNomComplet());
    }

    
	//add
	@FXML
    public void add(){
		Utilisateur utilisateur = new Utilisateur() ;
		RMSApplication.getInstance().showFormUtilisateurUI(utilisateur);
    }
	//list
	@FXML
    public void list(){
		RMSApplication.getInstance().showListUtilisateur();
    }
	@FXML
	public void disconnect() {
		boolean confirmer = Utilitaire.showConfirmationDialog("Confirmation", "Voulez vous vraiment  vous déconnecter?");
		   if(confirmer) { 
			   RMSApplication.getInstance().showLoginUI();
		   }

	}
	@FXML
	public void cancel(){
		boolean isCancel = Utilitaire.showConfirmationDialog("Quitter Application", "Voulez-vous vraiment quitter l'application?");
		if(isCancel) {
			System.exit(0);
		}
	}
}
