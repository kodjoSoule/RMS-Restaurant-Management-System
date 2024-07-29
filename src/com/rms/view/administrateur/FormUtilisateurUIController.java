package com.rms.view.administrateur;

import java.util.Optional;

import javax.swing.JTable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hibernate.dao.impl.AdministrateurHbnDaoImpl;
import com.hibernate.dao.impl.UtilisateurHbnDaoImpl;
import com.hibernate.factories.AdministrateurFactory;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.UtilisateurFactory;
import com.rms.RMSApplication;
import com.rms.dao.IDao;
import com.rms.exceptions.DAOException;
import com.rms.model.Administrateur;
import com.rms.model.ChefCuisinier;
import com.rms.model.Restaurateur;
import com.rms.model.Role;
import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;

import org.hibernate.Session;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class FormUtilisateurUIController {
	@FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private CheckBox showcheckBox ;
    private Stage dialogStage;
    private Utilisateur user;
	private boolean validerClicked; 
    ObservableList<Utilisateur> dataList = FXCollections.observableArrayList();
    IDao<Utilisateur> dataSource ;
    //AdministrateurHbnDaoImpl dataSource ;
    //
    public FormUtilisateurUIController() {
    }
    
    @FXML
    private void initialize(){
    	roleComboBox.getItems().clear();
    	roleComboBox.getItems().addAll(
    			Role.ADMINISTRATEUR.name(), 
    			Role.CHEF_CUISINIER.name(),
    			Role.RESTAURATEUR.name());
    	//dataSource = ConcreteFactory.getFactory(Utilisateur.class).getAdministrateurDao(AdministrateurHbnDaoImpl.class);
    	dataSource = ConcreteFactory.getFactory(UtilisateurFactory.class).getUtilisateurDao(UtilisateurHbnDaoImpl.class);
    	
    			
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.user = utilisateur;
        nomField.setText(utilisateur.getNomProperty().get());
        prenomField.setText(utilisateur.getPrenomProperty().get());
        emailField.setText(utilisateur.getEmailProperty().get());
        telephoneField.setText(utilisateur.getTelephoneProperty().get());
        loginField.setText(utilisateur.getLoginProperty().get());
        passwordField.setText(utilisateur.getPasswordProperty().get());
        roleComboBox.getSelectionModel().select(utilisateur.getRoleProperty().get());
    }
    @FXML
    private void handleValider() {
        if (isInputValid()) {
        	
            user.setNom(nomField.getText());
            user.setPrenom(prenomField.getText());
            user.setEmail(emailField.getText());
            user.setTelephone(telephoneField.getText());
            user.setLogin(loginField.getText());
            user.setPassword(passwordField.getText());
            user.setRole(roleComboBox.getSelectionModel().getSelectedItem());
            Role type = Role.valueOf(user.getRole());
            try {
            	dataSource.create(user);
				Utilitaire.showAlert("Ajout réussie", "Utilisateur\n ");
				RMSApplication.getInstance().showListUtilisateur();
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    }

    
    private boolean isInputValid() { // Validates the user input in the text fields.
        String errorMessage = "";

        if (nomField.getText() == null || nomField.getText().length() == 0) 
            errorMessage += "Le nom n'est pas renseigné !\n"; 

        if (prenomField.getText() == null || prenomField.getText().length() == 0) { 
            errorMessage += "Le prenom n'est pas renseigné !\n"; 
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) { 
            errorMessage += "L'email n'est pas renseigné !\n"; 
        }
        if (telephoneField.getText() == null || telephoneField.getText().length() == 0) { 
            errorMessage += "L'email n'est pas renseigné !\n"; 
        }
        //roleComboBox.getSelectionModel().select(user.getRole().get());
        if (roleComboBox.getSelectionModel().getSelectedItem().isEmpty()) { 
            errorMessage += "Le role n'est pas renseigné !\n"; 
        }
        
        if (errorMessage.length() == 0) return true;
        else { // Show the error message.            
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Champs non renseignés et/ou invalides !");
            alert.setHeaderText("Veuillez remplir tous les champs svp !");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            return false;
        }
    }
    @FXML // Called when the user clicks Annuler.
    private void handleAnnuler() {
    	RMSApplication.getInstance().showListUtilisateur();
    }
    public boolean isValiderClicked() {		
		return validerClicked;
	}

 }