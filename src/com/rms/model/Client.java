package com.rms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity (name = "T_Client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
	public Client() {
		id = 0 ;
		nom = "";
		prenom = "";
		adresse = "";
		email = "";
		telephone = "";
	}
    
    @OneToMany(mappedBy = "client")
    private List<Commande> commandes = new ArrayList<>();
    //Getters and setters
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	//getters property
	public SimpleStringProperty getNomProperty() {
		return new SimpleStringProperty(nom);
	}
	public SimpleStringProperty getPrenomProperty() {
        return new SimpleStringProperty(prenom);
    }
	public SimpleStringProperty getEmailProperty() {
        return new SimpleStringProperty(email);
    }
	public SimpleStringProperty getTelephoneProperty() {
        return new SimpleStringProperty(telephone);
    }
	public SimpleStringProperty getAdresseProperty() {
        return new SimpleStringProperty(adresse);
    }
	public IntegerProperty getIdProperty() {
        return new SimpleIntegerProperty(id);
    }
	 public void addCommande(Commande commande) {
	        commandes.add(commande);
	        
	 }

	 public void removeCommande(Commande commande) {
	        commandes.remove(commande);
	        commande.setClient(null);
	 }
	public String getNomComplet() {
		// TODO Auto-generated method stub
		return nom + " " + prenom;
	}
	
    
    
 }
