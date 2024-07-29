package com.rms.view.restauranteur;

import com.rms.model.Produit;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

public class ModifierQuantiteProduitUIController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label nomProduitLabel;
    
    @FXML
    private Label dateCommandeLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private TextField quantiteField;

    @FXML
    private Label prixLabel;

    
    @FXML
    private Button validerButton;

    @FXML
    private Button annulerButton;

    private Stage dialogStage;
    private boolean validerClicked;
    
    Produit produit ;
    double total = 0 ;
    @FXML
    private void initialize() {
        // Initialisations ici si nécessaire
    	
//    	nomProduitLabel = produit.getIntitule();
//        prixLabel = produit.getPrix();
//        quantiteField.setText(String.valueOf(produit.getQuantite()));
        
        
    }
    
    public void calculTotal() {
    	this.total = produit.getPrix() * produit.getQuantite();
    	this.totalLabel.setText(String.valueOf(this.total));
    }
    @FXML
    private void handleValider() {
        if (isInputValid()) {
            // Effectuer les actions nécessaires pour valider le paiement
        	produit.setQuantite(Integer.valueOf(quantiteField.getText()));
            validerClicked = true;
            dialogStage.close();
        }
        // Afficher une alerte ou un message approprié
    }

    @FXML
    private void handleAnnuler() {
        validerClicked = false;
        dialogStage.close();
    }

    private boolean isInputValid() {
        
    	String errorMessage = "";
    	if( quantiteField.getText() == null || Integer.valueOf(quantiteField.getText()) < 1 ) {
            errorMessage += "La quantité doit etre supérieure à 1. \n";
        }
        if (errorMessage.isEmpty()) {
            return true;
        }else {
           Alert alert = new Alert(AlertType.ERROR);
           alert.initOwner(dialogStage);
           alert.setTitle("Champs non renseignés et/ou invalides !");
           alert.setHeaderText("Veuillez remplir tous les champs correctement svp !");
           alert.setContentText(errorMessage);

           alert.showAndWait();
            return false;
       }

    }

    // Méthode pour récupérer le montant à rendre
    private double getMontantRendu() {
        // Calculer et retourner le montant à rendre en fonction du montant reçu et du montant total
        return 0.0;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isValiderClicked() {
        return validerClicked;
    }

	public void setProduit(Produit produit) {
		this.produit = produit ;
		this.nomProduitLabel.setText(produit.getIntitule());
		
		 
		this.prixLabel.setText(produit.getPrix()+"");
		this.quantiteField.setText(String.valueOf(produit.getQuantite()));
		this.totalLabel.setText((produit.getTotal())*(produit.getQuantite())+"");
	}
}
