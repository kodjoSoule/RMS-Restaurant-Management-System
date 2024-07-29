package com.rms;

import com.rms.model.Categorie;


import com.rms.model.Commande;
import com.rms.model.LigneCommande;
import com.rms.model.Paiement;
import com.rms.model.Produit;

import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;
import com.rms.view.administrateur.FormUtilisateurUIController;
import com.rms.view.authentification.LoginUIController;
import com.rms.view.chefcuisinier.FormCategroieUIController;
import com.rms.view.chefcuisinier.FormProduitUIController;
import com.rms.view.restauranteur.CommandeListUIController;
import com.rms.view.restauranteur.FormCommandeUIController;
import com.rms.view.restauranteur.ModifierQuantiteProduitUIController;
import com.rms.view.restauranteur.PaiementListUIController;
import com.rms.view.restauranteur.PaiementUIController;
import com.rms.view.restauranteur.ProduitsListsRestauranteurUIController;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

//
//enum TypeOperation{
//	update,
//	create
//}
public class RMSApplication extends Application {
	
	
	private Stage primaryStage;
	private BorderPane mainAdministrateurUI;
    //for the restaurateur
    private BorderPane mainRestauranteurUI;
    //for chefCuisinier
    private BorderPane mainChefCuisinierUI ;
    
    private BorderPane loginUI ;
    private AnchorPane userUI;
	private AnchorPane formUtilisateurUI ;
	private AnchorPane ListProduitsChefCuisinier ;
	private AnchorPane FormProduitUI;
	
	//
	private AnchorPane listProduitsRestaurateur ;
	
	private AnchorPane homeAdministrateurUI ;
	private AnchorPane homeRestauranteurUI ;
	private AnchorPane homeChefCuisinierUI ;
    //
    private static RMSApplication instance = null ;
    //Utilisateur
    Utilisateur utilisateur ;
   
     
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestion de restaurants");
        instance = this ;
        
        Utilisateur.genererAdminUser();
        showLoginUI();
        
        //initRootLayout();
//        showUserUI();
        //For administration
///        initRootLayoutAdministrateur();
//        showHomeAdministrateurUI();
        
        //ChefCuisinier
        //iniRootLayoutChefCuisinier();
        //showHomeChefCuisinierUI();
        
        //Restaurateur
        //initRootLayoutRestaurateur();
        //showHomeRestaurateurUI();
            
        
	}
	
	public void showHomeChefCuisinierUI() {
		try {
        	// Load userUI
    		homeChefCuisinierUI = (AnchorPane) FXMLLoader.load(getClass().getResource("view/chefcuisinier/HomeChefCuisinierUI.fxml"));
        	// Set userUI into the center of root layout.
        	mainChefCuisinierUI.setCenter(homeChefCuisinierUI);
        } catch (IOException e) {
        	e.printStackTrace();
        }
		
	}
	public void iniRootLayoutChefCuisinier() {
		// TODO Auto-generated method stub
				try {
		        	// Load root layout from fxml file.
					mainChefCuisinierUI = (BorderPane) FXMLLoader.load(getClass().getResource("view/chefcuisinier/MainChefCuisinierUI.fxml"));
		        	
		        	// Show the scene containing the root layout.
		        	Scene scene = new Scene(mainChefCuisinierUI);
		        	scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
		        	primaryStage.setScene(scene);
		        	primaryStage.show();

		        } catch (IOException e) { 
		        	e.printStackTrace(); 
		        }
	}
	//Administration
	// Initializes the root layout.
    public void initRootLayoutAdministrateur() {
        try {
        	// Load root layout from fxml file.
        	mainAdministrateurUI = (BorderPane) FXMLLoader.load(getClass().getResource("view/administrateur/MainAdministrateurUI.fxml"));
        	
        	// Show the scene containing the root layout.
        	Scene scene = new Scene(mainAdministrateurUI);
        	scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        	primaryStage.setScene(scene);
        	primaryStage.show();

        } catch (IOException e) { 
        	e.printStackTrace(); 
        }
    }
    //Administration
    public void showHomeAdministrateurUI() {
    	try {
        	// Load userUI
    		homeAdministrateurUI = (AnchorPane) FXMLLoader.load(getClass().getResource("view/administrateur/HomeAdministrateurUI.fxml"));
        	//
        	// Set userUI into the center of root layout.
        	mainAdministrateurUI.setCenter(homeAdministrateurUI);
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    //
    
    @FXML
	public boolean showFormUtilisateurUI(Utilisateur utilisateur) {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("./view/administrateur/FormUtilisateurUI.fxml"));
        AnchorPane ListUtilisateurUI;
		try {
			ListUtilisateurUI = (AnchorPane) loader.load();
			//Set the user into the controller.
			FormUtilisateurUIController controller = loader.getController();
		    controller.setUtilisateur(utilisateur);

			mainAdministrateurUI.setCenter(ListUtilisateurUI);
			return controller.isValiderClicked();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
    }
    // Shows the login UI inside the root layout.
    public void showLoginUI() {
        try { 
            
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RMSApplication.class.getResource("view/authentification/loginUI.fxml"));
        	loginUI = (BorderPane) loader.load();
            LoginUIController controller = loader.getController();
            controller.setApplication(this);
        	
        	Scene scene = new Scene(loginUI);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
	public void showListUtilisateur(){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("./view/administrateur/UtilisateurListUI.fxml"));
        AnchorPane ListUtilisateurUI;
		try {
			ListUtilisateurUI = (AnchorPane) loader.load();
			mainAdministrateurUI.setCenter(ListUtilisateurUI);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

    // Returns the main stage.
    public Stage getPrimaryStage() {
    	return primaryStage;
    }


//
	public static RMSApplication getInstance() {
		
		return instance ;
	}

//
	public void closeAuthentificationUI() {
		 //primaryStage.close();
		 loginUI.getScene().getWindow().hide();
	}
	public void initRootLayoutRestaurateur() {
		// TODO Auto-generated method stub
		try {
        	// Load root layout from fxml file.
			mainRestauranteurUI = (BorderPane) FXMLLoader.load(getClass().getResource("view/restauranteur/MainRestaurateurUI.fxml"));
        	
        	// Show the scene containing the root layout.
        	Scene scene = new Scene(mainRestauranteurUI);
        	scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        	primaryStage.setScene(scene);
        	primaryStage.show();

        } catch (IOException e) { 
        	e.printStackTrace(); 
        }

	}
	
	public void showListProduitsChefCuisinierUI() {
		try {
        	// Load userUI
    		ListProduitsChefCuisinier = (AnchorPane) FXMLLoader.load(getClass().getResource("view/chefcuisinier/ProduitsListUI.fxml"));
        	//
        	// Set userUI into the center of root layout.
        	mainChefCuisinierUI.setCenter(ListProduitsChefCuisinier);
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	public boolean showFormProduitUI(Produit produit) {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("view/chefcuisinier/FormProduitUI.fxml"));
        
        AnchorPane formProduit ;
		try {
			formProduit = (AnchorPane) loader.load();
			//Set the user into the controller.
			FormProduitUIController controller = loader.getController();
		    controller.setProduit(produit);

			mainChefCuisinierUI.setCenter(formProduit);
//			return controller.isValiderClicked();
			return true ;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
			
		}
	}
	//Panel Restauranteur
	
		public void showHomeRestaurateurUI() {
			try {
	        	// Load userUI
	    		homeRestauranteurUI = (AnchorPane) FXMLLoader.load(getClass().getResource("view/restauranteur/HomeRestaurateurUI.fxml"));
	        	//
	        	// Set userUI into the center of root layout.
	    		mainRestauranteurUI.setCenter(homeRestauranteurUI);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
	public void showListProduitsRestauranteur(Commande commnade) {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("view/restauranteur/ProduitsListRestauranteurUI.fxml"));
        AnchorPane listProduitsRestaurateur ; 
        try {
        	// Load userUI
			//listProduitsRestaurateur = (AnchorPane) FXMLLoader.load(getClass().getResource("view/restauranteur/ProduitsListRestauranteurUI.fxml"));
        	listProduitsRestaurateur = (AnchorPane) loader.load();
        	ProduitsListsRestauranteurUIController controller = loader.getController();
		    controller.setSelectedCommande(commnade);
		    // Set userUI into the center of root layout.
			mainRestauranteurUI.setCenter(listProduitsRestaurateur);
        } catch (IOException e) {
        	e.printStackTrace();
        }
		
	}
	public boolean showFormCategorieUI(Categorie categorie) {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("view/chefcuisinier/FormCategorieUI.fxml"));
        
        AnchorPane formCategorie ;
		try {
			formCategorie = (AnchorPane) loader.load();
			//Set the user into the controller.
			FormCategroieUIController controller = loader.getController();
		    controller.setCategorie(categorie);
			mainChefCuisinierUI.setCenter(formCategorie);
			return controller.isValiderClicked();
//			return true ;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
			
		}
	}
	public void showListCategoriesRestauranteurUI() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean showFormCommandeUI(Commande commande, Produit produit, LigneCommande ligneCommande) {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("view/restauranteur/FormCommandeUI.fxml"));
        AnchorPane formCommande;
		try {
			formCommande = (AnchorPane) loader.load();
			
			FormCommandeUIController controller = loader.getController();
		    //controller.setSelectedProduit(produit);
			//controller.setCommande(commande);
			
		    controller.setCommandeProduit(commande, produit, null);
		    //controller.setLigneCommande(ligneCommande);
			mainRestauranteurUI.setCenter(formCommande);
			return controller.isValiderClicked();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
			
		}
	}
	public void showListCommandeRestauranteurUI() {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("view/restauranteur/CommandeListUI.fxml"));
        AnchorPane commandeList;
		try {
			commandeList = (AnchorPane) loader.load();
			//Set the user into the controller.
			CommandeListUIController controller = loader.getController();
			controller.loadCommandes();
		    mainRestauranteurUI.setCenter(commandeList);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
	}
	public boolean showFormPaiementUI(Paiement paiement) {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("view/restauranteur/PaiementUI.fxml"));
        AnchorPane formPaiement;
		try {
			formPaiement = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
            dialogStage.setTitle("Paiement");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(formPaiement);
            dialogStage.setScene(scene);
			PaiementUIController controller = loader.getController();
            controller.setPaiement(paiement);
            controller.setDialogStage(dialogStage);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isValiderClicked();
            
		} catch (IOException e) {
			e.printStackTrace();
			return false;
			
		}
	}
	//
	public boolean showFomModifierQuantieUI(Produit produit) {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("view/restauranteur/ModifierQuantiteProduitUI.fxml"));
        AnchorPane formPaiement;
		try {
			formPaiement = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
            dialogStage.setTitle("Paiement");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(formPaiement);
            dialogStage.setScene(scene);
			ModifierQuantiteProduitUIController controller = loader.getController();
            controller.setProduit(produit);
            controller.setDialogStage(dialogStage);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isValiderClicked();
            
		} catch (IOException e) {
			e.printStackTrace();
			return false;
			
		}
	}

	
	public void showListPaiementUI() {
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RMSApplication.class.getResource("view/restauranteur/PaiementListUI.fxml"));
        AnchorPane formPaiement;
		try {
			formPaiement = (AnchorPane) loader.load();
			//Set the user into the controller.
			PaiementListUIController controller = loader.getController();

			mainRestauranteurUI.setCenter(formPaiement);
						
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
		public void setAuthenticatedUser(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setUser(Utilisateur utilisateur2) {
		// TODO Auto-generated method stub
		this.utilisateur = utilisateur2;
	}
	public Utilisateur getUser() {
        // TODO Auto-generated method stub
        return this.utilisateur;
    }

	public void disconnect() {
		boolean confirmer = Utilitaire.showConfirmationDialog("Voulez vous vraiment  vous déconnecter?","Confirmation");
		   if(confirmer) { 
			   RMSApplication.getInstance().showLoginUI();
		   }

	}

	public void Quitter() {
		// TODO Auto-generated method stub
		boolean isCancel = Utilitaire.showConfirmationDialog("Voulez-vous vraiment quitter l'application?", "Quitter Application");
		if(isCancel) {
			System.exit(0);
		}
	}


}