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
import com.hibernate.dao.impl.ProduitHbnDaoImpl;
import com.hibernate.factories.CategorieFactory;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.ProduitFactory;
import com.rms.RMSApplication;
import com.rms.dao.IDao;
import com.rms.exceptions.DAOException;
import com.rms.model.Categorie;
import com.rms.model.Produit;
import com.rms.model.Role;
import com.rms.model.TypeCategorie;
import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;




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

public class FormProduitUIController {
	
	 	@FXML
	    private TextField idField;
	    @FXML
	    private TextField intituleField;
	    @FXML
	    private TextField prixField;
	    @FXML
	    private TextField quantiteField;
	    @FXML
	    private TextField descriptionField;
	    // Autres champs de texte pour les autres propriétés du produit
	    @FXML
	    private Button enregistrerButton;
	    // Autres éléments d'interface utilisateur
	   @FXML
	   private ComboBox<Categorie> categorieComboBox;
	   @FXML
	   private ImageView imageProduit;
    
    private Stage dialogStage;
    private Produit produit;
	private boolean validerClicked; 
	 private File imageFile;
    ObservableList<Produit> dataList = FXCollections.observableArrayList();
    ObservableList<Categorie> categorieList = FXCollections.observableArrayList();
    ProduitHbnDaoImpl produitDoa  ; 
    CategorieHbnDaoImpl categorieDoa  ; 
    
    
    
    //
    public FormProduitUIController() {
    }
    
    @FXML
    private void initialize(){
    	//categorieComboBox.getItems().clear();
//    	categorieComboBox.getItems().addAll(
//    			TypeCategorie.values());
    	
    	//categorieComboBox.getItems().setAll(categorieList);
    	
    	produitDoa = ConcreteFactory.getFactory(ProduitFactory.class).getProduitDao(ProduitHbnDaoImpl.class);
    	categorieDoa = ConcreteFactory.getFactory(CategorieFactory.class).getCategorieDao(CategorieHbnDaoImpl.class);
    	loadCategorie();
    }
    
    //
    private void loadCategorie() {
        // Récupérer tous les produits depuis la base de données
        	List<Categorie> categories = null;
        try {
        	categories = categorieDoa.list();
            
        } catch (DAOException e) {
            e.printStackTrace();
            // Gérer l'exception si nécessaire
        }
//        // Vérifier si la liste de produits n'est pas vide
        if (categories != null && !categories.isEmpty()) {
            // Ajouter les produits au ComboBox
        	categorieComboBox.getItems().clear();
        	//produitComboBox.getItems().setItems(FXCollections.observableArrayList(produits.to));
        	categorieComboBox.getItems().addAll(FXCollections.observableArrayList(categories));
        	categorieComboBox.getSelectionModel().selectFirst();
        	
        }
    }
  
    //
    public void setProduit(Produit produit) {
        if (produit != null) {
    	this.produit = produit;
        //idField.setText(String.valueOf(produit.getIdProperty().get()));
        intituleField.setText(produit.getIntituleProperty().get());
        prixField.setText(String.valueOf(produit.getPrixProperty().get()));
        descriptionField.setText(produit.getDescriptionProperty().get());
        quantiteField.setText(produit.getQuantite()+"");
        categorieComboBox.setValue(produit.getCategorie());
        imageProduit.setImage(convertByteArrayToImage(produit.getImage()));
        }
    }
    private byte[] convertImageToByteArray(Image image) {
    	
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //
    public Image convertByteArrayToImage(byte[] byteArray) {
    	if (byteArray == null) {
            return null;
        }
    	try {
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            return new Image(bais);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    private void handleValider() {
    	
        if (isInputValid()) {
            produit.setIntitule(intituleField.getText());
            produit.setPrix(Double.parseDouble(prixField.getText()));
            produit.setDescription(descriptionField.getText());
            produit.setImage(convertImageToByteArray(imageProduit.getImage()));
            produit.setCategorie(categorieComboBox.getValue());
            produit.setQuantite(Integer.parseInt(quantiteField.getText()));
            
            try {
				produitDoa.create(produit);
				Utilitaire.showAlert("Produit ajouté avec succès", "Produit Ajoutée");
				RMSApplication.getInstance().showListProduitsChefCuisinierUI();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		updateTableView();
//    		addSearchListener();
        }
    }
    @FXML
	private void handleOnActionNouveau() {
		Utilisateur utilisateur = new Utilisateur() ;
    	boolean validerClicked = RMSApplication.getInstance().showFormUtilisateurUI(utilisateur);
    	if (validerClicked) {
    		Utilitaire.showAlert(null,utilisateur.toString());
    	}
	}
    @FXML
    private void handleOnActionChoisirImage() {
    	// Ouvrir une boîte de dialogue pour permettre à l'utilisateur de sélectionner une image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = new Stage();
        imageFile = fileChooser.showOpenDialog(stage);

        if (imageFile != null) {
            try {
                // Afficher l'image sélectionnée dans l'ImageView
                Image image = new Image(new FileInputStream(imageFile));
                imageProduit.setImage(image);
            } catch (FileNotFoundException e) {
                // Gérer l'exception en cas d'erreur de chargement d'image
                e.printStackTrace();
            }
        }
    }
    private boolean isInputValid() {
        String errorMessage = "";
        if (intituleField.getText() == null || intituleField.getText().isEmpty()) {
            errorMessage += "La désignation n'est pas renseignée !\n";
        }
        if (prixField.getText() == null || prixField.getText().isEmpty()) {
            errorMessage += "Le prix n'est pas renseigné !\n";
        }
        if(descriptionField.getText() == null || descriptionField.getText().isEmpty()) {
        	errorMessage += "La description n'est pas renseignée!\n";
        }
        if (descriptionField.getText() == null || descriptionField.getText().isEmpty()) {
            errorMessage += "La description n'est pas renseignée !\n";
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
    @FXML 
    private void handleAnnuler() {
    	RMSApplication.getInstance().showListProduitsChefCuisinierUI();
    }
    public boolean isValiderClicked() {		
		return validerClicked;
	}

 }