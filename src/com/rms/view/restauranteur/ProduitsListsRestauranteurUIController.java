
package com.rms.view.restauranteur;






import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.hibernate.dao.impl.LigneCommandeHbnDaoImpl;
import com.hibernate.dao.impl.ProduitHbnDaoImpl;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.LigneCommandeFactory;
import com.hibernate.factories.ProduitFactory;
import com.rms.RMSApplication;
import com.rms.exceptions.DAOException;
import com.rms.model.Commande;
import com.rms.model.LigneCommande;
import com.rms.model.Produit;

import com.rms.utilitaire.Utilitaire;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProduitsListsRestauranteurUIController {
	
	public Commande getSelectedCommande() {
		return selectedCommande;
	}
	public void setSelectedCommande(Commande selectedCommande) {
		this.selectedCommande = selectedCommande;
	}
	@FXML
	private TableView<Produit> tableView;
	@FXML
	private TableColumn<Produit, String> idcolumn ;
	@FXML
	private TableColumn<Produit, String> intitulecolumn ;
	@FXML
	private TableColumn<Produit, String> prixcolumn ;
	@FXML
	private TableColumn<Produit, String> descriptioncolumn ;
	@FXML
	private TableColumn<Produit, String> categoriecolumn;
	
	
//	@FXML
//	private TableColumn<Produit, String> quantitecolumn ;
	
	@FXML
	TableColumn<Produit, String> localDatecolumn ;
	@FXML
	private Label totalLabel ;
	@FXML
    private TextField searchTextField;
	@FXML
	private ImageView imageProduit;
	Produit selectedProduit;
	Commande selectedCommande ;
	LigneCommande ligneCommande;
	LigneCommandeHbnDaoImpl ligneCommandeDao ;
	private ProduitHbnDaoImpl dataSource;
	ObservableList<Produit> dataList = FXCollections.observableArrayList();
	
	
	
	//constructeur
	public ProduitsListsRestauranteurUIController() {
		
	}
	@FXML
	private void initialize() {
		idcolumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
		intitulecolumn.setCellValueFactory(cellData -> cellData.getValue().getIntituleProperty());
		prixcolumn.setCellValueFactory(cellData -> cellData.getValue().getPrixProperty());
		descriptioncolumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		categoriecolumn.setCellValueFactory(cellData -> cellData.getValue().getCategorie().getLibelleProperty());
		dataSource = ConcreteFactory.getFactory(ProduitFactory.class).getProduitDao(ProduitHbnDaoImpl.class);
    	ligneCommandeDao = ConcreteFactory.getFactory(LigneCommandeFactory.class).getLigneCommandeDao(LigneCommandeHbnDaoImpl.class);
		
		
		addChangeListener();
//    	cleanUserFromTableView();
    	try {
    		
			updateTableView();
			addSearchListener();
		} catch (DAOException e) {}

    	
    	
	}
	private void addChangeListener() {
    	tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null) {  
             this.selectedProduit = newValue;
             showSelectedProduitImage();
            } 
      	});
    }

	// Méthode pour afficher l'image du produit sélectionné dans l'ImageView
	private void showSelectedProduitImage() {
	    if (selectedProduit != null) {
	        byte[] imageData = selectedProduit.getImage();
	        if (imageData != null) {
	            Image image = new Image(new ByteArrayInputStream(imageData));
	            imageProduit.setImage(image);
	        } else {
	            // Si l'image est null, effacer l'ImageView
	        	imageProduit.setImage(null);
	        }
	    } else {
	    	imageProduit.setImage(null);
	    }
	}
	public void updateTableView() throws DAOException {
		if(dataSource == null) {
			Utilitaire.showAlert(null, "Oups");
			return ;
		}
		if (dataSource.list().size() > 0) {
          dataList.clear();
          dataList.addAll(dataSource.list());
          tableView.setItems(dataList);
          tableView.getSelectionModel().select(0);
            
        }
   }
	
	
	@FXML
    public void handleOnClickEdit() throws Exception {
    	 selectedProduit = tableView.getSelectionModel().getSelectedItem();
    	 if (selectedProduit != null) {
    		 boolean validerClicked = RMSApplication.getInstance().showFormProduitUI(selectedProduit);
    		 dataSource.update(selectedProduit);
    		 updateTableView();
    		 addSearchListener();
    		 cleanSearchField();
    		 tableView.getSelectionModel().select(selectedProduit);
    	  
    		 
    	 }else {
 	    	Utilitaire.showAlert(null, "Aucun elements selectionnee");
    	 }
 	    	
 	    	
 	    }
	@FXML
	private void handleOnClickCommander() {
		if (selectedProduit != null) {
			
			if(selectedCommande == null){
			selectedCommande = new Commande();
			}
			//selectedCommande.addProduit(selectedProduit);
			//ligneCommande = new LigneCommande();
			//ligneCommande.setProduit(selectedProduit);
			//ligneCommande.setCommande(selectedCommande);
			//selectedCommande.getLignesCommande().add(ligneCommande);
			boolean validerClicked = RMSApplication.getInstance().showFormCommandeUI(selectedCommande, selectedProduit, null);
		}else {
			Utilitaire.showAlert(null, "Aucun elements selectionnee");
		}
	}
	@FXML
    public void handleOnClickNouveau() throws Exception {
    	Produit produit = new Produit() ;
    	boolean validerClicked = RMSApplication.getInstance().showFormProduitUI(produit);
    }
	@FXML
    public void addSearchListener() throws DAOException {
    	updateTableView();
    	//1
    	FilteredList<Produit> filteredData = new FilteredList<>(dataList, p -> true);
    	//2
    	searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(produit -> {
    		if (newValue == null || newValue.isEmpty()) {
    			return true;
			}

    		String lowerCaseFilter = newValue.toLowerCase();
			if (produit.getIntituleProperty().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				return true; 
			} else if (produit.getDescriptionProperty().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				return true; 
			}
				return false; 
			});
		//3
    	SortedList<Produit> sortedData = new SortedList<>(filteredData);
    	//4
    	sortedData.comparatorProperty().bind(tableView.comparatorProperty());
    	//5
    	tableView.setItems(sortedData);
		
		});
	}
	public void handleOnClickDelete() throws Exception {
    	
    	if (selectedProduit != null) {
    		boolean confirmed = Utilitaire.showConfirmationDialog("Confirmation de suppression",
                    "Voulez-vous vraiment supprimer cet utilisateur ?");
    		if(confirmed) {
    			dataSource.delete(selectedProduit.getId());
    			
    			updateTableView();
    			cleanSearchField();
    			//addSearchListener();
    		}
    		
	    }else {
	    	Utilitaire.showAlert(null,"Aucun elements selectionne");
	    }
    	
    	
    }
	public void cleanSearchField() {
    	searchTextField.clear();
    }
	public void setSelectedProduit(Produit selectedProduit2) {
		// TODO Auto-generated method stub
		this.selectedProduit = selectedProduit2;
	}
	
}

