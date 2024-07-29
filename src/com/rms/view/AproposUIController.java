package com.rms.view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AproposUIController {
	    @FXML
	    private void handleFermer(ActionEvent event) {
	        // Fermer la fenêtre "À Propos"
	        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
	        stage.close();
	    }
}
