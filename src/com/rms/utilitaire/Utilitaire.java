package com.rms.utilitaire;



import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;


import com.hibernate.factories.AdministrateurFactory;
import com.hibernate.factories.ConcreteFactory;
import com.rms.exceptions.DAOException;
import com.rms.model.Administrateur;
import com.rms.model.Role;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
/**
 * Author Kodjo Souleymane
 */
public class Utilitaire {
	Utilitaire(){
		
	}
	 public static Date convertLocalDateToDate(LocalDate localDate) {
	        if (localDate == null) {
	            return null;
	        }
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    }
	    public static LocalDate convertDateToLocalDate(Date date) {
	        if (date == null) {
	            return null;
	        }
	        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    }
	
	public static void showAlert(String message, String titre ) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(titre);
		alert.setContentText(message);
		Optional<ButtonType> rep = alert.showAndWait();
		
	}
    public static boolean showConfirmationDialog(String message, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
	

}
