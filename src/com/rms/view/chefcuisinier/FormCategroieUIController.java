package com.rms.view.chefcuisinier;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JTable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hibernate.dao.impl.CategorieHbnDaoImpl;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.CategorieFactory;
import com.rms.RMSApplication;
import com.rms.dao.IDao;
import com.rms.exceptions.DAOException;
import com.rms.model.Categorie;
import com.rms.model.Categorie;
import com.rms.model.Role;
import com.rms.model.TypeCategorie;
import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;

import jakarta.persistence.Transient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class FormCategroieUIController {
	 	
	    @FXML
	    private TextField libelleField;
	    @FXML
	    private TextField descriptionField;
	    // Autres champs de texte pour les autres propriétés du produit
	    @FXML
	    private Button enregistrerButton;
	    @FXML
	    private TableView<Categorie> categorieTable;
	    @FXML
	    private TableColumn<Categorie, String> libelleColumn;
	    @FXML
	    private TableColumn<Categorie, String> descriptionColumn;
	    
    
    private Stage dialogStage;
    private Categorie categorie;
	private boolean validerClicked; 
	 
    ObservableList<Categorie> dataList = FXCollections.observableArrayList();
    CategorieHbnDaoImpl CategorieDao  ; 
    @Transient
    Categorie selectedCategorie ;
    @Transient
    @FXML
    private TextField searchTextField ;
    public FormCategroieUIController() {
    }
    
    @FXML
    private void initialize(){
    	libelleColumn.setCellValueFactory(cellData -> cellData.getValue().getLibelleProperty());
    	descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());

    	CategorieDao = ConcreteFactory.getFactory(CategorieFactory.class).getCategorieDao(CategorieHbnDaoImpl.class);
    	
    	try {
    		addChangeListener();
			updateTableView();
			addSearchListener();
		} catch (DAOException e) {}
    	

    }
    private void addChangeListener() {
    	categorieTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null) {  
             this.selectedCategorie = newValue;
             setCategorie(selectedCategorie);
                
            } 
      	});
    }
    
    public void updateTableView() throws DAOException{
    	if (CategorieDao.list().size() > 0) {
             dataList.clear();
             dataList.addAll(CategorieDao.list());
             categorieTable.setItems(dataList);
             categorieTable.getSelectionModel().select(0);
             setCategorie(categorie);
         }

    }
    

    public void setCategorie(Categorie categorie) {
        if (categorie != null) {
    	this.categorie = categorie;
        
        libelleField.setText(categorie.getLibelle());
        descriptionField.setText(categorie.getDescription());
        
        }
    }
    

    @FXML
    private void handleValider() {
    	
        if (isInputValid()) {
            categorie.setLibelle(libelleField.getText());
            categorie.setDescription(descriptionField.getText());
            try {
				CategorieDao.create(categorie);
				updateTableView();
				addSearchListener();
				Utilitaire.showAlert(null, categorie.toString());
				RMSApplication.getInstance().showFormCategorieUI(new Categorie());
				RMSApplication.getInstance().showListCategoriesRestauranteurUI();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		
//    		
        }
    }
    @FXML
    private void handleOnActionSupprimer() {
        if (selectedCategorie != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer cette catégorie ?");
            
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    CategorieDao.delete(selectedCategorie.getIdCategorie()); // Assuming you have a delete method in your DAO
                    updateTableView();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Utilitaire.showAlert(null, "Aucune catégorie sélectionnée !");
        }
    }

    @FXML
    public void addSearchListener() throws DAOException {
        updateTableView();

        FilteredList<Categorie> filteredData = new FilteredList<>(dataList, p -> true);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(categorie -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (categorie.getLibelle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (categorie.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                
                return false;
            });
        });

        SortedList<Categorie> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(categorieTable.comparatorProperty());

        categorieTable.setItems(sortedData);
    }

    @FXML
	private void handleOnActionNouveau() {
		Utilisateur utilisateur = new Utilisateur() ;
    	boolean validerClicked = RMSApplication.getInstance().showFormUtilisateurUI(utilisateur);
    	if (validerClicked) {
    		Utilitaire.showAlert(null,utilisateur.toString());
    		
    	}
    	try {
			updateTableView();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        private boolean isInputValid() {
        String errorMessage = "";
        if (libelleField.getText() == null || libelleField.getText().isEmpty()) {
            errorMessage += "La désignation n'est pas renseignée !\n";
        }
        
        if(descriptionField.getText() == null || descriptionField.getText().isEmpty()) {
        	errorMessage += "La description n'est pas renseignée!\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Afficher un message d'erreur pour les champs non remplis ou incorrects
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Champs non renseignés et/ou invalides !");
            alert.setHeaderText("Veuillez remplir tous les champs correctement svp !");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }
    @FXML // Called when the user clicks Annuler.
    private void handleAnnuler() {
    	RMSApplication.getInstance().showHomeRestaurateurUI();
    }
    public boolean isValiderClicked() {		
		return validerClicked;
	}

 }