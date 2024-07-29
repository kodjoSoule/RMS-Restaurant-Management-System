package com.rms.view.restauranteur;

import com.rms.model.Commande;
import com.rms.model.Paiement;
import com.rms.utilitaire.Utilitaire;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormPaiementUIController {
    
    @FXML
    private ComboBox<Commande> commandeComboBox;
    
    @FXML
    private Label montantTotalLabel;
    
    @FXML
    private TextField montantRecuField;
    
    @FXML
    private Label montantARendreLabel;
    
    @FXML
    private void initialize() {
        // Initialiser le ComboBox et les autres éléments d'interface si nécessaire
    }
    
    private Stage dialogStage;
    private Paiement paiement;
    
    
    // Autres méthodes du contrôleur si nécessaire
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
        if (paiement != null) {
            commandeComboBox.setValue(paiement.getCommande());
            montantTotalLabel.setText(Double.toString(paiement.getCommande().getMontantTotal()));
            montantARendreLabel.setText(""); // Remplir le montant à rendre si besoin
        }
    }
    
    
    
    @FXML
    private void handleValider() {
        
            paiement.setCommande(commandeComboBox.getValue());
            paiement.setMontantPayer(Double.parseDouble(montantRecuField.getText()));
            // Effectuer les actions nécessaires pour enregistrer le paiement
            
            
            dialogStage.close();
        
    }
    
    @FXML
    private void handleAnnuler() {
    	
        dialogStage.close();
    }
    
    
}
