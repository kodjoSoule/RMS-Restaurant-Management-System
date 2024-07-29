package com.rms.view.restauranteur;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.hibernate.dao.impl.PaiementHbnDaoImpl;
import com.hibernate.dao.impl.ProduitHbnDaoImpl;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.PaiementFactory;
import com.hibernate.factories.ProduitFactory;
import com.rms.exceptions.DAOException;
import com.rms.model.Paiement;

import com.rms.utilitaire.Utilitaire;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PaiementUIController {

    @FXML
    private Label idCommandeLabel;
    @FXML
    private Label nomCompletClientLabel;
    @FXML
    private Label dateCommandeLabel;
    @FXML
    private Label datePaiementLabel;
    @FXML
    private Label totalLabel;

    @FXML
    private TextField montantRecueField;

    @FXML
    private Label montantARendreLabel;
    @FXML
    private Label erreurLabel;
    @FXML
    private DatePicker datePicker;

    @FXML
    private Button payerButton;

    @FXML
    private Button annulerButton;
    
    
    
    
    
    Paiement paiement ;
    private Stage dialogStage;
    private boolean validerClicked;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    @FXML
    private void initialize() {
    	datePaiementLabel.setText(LocalDateTime.now().format(formatter));
    }

    @FXML
    private void handlePayer() {
    	if (isInputValid()) {
        	paiement.setMontantRecue(Double.parseDouble( montantRecueField.getText()));
            validerClicked = true;
            
            dialogStage.close();
        }
        Utilitaire.showAlert("Payer, "+paiement.getMontantRecue(), "Votre paiement a bien été enregistrée");
    }
    @FXML
    private boolean isInputValid() { 
        String errorMessage = "";
        if( montantRecueField.getText() == null && montantRecueField.getText().equals("") ) {
             errorMessage += "Veuillez renseigner le montant à payer";
        }
        if (errorMessage.length() == 0) return true;
        else {            
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Champs non renseignés et/ou invalides !");
            alert.setHeaderText("Veuillez remplir tous les champs svp !");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
        
    @FXML
    private void handleAnnuler() {
    	validerClicked = false;
        dialogStage.close();
    }

	public Label getCommandeLabel() {
		return idCommandeLabel;
	}

	public void setCommandeLabel(Label commandeLabel) {
		this.idCommandeLabel = commandeLabel;
	}

	public Label getTotalLabel() {
		return totalLabel;
	}

	public void setTotalLabel(Label totalLabel) {
		this.totalLabel = totalLabel;
	}

	public TextField getMontantRecueField() {
		return montantRecueField;
	}

	public void setMontantRecueField(TextField montantRecueField) {
		this.montantRecueField = montantRecueField;
	}

	public Label getMontantARendreLabel() {
		return montantARendreLabel;
	}

	public void setMontantARendreLabel(Label montantARendreLabel) {
		this.montantARendreLabel = montantARendreLabel;
	}

	public Button getPayerButton() {
		return payerButton;
	}

	public void setPayerButton(Button payerButton) {
		this.payerButton = payerButton;
	}

	public Button getAnnulerButton() {
		return annulerButton;
	}

	public void setAnnulerButton(Button annulerButton) {
		this.annulerButton = annulerButton;
	}

	public Paiement getPaiement() {
		return paiement;
	}

	public void setPaiement(Paiement paiement) {
		this.paiement = paiement;
		this.idCommandeLabel.setText(paiement.getCommande().getId()+"");
//		this.montantARendreLabel.setText(String.valueOf(paiement.getMontantPayer()));
		this.totalLabel.setText(paiement.getCommande().getMontantTotal()+" FCFA");
		this.montantRecueField.setText(String.valueOf(paiement.getMontantRecue()));
		this.dateCommandeLabel.setText(paiement.getCommande().getLocalDateTime().format(formatter));
		this.nomCompletClientLabel.setText(paiement.getCommande().getClient().getNomComplet());
		//this.commandeLabel.setText(paiement.getCommande().getNom());
		this.payerButton.setDisable(true);
		//this.annulerButton.setDisable(true);
		addLister();
	}

	public void addLister() {
		 montantRecueField.textProperty().addListener((observable, oldValue, newValue) -> {
		        // Vérifier si le montantRecueField contient une valeur numérique valide
		        if (newValue.matches("\\d+(\\.\\d+)?")) {
		            double montantRecu = Double.parseDouble(newValue);
		            double montantPayer = paiement.getCommande().getMontantTotal();

		            // Vérifier si le montant reçu est supérieur ou égal au montant à payer
		            if (montantRecu >= montantPayer) {
		            	
		                payerButton.setDisable(false); // Activer le bouton "Payer"
		                montantARendreLabel.setText(getMontantRendue()+" FCFA");
		                erreurLabel.setText("Montant suffisant");
		                erreurLabel.setTextFill(Color.GREEN); // Afficher le label en vert
		            } else {
		                payerButton.setDisable(true); // Désactiver le bouton "Payer"
		                erreurLabel.setText("Montant insuffisant");
		                erreurLabel.setTextFill(Color.RED); // Afficher le label en rouge
		            }
		        } else {
		            // Si le montantRecueField ne contient pas une valeur numérique valide, désactiver le bouton "Payer"
		            payerButton.setDisable(true);
		            erreurLabel.setText("");
		        }
		    });
	}
	public double getMontantRendue() {
		double montantRendue;
		montantRendue =  Double.parseDouble( montantRecueField.getText()) - paiement.getMontantPayer();
		return montantRendue;
	}
	public void setDialogStage(Stage dialogStage) {
		// TODO Auto-generated method stub
		this.dialogStage = dialogStage;
	}
	
	public boolean isValiderClicked() {		
		return validerClicked;
	}
	
	
    	
	
}
