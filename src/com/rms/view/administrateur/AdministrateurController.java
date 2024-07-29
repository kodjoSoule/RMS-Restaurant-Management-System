package com.rms.view.administrateur;

import java.util.Optional;

import com.rms.RMSApplication;
import com.rms.dao.IDao;
import com.rms.model.Utilisateur;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;

public class AdministrateurController {
	private String[] m_choices = {"UtilisateurDaoImpl", "ObjectFileUtilisateurDaoImpl" , "UtilisateurDataRamDaoImpl"};
//	List<String> m_choices = new ArrayList<>();
//	m_choices.add("Fichier");
	private String m_title = "A Choice Dialog";
    private String m_message = "What is your ?";
    private String valeurChoisie ;
    private static IDao<Utilisateur> currentDataSource ;
    private RMSApplication application;

	@FXML
	Menu fileMenu;
	@FXML
	Menu helpMenu;
	@FXML
	MenuItem exitMenuItem;
	@FXML
	MenuItem dataSourceMenuItem;
	
	@FXML
	private void initialize() {
    }
	@FXML
	private void handleOnClickDataSource() {
		ChoiceDialog(m_title, m_message, m_choices).ifPresent((str) -> {
			System.out.println(str);
			this.valeurChoisie = str ; 
			showAlert(valeurChoisie);
		});
	}
	
	@FXML
	private void handleDisconnect() {
        Boolean confirmed = showConfirmationDialog("Deconnexion", "Are you sure you want to disconnect?");
    	if(confirmed) {
    	application= RMSApplication.getInstance();
    	application.showLoginUI();
        //application.getPrimaryStage().show();
    	
    	}
    }
    @FXML
    private void handleExit() {
        Boolean confirmed = showConfirmationDialog("Exit", "Are you sure you want to exit?");
    	if(confirmed) {
    	//Quitter l'app
        System.exit(0);
    	}
    }
    
    //
    public void setApplication(RMSApplication application) {
        this.application = application;
    }

    private Optional<String> ChoiceDialog(String m_title, String message, String[] choices ) {
    	ChoiceDialog<String> cDial = new ChoiceDialog<>(choices[0], choices);
    	cDial.setTitle(m_title);
    	cDial.setHeaderText(message);
    	cDial.setContentText("Option:");
    	Optional<String> selection = cDial.showAndWait();
    	//selection.ifPresent(str -> System.out.println("Selection:" + str));
		return selection;
    }
    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
  //Utils    
    public void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		Optional<ButtonType> rep = alert.showAndWait();
	}
    
}
