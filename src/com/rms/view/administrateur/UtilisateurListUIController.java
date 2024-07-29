package com.rms.view.administrateur;

import java.awt.PrintJob;
import java.util.Optional;

import javax.swing.JTable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hibernate.dao.impl.UtilisateurHbnDaoImpl;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.UtilisateurFactory;
import com.rms.RMSApplication;
import com.rms.dao.IDao;
import com.rms.exceptions.DAOException;
import com.rms.model.Utilisateur;

import org.hibernate.Session;

import jakarta.persistence.Query;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class UtilisateurListUIController {
	@FXML
    private TableView<Utilisateur> userTable;
    @FXML
    private TableColumn<Utilisateur, String> nomColumn;
    @FXML
    private TableColumn<Utilisateur, String> prenomColumn;
    @FXML
    private Label idLabel ;
    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;    
    @FXML
    private Label emailLabel;    
    @FXML
    private Label telephoneLabel;    
    @FXML
    private Label loginLabel;    
    @FXML
    private Label passwordLabel;
    @FXML
    private Label roleLabel;
    
    //Search
    @FXML
    private TextField searchTextField;
    //
    @FXML
    private Button searchButton ;
    @FXML
    private ComboBox<String> searchCombox ;
    
    

    UtilisateurHbnDaoImpl dataSource ;
    Utilisateur selectedUtilisateur ;
    
    ObservableList<Utilisateur> dataList = FXCollections.observableArrayList();
    
    //
    public UtilisateurListUIController() {
    	    
    }
    
    @FXML
    private void initialize(){
    	nomColumn.setCellValueFactory(cellData -> cellData.getValue().getNomProperty());
    	prenomColumn.setCellValueFactory(cellData -> cellData.getValue().getPrenomProperty());

    	//ConcreteFactory.getFactory(UtilisateurFactory.class).getUtilisateurDao(UtilisateurHbnDaoImpl.class);
    	 dataSource = ConcreteFactory.getFactory(UtilisateurFactory.class).getUtilisateurDao(UtilisateurHbnDaoImpl.class);
//
    	addChangeListener();
    	
    	cleanUtilisateurFromTableView();
    	try {
			updateTableView();
			addSearchListener();
		} catch (DAOException e) {}
    	

    }
    private void addChangeListener() {
    	userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null) {  
             this.selectedUtilisateur = newValue;
             setUtilisateur(selectedUtilisateur);
                
            } else {
          	  cleanUtilisateurFromTableView();
            }
      	});
    }
    public static void printTableView(TableView<Utilisateur> tableView) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(tableView);
            if (success) {
                job.endJob();
            }
        }
    }
    public void updateTableView() throws DAOException{
    	if (dataSource.list().size() > 0) {
             dataList.clear();
             dataList.addAll(dataSource.list());
             userTable.setItems(dataList);
             userTable.getSelectionModel().select(0);
             
             setUtilisateur(selectedUtilisateur);
         }
    	//userTable.setItems(dataSource.list());
    }
    
    
    public void setUtilisateur(Utilisateur user) {
    	if( user!=null) {
    	 
    	idLabel.setText( String.valueOf(user.getId()));
    	nomLabel.setText(user.getNomProperty().getValue());
        prenomLabel.setText(user.getPrenomProperty().getValue());
        telephoneLabel.setText(user.getTelephoneProperty().getValue());
        emailLabel.setText(user.getEmailProperty().getValue());
        loginLabel.setText(user.getLoginProperty().getValue());
        passwordLabel.setText(user.getPasswordProperty().getValue());
        roleLabel.setText(user.getRoleProperty().getValue());
        }else {
        	idLabel.setText("");
        	nomLabel.setText("");
            prenomLabel.setText("");
            telephoneLabel.setText("");
            emailLabel.setText("");
            loginLabel.setText("");
            passwordLabel.setText("");
            roleLabel.setText("");
        }
    }
    
    //onCancle
    
    public void handleOnClickDelete() throws Exception {
    	
    	if (selectedUtilisateur != null) {
    		boolean confirmed = showConfirmationDialog("Confirmation de suppression",
                    "Voulez-vous vraiment supprimer cet utilisateur ?");
    		if(confirmed) {
    			dataSource.delete(selectedUtilisateur.getId());
    			
    			updateTableView();
    			cleanSearchField();
    			addSearchListener();
    		}
    		
	    }else {
	    	showAlert("Aucun elements selectionne");
	    }
    	
    	
    }
    
    
   
    @FXML
    public void handleOnClickAdd() throws Exception {
    	Utilisateur utilisateur = new Utilisateur() ;
    	boolean validerClicked = RMSApplication.getInstance().showFormUtilisateurUI( utilisateur);
    	if (validerClicked) {
    		//dataSource.createUtilisateur(user);
    		dataSource.create(utilisateur);
    		updateTableView();
    		addSearchListener();
            
    	}
    }
    @FXML
    public void handleOnClickEdit() throws Exception {
    	 Utilisateur selectedUtilisateur = userTable.getSelectionModel().getSelectedItem();
    	 if (selectedUtilisateur != null) {
    		 boolean validerClicked = RMSApplication.getInstance().showFormUtilisateurUI(selectedUtilisateur);
    		 if (validerClicked) {
    			 
    			 dataSource.update(selectedUtilisateur);
    			 
    			 updateTableView();
    			 addSearchListener();
    			 cleanSearchField();
    			 userTable.getSelectionModel().select(selectedUtilisateur);
    			 
    		 }
    		 
    			
    	 }else {
 	    	showAlert("Aucun elements selectionnee");
 	    	
 	    	
 	    	
 	    }
    }
    public void cleanSearchField() {
    	searchTextField.clear();
    }
    public void cleanUtilisateurFromTableView() {
    	idLabel.setText("");
    	nomLabel.setText("");
        prenomLabel.setText("");
        telephoneLabel.setText("");
        emailLabel.setText("");
        loginLabel.setText("");
        passwordLabel.setText("");
        roleLabel.setText("");
    }
        //search_user
    @FXML
    public void addSearchListener() throws DAOException {
    	updateTableView();
    	//1
    	FilteredList<Utilisateur> filteredData = new FilteredList<>(dataList, p -> true);
    	//2
    	searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(user -> {
    		if (newValue == null || newValue.isEmpty()) {
    			return true;
			}


    		String lowerCaseFilter = newValue.toLowerCase();
			if (user.getNomProperty().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				return true; 
			} else if (user.getPrenomProperty().get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				return true; 
			}
				return false; 
			});
		});
    	//3
    	SortedList<Utilisateur> sortedData = new SortedList<>(filteredData);
    	//4
    	sortedData.comparatorProperty().bind(userTable.comparatorProperty());
    	//5
    	userTable.setItems(sortedData);
    }
    
        
        
    public void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		Optional<ButtonType> rep = alert.showAndWait();
		
	}
    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    
}