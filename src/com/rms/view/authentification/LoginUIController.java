package com.rms.view.authentification;





import com.rms.RMSApplication;
import com.rms.model.Role;
import com.rms.model.Utilisateur;
import com.rms.utilitaire.Utilitaire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginUIController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;
    
    Utilisateur utilisateur ;
    boolean flag;

	private RMSApplication rMSApplication;
	
	@FXML
	public void cancel(){
		boolean isCancel = Utilitaire.showConfirmationDialog( "Voulez-vous vraiment quitter l'application?", "Quitter Application");
		if(isCancel) {
			System.exit(0);
		}
	}
	private boolean validateLoginFields() {
	    String errorMessage = "";

	    if (loginField.getText().trim().isEmpty()) {
	        errorMessage += "\nEntrer un nom utilisateur valide.";
	    }

	    if (passwordField.getText().isEmpty()) {
	        errorMessage += "\nEntrer un mot de passe.";
	    }

	    if (errorMessage.isEmpty()) {
	        return true;
	    } else {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erreur de Validation ");
	        alert.setHeaderText(null);
	        alert.setContentText(errorMessage);
	        alert.showAndWait();
	        return false;
	    }
	}

	
	    @FXML
    public void login(ActionEvent event){
	  if (validateLoginFields()) {
	    String login = loginField.getText();
	    String password = passwordField.getText();
	    Role role ;
	    
	    utilisateur = new Utilisateur(login, password);
	    	    try {
		    if(utilisateur.authenticate()) {
		    	RMSApplication.getInstance().setUser(utilisateur);

		    	
		    	switch (utilisateur.getRole()) {
		    	    case "ADMINISTRATEUR" :
		    	    	RMSApplication.getInstance().initRootLayoutAdministrateur();
		    	        RMSApplication.getInstance().showHomeAdministrateurUI();
	        			cleanForm();
		    	        // Fermez la fenêtre actuelle
		    	        Stage currentStage = (Stage) submitButton.getScene().getWindow();
		    	        if(currentStage!= null)
		    	        currentStage.close();
		    	        break;
		    	    case "RESTAURATEUR":
		    	        
		    	        RMSApplication.getInstance().initRootLayoutRestaurateur();
		    	        RMSApplication.getInstance().showHomeRestaurateurUI();
		    	        cleanForm();
		    	        // Fermez la fenêtre actuelle
		    	        Stage currentStage2 = (Stage) submitButton.getScene().getWindow();
		    	        if(currentStage2!= null)
		    	        currentStage2.close();
		    	        break;
		    	    case "CHEF_CUISINIER":
		    	        
		    	    	RMSApplication.getInstance().iniRootLayoutChefCuisinier();
		    	        RMSApplication.getInstance().showHomeChefCuisinierUI();
		    	        cleanForm();
		    	        // Fermez la fenêtre actuelle
		    	        Stage currentStage3 = (Stage) submitButton.getScene().getWindow();
		    	        if(currentStage3!= null)
		    	        currentStage3.close();
		    	        break;
		    	    default:
		    	        Alert alert = new Alert(AlertType.ERROR);
		    	        alert.setTitle("Échec de la connexion");
		    	        alert.setHeaderText(null);
		    	        alert.setContentText("Nom utilisateur ou mot de passe incorrect");
		    	        alert.showAndWait();
		    	        cleanForm();
		    	        break;
		    	}
		    }else {
		    	Utilitaire.showAlert("Login ou Mot de passe invalide", "Authentification");
		    }
		}catch (Exception ex)
        {
			ex.printStackTrace();
            //Utilitaire.showAlert("Une erreur s'est produite : ", "Erreur");
            cleanForm();
        }
	   
	 }

    }
    public TextField getLoginField() {
		return loginField;
	}
    
    private void cleanForm() {
        loginField.clear();
        passwordField.clear();
    }

	public void setLoginField(TextField loginField) {
		this.loginField = loginField;
	}
	public PasswordField getPasswordField() {
		return passwordField;
	}
	public void setPasswordField(PasswordField passwordField) {
		this.passwordField = passwordField;
	}
	public Button getSubmitButton() {
		return submitButton;
	}
	public void setSubmitButton(Button submitButton) {
		this.submitButton = submitButton;
	}
	public Utilisateur getUtillisateur() {
		return utilisateur;
	}
	public void setUtillisateur(Utilisateur utillisateur) {
		this.utilisateur = utillisateur;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public RMSApplication getrMSApplication() {
		return rMSApplication;
	}
	public void setrMSApplication(RMSApplication rMSApplication) {
		this.rMSApplication = rMSApplication;
	}
	public void setApplication(RMSApplication app) {
    	this.rMSApplication = app ;
    }
    

}