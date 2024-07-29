package com.rms.view.restauranteur;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.rms.model.Commande;
import com.rms.model.LigneCommande;
import com.rms.model.Mode_Paiement;
import com.rms.model.Paiement;
import com.rms.model.Produit;
import com.rms.model.Recette;
import com.rms.model.StatutCommande;
import com.rms.utilitaire.Utilitaire;

import jakarta.persistence.Tuple;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.hibernate.dao.impl.CommandeHbnDaoImpl;
import com.hibernate.dao.impl.LigneCommandeHbnDaoImpl;
import com.hibernate.dao.impl.PaiementHbnDaoImpl;
import com.hibernate.dao.impl.ProduitHbnDaoImpl;
import com.hibernate.factories.CommandeFactory;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.LigneCommandeFactory;
import com.hibernate.factories.PaiementFactory;
import com.hibernate.factories.ProduitFactory;
import com.rms.RMSApplication;
import com.rms.exceptions.DAOException;

public class CommandeListUIController {

    @FXML
    private TableView<Commande> commandeTableView;
    @FXML
    private TableColumn<Commande, String> idCommandeColumn;
    @FXML
    private TableColumn<Commande, String> nomClientColumn;
    @FXML
    private TableColumn<Commande, String> prenomClientColumn;
    @FXML
    private TableColumn<Commande, String> addresseClientColumn;
    @FXML
    private TableColumn<Commande, String> telephoneClientColumn;
    @FXML
    private TableColumn<Commande, String> emailClientColumn;
    
    @FXML
    private TableColumn<Commande, String> commandeClientColumn;

    @FXML
    private TableColumn<Commande, String> statusColumn;

    @FXML
    private TableColumn<Commande, String> dateColumn;
//    @FXML
//    private TableColumn<Commande, String> quantiteCommandeColumn;
    @FXML
    private TableColumn<Commande, Double> montantTotalColumn;

    //produit list
    @FXML
    private TableView<LigneCommande> produitsTableView;
    
    @FXML
    private TableColumn<LigneCommande, String> produitColumn;

    @FXML
    private TableColumn<LigneCommande, Double> prixColumn;
    //Stock
    @FXML
    private TableColumn<LigneCommande, Integer> quantiteColumn;
    
    //Quantite commandee
//    @FXML
//    private TableColumn<Produit, Integer> quantiteComColumn;
    @FXML
    private TableColumn<LigneCommande, Double> totalColumn;


    @FXML
    private Button ajouterProduitButton;

    @FXML
    private Button retirerProduitButton;

    @FXML
    private Button payerButton;
    
    CommandeHbnDaoImpl commandeDao ;
    ProduitHbnDaoImpl produitDao ;
    PaiementHbnDaoImpl paiementDao ;
    LigneCommandeHbnDaoImpl ligneCommandeDao ; 
    Commande selectedCommande ;
    Produit selectedProduit ;
    LigneCommande selectedLigneCommande ;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    @FXML
    private void initialize() {
    	 
    	commandeDao = ConcreteFactory.getFactory(CommandeFactory.class).getCommandeDao(CommandeHbnDaoImpl.class);
    	produitDao = ConcreteFactory.getFactory(ProduitFactory.class).getProduitDao(ProduitHbnDaoImpl.class);
    	paiementDao = ConcreteFactory.getFactory(PaiementFactory.class).getPaiementDao(PaiementHbnDaoImpl.class);
    	ligneCommandeDao = ConcreteFactory.getFactory(LigneCommandeFactory.class).getLigneCommandeDao(LigneCommandeHbnDaoImpl.class);
        
    	
    	idCommandeColumn.setCellValueFactory( cellData -> cellData.getValue().getIdProperty());
    	nomClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient().getNomProperty());
        prenomClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient().getPrenomProperty());
        addresseClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient().getAdresseProperty());
        telephoneClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient().getTelephoneProperty());
        emailClientColumn.setCellValueFactory(cellData -> cellData.getValue().getClient().getEmailProperty());
        
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalDateTime().format(formatter)));
        
        
        //montantTotalColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
        //par getMontantTotalColumn.setCellValueFactory(new PropertyValue)
        montantTotalColumn.setCellValueFactory(cellData -> {
            DoubleProperty property = new SimpleDoubleProperty(cellData.getValue().calculerMontantTotal());
            return property.asObject();
        });
        
        setColumnTableau();        
        loadCommandes();
        addListerner();        
    }
    public void setColumnTableau() {
    	
    	//idProduitColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    	produitColumn.setCellValueFactory(new PropertyValueFactory<>("produit")); // Il faudra ajuster en fonction de la propriété de Produit que vous voulez afficher
    	
    	prixColumn.setCellValueFactory(cellData -> {
    	    double prix = 0 ;
    	    prix = cellData.getValue().getProduit().getPrix();
    		return new SimpleDoubleProperty(prix).asObject();
    	 });
    	quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
    	totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    public void addListerner() {
    	//Définit le gestionnaire d'événements pour détecter la sélection d'une commande dans la TableView produitsTableView1
        commandeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Charge les produits de la commande sélectionnée dans la TableView produitsTableView
                //loadProduits1(newValue);
            	loadLigneCommandes(newValue);
                selectedCommande = newValue;
                
                
            } else {
                // Efface les données de la TableView produitsTableView si aucune commande n'est sélectionnée
                produitsTableView.getItems().clear();
            }
        });
    }
    private void loadLigneCommandes(Commande commande) {
        if (commande != null) {
            produitsTableView.getItems().clear();
            
            // Récupérer la liste des lignes de commande associées à la commande
            List<LigneCommande> ligneCommandes = ligneCommandeDao.getLigneCommandeByCommande(commande);
            
            // Remplir le tableau avec les lignes de commande
            produitsTableView.getItems().addAll(FXCollections.observableArrayList(ligneCommandes));
               
        
        } else {
            produitsTableView.getItems().clear();
        }
    }

      public void loadCommandes() {
        try {
            
            commandeTableView.getItems().clear();
            //commandeTableView.getItems().addAll(commandeDao.listAvecProduits());
            commandeTableView.getItems().addAll(commandeDao.list());
            
            
            // Appliquer le style en fonction du statut de paiement
            commandeTableView.setRowFactory(tableView -> new TableRow<Commande>() {
                @Override
                protected void updateItem(Commande ligne, boolean empty) {
                    super.updateItem(ligne, empty);

                    if (ligne == null || empty) {
                        setStyle("");
                    } else if (ligne.getStatus() == "Payer") { 
                        setStyle("-fx-background-color: green;");
                    } else {
                        setStyle("");
                    }
                }
            });
            

            
        } catch (DAOException e) {
        }
    }
    @FXML
    private void handleAjouterProduitButton() {
    	 if (selectedCommande == null) {
    		 Utilitaire.showAlert("Aucune commande n'est sélectionnée", "Erreur");	    		 
    	       return ;
    	 }
    	 //selectedCommande.ajouterProduit(selectedProduit, 1);
    	 loadCommandes();
    	 RMSApplication.getInstance().showListProduitsRestauranteur(selectedCommande);
    	 refreshTab();
    }
    
    @FXML
    private void handleRetirerProduitButton() {
    	Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();
        //Produit selectedProduit = produitsTableView.getSelectionModel().getSelectedItem();
        LigneCommande selectedProduit = produitsTableView.getSelectionModel().getSelectedItem();
    	
        if (selectedProduit == null) {
    		Utilitaire.showAlert("Erreur", "Aucune commande n'est sélectionenée");
    		return ;
    	}
        if (selectedCommande != null && selectedProduit != null) {
        	selectedCommande.removeLigneCommande(selectedProduit);
            selectedCommande.getProduits().remove(selectedProduit);
            
        	//loadProduits(selectedCommande);
        	loadLigneCommandes(selectedCommande);
            
            commandeTableView.getItems().clear();
            produitsTableView.getItems().clear();
            loadCommandes();
        }
       
    }
    
    @FXML
    private void handleRetirerCommande() {
    	
    	Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();
    	
        if (selectedCommande != null) {
        	boolean isConfirm = Utilitaire.showConfirmationDialog("Voulez-vous vraiment supprimer cette commande?", "Confirmer");
        	if(isConfirm) {
        	selectedCommande.getProduits().clear();
        	
        	selectedCommande.getLignesCommande().clear();
        	try {
				commandeDao.delete(selectedCommande.getId());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
            commandeTableView.getItems().clear();
            produitsTableView.getItems().clear();
            loadCommandes();
            
            
    	}else {
    		Utilitaire.showAlert("Aucune commande n'est sélectionnée", "ALert");
    	}
    }
    @FXML
    private void handlePayerButton() {
        Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();
        
        if (selectedCommande == null) {
            Utilitaire.showAlert("Erreur", "Aucune commande n'est sélectionnée");
            return;
        }
        if( selectedCommande.getStatus() == StatutCommande.Payer.name()) {
        	
        	Utilitaire.showAlert("Erreur", "Cette commande est déjà payée");
            return;
        }
        // Code pour afficher une boîte de dialogue ou un formulaire pour saisir les informations du paiement
        Paiement nouveauPaiement = new Paiement();
        nouveauPaiement.setCommande(selectedCommande);
        nouveauPaiement.setModePaiement(Mode_Paiement.Espece);
        nouveauPaiement.setDatePaiement(LocalDateTime.now());
        boolean validerClicked = RMSApplication.getInstance().showFormPaiementUI(nouveauPaiement);
        if (validerClicked) {
			 try {
				selectedCommande.setPaiement(nouveauPaiement);
				selectedCommande.setStatus(StatutCommande.Payer.name());
				
				paiementDao.create(nouveauPaiement);
				
				commandeDao.update(selectedCommande);
				//genererRecette(selectedCommande);
				Utilitaire.showAlert("Succès", "Paiement ajouté avec succès");
				commandeTableView.refresh();
				commandeTableView.getItems().clear();
	            produitsTableView.getItems().clear();
	            loadCommandes();
			} catch (DAOException e) {
				e.printStackTrace();
			}
		 }
    }
    
    @FXML
    private void handleModifierQuantiteButton() {
    	
        Commande selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();
        
        if (selectedCommande == null) {
            Utilitaire.showAlert("Erreur", "Aucune commande n'est sélectionnée");
            return;
        }
        if( selectedCommande.getStatus() == StatutCommande.Payer.name()) {
        	
        	Utilitaire.showAlert("Erreur", "Cette commande est déjà payée");
            return;
        }
        // Code pour afficher une boîte de dialogue ou un formulaire pour saisir les informations du paiement
        if (selectedCommande != null && selectedProduit != null) {
        	boolean validerClicked = RMSApplication.getInstance().showFomModifierQuantieUI(selectedProduit);
        	if (validerClicked) {
        		try {
					LigneCommande ligneCommande = ligneCommandeDao.getLigneCommandeByProduitAndCommande(selectedProduit, selectedCommande);
					ligneCommande.addQuantite(selectedProduit.getQuantite());
					ligneCommandeDao.update(ligneCommande);
					loadCommandes();
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}
        
        if (validerClicked) {
			 try {
				
				produitDao.update(selectedProduit);
				//genererRecette(selectedCommande);
		        
				Utilitaire.showAlert("Succès", "Paiement ajouté avec succès");
				commandeTableView.refresh();
			} catch (DAOException e) {
				e.printStackTrace();
			}
		 }
        return;
        }
    }
    
//    private void genererRecette(Commande commande) {
//        Recette recette = new Recette();
//        recette.setDateCreation(LocalDateTime.now()); // Date de création de la recette (actuelle)
//        double montantTotalRecette = 0.0;
//
//        for (Produit produitCommande : commande.getProduits()) {
////            recette.addProduitRecette(produitCommande);
////            montantTotalRecette += produitCommande.getTotal();
//        }
//
//        recette.setMontantTotal(montantTotalRecette);
//        // Enregistrer la recette dans la base de données ou tout autre moyen de stockage approprié
//        //recetteDao.create(recette);
//
//    }
	public void refreshTab() {
		// TODO Auto-generated method stub
		this.commandeTableView.refresh();
		this.produitsTableView.refresh();
	}
}
