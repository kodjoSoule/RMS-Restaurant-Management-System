package com.rms.view.restauranteur;


import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

import com.hibernate.dao.impl.ClientHbnDaoImpl;
import com.hibernate.dao.impl.CommandeHbnDaoImpl;
import com.hibernate.dao.impl.LigneCommandeHbnDaoImpl;
import com.hibernate.dao.impl.ProduitHbnDaoImpl;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.LigneCommandeFactory;
import com.hibernate.factories.ProduitFactory;

import com.hibernate.factories.CommandeFactory;
import com.rms.RMSApplication;

import com.rms.exceptions.DAOException;

import com.rms.model.Client;
import com.rms.model.Commande;
import com.rms.model.LigneCommande;
import com.rms.model.Produit;


import com.rms.model.StatutCommande;

import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class FormCommandeUIController {
	 	
	    @FXML
	    private ComboBox<Produit> produitComboBox;
	    @FXML
	    private Label prixLabel ;
	    @FXML
	    private TextField quantiteField ;
	    @FXML private ComboBox<String> statutCombox ;
	    @FXML private TextField nomClientField ;
	    @FXML private TextField prenomClientField ;
	    @FXML private TextField emailClientField ;
	    @FXML private TextField telephoneClientField ;
	    @FXML private TextField adresseClientField ;
	    @FXML private DatePicker dateCommandeField ;
	    @FXML
	    private Button validerButton ;
	    @FXML
	    private Stage dialogStage;
        private Client client;
		private boolean validerClicked; 
	 
    ObservableList<Commande> dataList = FXCollections.observableArrayList();
    CommandeHbnDaoImpl commandeDao  ; 
    ClientHbnDaoImpl clientDao ; 
    ProduitHbnDaoImpl produitDao ;
    LigneCommandeHbnDaoImpl ligneCommandeDao ;
    
    //
    Produit produit ;
    private Commande commande;
    
	private LigneCommande ligneCommande; 
    public FormCommandeUIController() {
    }
    
    @FXML
    private void initialize(){
    	commandeDao = ConcreteFactory.getFactory(CommandeFactory.class).getCommandeDao(CommandeHbnDaoImpl.class);
    	produitDao = ConcreteFactory.getFactory(ProduitFactory.class).getProduitDao(ProduitHbnDaoImpl.class);
    	
    	ligneCommandeDao = ConcreteFactory.getFactory(LigneCommandeFactory.class).getLigneCommandeDao(LigneCommandeHbnDaoImpl.class);
    	
    	dateCommandeField.setValue(LocalDate.now());
    	clientDao = new ClientHbnDaoImpl() ;
    	//produit = produitComboBox.getValue() ;
    	produitComboBox.setValue(produit);
    	statutCombox.getItems().clear();
    	statutCombox.getItems().addAll(
    			StatutCommande.EN_COURS.name(),
    			StatutCommande.Payer.name()
    			);
    	 if (produit != null) {
    	        loadProduitInfo();
    	   }
    	//loadProduits();
    		
    	
    	 statutCombox.getSelectionModel().select(0);
    			
    }
    
    private void loadProduitInfo() {
    	produitComboBox.setItems(FXCollections.observableArrayList(produit));
        produitComboBox.getSelectionModel().select(0);
        produitComboBox.setEditable(false);
        prixLabel.setText(String.valueOf(produit.getPrix())+ " FCFA");
        if(ligneCommande != null)
        quantiteField.setText(String.valueOf(ligneCommande.getQuantite() ));
        
        quantiteField.setText("1");
    }
    
    public void setSelectedProduit(Produit selectedProduit) {
    	this.produit = selectedProduit;
    	 loadProduitInfo();
    }
    private void loadProduits() {
        	List<Produit> produits = null;
        try {
        	produits = produitDao.list();
        	if (produits != null && !produits.isEmpty()) {
        		produitComboBox.getItems().clear();
            	produitComboBox.setItems(FXCollections.observableArrayList(produits));
            	produitComboBox.getSelectionModel().select(0);
            	
            }
            
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    public void setCommande(Commande commande) {
        if (commande != null) {
        	this.commande = commande;
        }
    }
    public void setCommandeProduit(Commande commande, Produit produit, LigneCommande ligneCommande1) {
        if (commande != null && produit!= null) {
            this.commande = commande;
            this.produit = produit;
            //LigneCommande ligneCommande = new LigneCommande();
            produitComboBox.getItems().clear();
            produitComboBox.getItems().addAll(FXCollections.observableArrayList(produit));
            produitComboBox.getSelectionModel().select(0);
            produitComboBox.setEditable(false);
            prixLabel.setText(String.valueOf(produit.getPrix())+ " FCFA");
            //quantiteField.setText(String.valueOf(ligneCommande.getQuantite()));
            quantiteField.setText("1");
            
            setClient(commande.getClient());
            
            loadProduitInfo();
            
        }
    }
    
        
    public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		if(client != null) {
		this.client = client;
		this.nomClientField.setText(client.getNom());
		this.nomClientField.setEditable(false);
		this.prenomClientField.setText(client.getPrenom());
		this.prenomClientField.setEditable(false);
		this.adresseClientField.setText(client.getAdresse());
		this.adresseClientField.setEditable(false);
		this.telephoneClientField.setText(client.getTelephone());
		this.telephoneClientField.setEditable(false);
		this.emailClientField.setText(client.getEmail());
		this.emailClientField.setEditable(false);
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
        
    }    
    @FXML
    private void handleValider() {
        if (isInputValid()) {
            try {
                //produit.addQuantite(Integer.valueOf(quantiteField.getText()));
                //produit.getCommandes().add(commande);
                commande.addProduit(produit);
                
                if (client == null) {
                    client = new Client();
                    client.setNom(nomClientField.getText());
                    client.setPrenom(prenomClientField.getText());
                    client.setEmail(emailClientField.getText());
                    client.setAdresse(adresseClientField.getText());
                    client.setTelephone(telephoneClientField.getText());
                    
                    // Enregistrer le client dans la base de données
                    Client client1 = clientDao.create1(client);
                    commande.setClient(client1);
                    //commandeDao.update(commande);
                    // Mettre à jour la quantité en stock du produit
                    
                }
                //produitDao.update(produit);
                
                // Créer une nouvelle commande
                commande.setLocalDateTime(LocalDateTime.now());
                commande.setStatus(statutCombox.getValue());
                //commande.setQuantite(Integer.valueOf(quantiteField.getText()));
                commande.setRestauranteur(RMSApplication.getInstance().getUser());
                
                // Enregistrer la commande dans la base de données
                commande = commandeDao.create1(commande);
                
                // Créer et enregistrer la ligne de commande
                if(ligneCommande == null) {
                ligneCommande = new LigneCommande();
                
                ligneCommande.setCommande(commande);
                ligneCommande.setProduit(produit);
                }
                ligneCommande.addQuantite(Integer.valueOf(quantiteField.getText()));
                LigneCommande ligneCommande1 =ligneCommandeDao.create1(ligneCommande);
                commande.getLignesCommande().add(ligneCommande1);
                //commandeDao.update(commande);
                
                // Mettre à jour les associations
                produit.addCommande(commande);
                
                //ici
                //commande.getLignesCommande().add(ligneCommande);
                
                
                // Mettre à jour les quantités en stock du produit
                produitDao.update(produit);
                
                //commandeDao.update(commande);
                
                Utilitaire.showAlert("Commande enregistrée","Ajoutée avec succès!");
                RMSApplication.getInstance().showListCommandeRestauranteurUI();
                cleanFormCommande();

            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }

    
	private void handleValider2() {
        if (isInputValid()) {
            try {
            	produit.addQuantite(Integer.valueOf(quantiteField.getText()));
            	produit.getCommandes().add(commande);
            	commande.addProduit(produit);
                if (client == null) {
                client = new Client();
                client.setNom(nomClientField.getText());
                client.setPrenom(prenomClientField.getText());
                client.setEmail(emailClientField.getText());
                client.setAdresse(adresseClientField.getText());
                client.setTelephone(telephoneClientField.getText());
                
                Client client1= clientDao.create1(client);
              //Enregistrer le client dans la base de données
                produitDao.update(produit);
                commande.setClient(client1);
                }
                // Créer une nouvelle commande
                commande.setLocalDateTime(LocalDateTime.now());
                commande.setStatus(statutCombox.getValue());
                //commande.setQuantite(Integer.valueOf(quantiteField.getText()));
                commande.setRestauranteur(RMSApplication.getInstance().getUser());
                // Enregistrer la commande dans la base de données
                //Commande commande1 = commandeDao.create1(commande);
                
                // Lie la ligne de commande à la commande
//                if (ligneCommande == null)
//                    ligneCommande = new LigneCommande();

                
                //ligneCommande.setCommande(commande1);

                // Ajouter la ligne de commande à la commande
                if(ligneCommandeDao.doesLigneCommandeExist(produit, commande)) {
                	ligneCommande.setCommande(commande);
                    ligneCommande.setProduit(produit);
                    ligneCommande.addQuantite(Integer.valueOf(quantiteField.getText()));
                    ligneCommandeDao.update(ligneCommande);
                }else {
                	LigneCommande ligneCommande = new LigneCommande();
                	ligneCommande.setCommande(commande);
                    ligneCommande.setProduit(produit);
                    ligneCommande.addQuantite(Integer.valueOf(quantiteField.getText()));
                	ligneCommandeDao.create(ligneCommande);
                }
                
                
                //commande.getLignesCommande().add(ligneCommande);
                commande.addLignesCommande(ligneCommande);
                //commande.CalculMontantTotalCommande();

                commandeDao.update(commande);
                // Enregistrer la ligne de commande dans la base de données
                
                
                Utilitaire.showAlert(null, commande.toString());
                RMSApplication.getInstance().showListCommandeRestauranteurUI();
                cleanFormCommande();

            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }

    
    public void cleanFormCommande() {
    	nomClientField.clear();
        prenomClientField.clear();
        emailClientField.clear();
        adresseClientField.clear();
        telephoneClientField.clear();
        quantiteField.clear();
        dateCommandeField.setValue(null);
        statutCombox.getSelectionModel().select(0);
        produitComboBox.getSelectionModel().select(0);
    }
    private boolean isInputValid() {
        String errorMessage = "";
        if (produitComboBox.getValue() == null ) {
            errorMessage += "Le produit n'est pas renseignée !\n";
        }
        
        if(quantiteField.getText() == null || quantiteField.getText().isEmpty() ) {
        	errorMessage += "La quantite n'est pas renseignée!\n";
        }
        if(Integer.valueOf(quantiteField.getText()) < 1 ){
        	errorMessage += "La quantite doit etre supperieur a 0!\n";
        }
        
        if( dateCommandeField.getValue() == null ) {
            errorMessage += "La date de commande n'est pas renseignée!\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        } else {

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
    	RMSApplication.getInstance().showListProduitsRestauranteur(null);
    }
    public boolean isValiderClicked() {		
		return validerClicked;
	}

	public void setLigneCommande(LigneCommande ligneCommande) {
		// TODO Auto-generated method stub
		this.ligneCommande = ligneCommande;
	}

 }