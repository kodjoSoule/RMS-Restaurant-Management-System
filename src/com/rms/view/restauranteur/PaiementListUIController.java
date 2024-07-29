package com.rms.view.restauranteur;

	import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
	import javafx.scene.control.cell.PropertyValueFactory;
	import com.rms.model.Paiement;
import com.rms.model.Produit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.hibernate.dao.impl.PaiementHbnDaoImpl;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.PaiementFactory;
import com.rms.exceptions.DAOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

	public class PaiementListUIController {
		 private ObservableList<Paiement> paiementsList = FXCollections.observableArrayList();

	    @FXML
	    private TableView<Paiement> paiementTableView;
	    @FXML
	    private TableColumn<Paiement, Integer> idPaiementColumn;
	    @FXML
	    private TableColumn<Paiement, Double> montantPayeColumn;
	    @FXML
	    private TableColumn<Paiement, String> modePaiementColumn;
	    @FXML
	    private ComboBox<String> periodeComboBox;
	    @FXML
	    private TableColumn<Paiement, String> datePaiementColumn;
	    @FXML
	    private Label labelTotal;
	    private PaiementHbnDaoImpl paiementDao;
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    @FXML
	    private void initialize() {
	    	paiementDao = ConcreteFactory.getFactory(PaiementFactory.class).getPaiementDao(PaiementHbnDaoImpl.class);
	        // Configure les colonnes pour la TableView des paiements
	        idPaiementColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	        montantPayeColumn.setCellValueFactory(new PropertyValueFactory<>("montantRecue"));
	        modePaiementColumn.setCellValueFactory(new PropertyValueFactory<>("modePaiement"));
	        
	        
	        datePaiementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatePaiement().format(formatter)));
	        
	     // Remplir le ComboBox avec les options de périodes
	        periodeComboBox.getItems().addAll("Journalière", "Hebdomadaire", "Mensuelle");
	        periodeComboBox.getSelectionModel().select(0);
	       
	        
	        // Charge les paiements enregistrés
	        loadPaiements();
	        calculateTotalSum();
	        
	    }
	    @FXML
	    private void calculateTotalSum() {
	        double totalSum = 0.0;
	        
	        for (Paiement paiement : paiementTableView.getItems()) {
	        	totalSum +=  paiement.getMontantRecue();
	        }
	        
	        labelTotal.setText(totalSum + " FCFA");
	    }
	    private void loadPaiements() {
	        try {
	            paiementTableView.getItems().clear();
	            paiementTableView.getItems().addAll(FXCollections.observableArrayList(paiementDao.list()));
	            
	            
	        } catch (DAOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @FXML
	    private void actualiserRecettes() {
	        System.out.println("actualiserRecettes");
	        String periode = periodeComboBox.getValue(); // Assuming you have a periodeComboBox somewhere in your controller
	        LocalDate startDate;
	        LocalDate endDate = LocalDate.now();

	        // Determine the start and end dates based on the selected period
	        switch (periode) {
	            case "Journalière":
	                startDate = endDate; // For a daily period, start and end date are the same (today)
	                break;
	            case "Hebdomadaire":
	                startDate = endDate.minusWeeks(1); // For a weekly period, subtract 1 week from today
	                break;
	            case "Mensuelle":
	                startDate = endDate.minusMonths(1); // For a monthly period, subtract 1 month from today
	                break;
	            default:
	                startDate = endDate; // Set a default value if needed
	        }

	        // Fetch paiements based on the date range
	        List<Paiement> paiements = getRecettesByDateRange(startDate, endDate);

	        // Update the ObservableList and TableView with the fetched paiements
	        paiementsList.clear();
	        paiementsList.addAll(paiements);
	        paiementTableView.setItems(paiementsList);
	        calculateTotalSum();
	    }

	    private List<Paiement> getRecettesByDateRange(LocalDate startDate, LocalDate endDate) {
	        List<Paiement> paiements = new ArrayList<>();

	        // You need to implement your actual logic to fetch paiements
	        // based on the date range and other criteria
	        // Replace the following with your actual data retrieval logic

	        try {
	            for (Paiement paiement : paiementDao.list()) {
	                LocalDate paiementDate = paiement.getDatePaiement().toLocalDate(); // Extract the LocalDate from LocalDateTime

	                if (isDateInRange(paiementDate, startDate, endDate)) {
	                    paiements.add(paiement);
	                }
	            }
	        } catch (DAOException e) {
	            e.printStackTrace();
	        }

	        return paiements;
	    }
	    private boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
	        // Helper method to check if a date falls within the specified range
	        return !date.isBefore(startDate) && !date.isAfter(endDate);
	    }
	    
	}
