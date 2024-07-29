package com.rms.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
@Entity(name = "T_Commandes")
public class Commande implements Serializable{
	@Override
	public String toString() {
		return "Commande ID : " + id ;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private LocalDateTime date;
    private String status ;
    //private int quantite ;
    
    private double montantTotalCommande = 0.0;
    
	public double getMontantTotalCommande() {
		calculerMontantTotal();
		return montantTotalCommande;
	}

	public void setMontantTotalCommande(double montantTotalCommande) {
		this.montantTotalCommande = montantTotalCommande;
	}
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "commade_produit")
	private List<Produit> produits = new ArrayList<>();
	
    @OneToOne (cascade= {CascadeType.PERSIST})
    @JoinColumn(name="restauranteur_id")
    private Utilisateur restauranteur;
    @OneToOne(mappedBy = "commande", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Paiement paiement;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "client_id")
    private Client client;
    
    //Ligne de commande
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> lignesCommande = new ArrayList<>();
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	 public double calculerMontantTotal() {
	        double montantTotal = 0.0;
	        for (LigneCommande ligneCommande : lignesCommande) {
	            montantTotal += ligneCommande.getTotal();
	        }
	        montantTotalCommande = montantTotal ;  
	        return montantTotal;
	    }
	
	public double getMontantTotal() {
		
		return montantTotalCommande;
	}

	public void setMontantTotal(double montantTotal) {
		this.montantTotalCommande = montantTotal;
	}

	public Utilisateur getRestauranteur() {
		return restauranteur;
	}

	public void setRestauranteur(Utilisateur restauranteur) {
		this.restauranteur = restauranteur;
	}

	public Paiement getPaiement() {
		return paiement;
	}

	public void setPaiement(Paiement paiement) {
		this.paiement = paiement;
		calculerMontantTotal();
	}
	public List<LigneCommande> getLignesCommande() {
		return lignesCommande;
	}

	public void setLignesCommande(List<LigneCommande> lignesCommande) {
		this.lignesCommande = lignesCommande;
		calculerMontantTotal();
	}
	public void addLignesCommande(LigneCommande lignesCommande) {
		this.lignesCommande.add(lignesCommande);
		calculerMontantTotal();
	}
	public LocalDateTime getLocalDateTime() {
		return date;
		
	}

	public void setLocalDateTime(LocalDateTime date) {
		this.date = date;
		calculerMontantTotal();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		calculerMontantTotal();
		this.status = status;
	}


	public Utilisateur getRestaurant() {
		return restauranteur;
	}
	
	public void setRestaurant(Utilisateur restaurant) {
		this.restauranteur = restaurant;
	}

	public List<Produit> getProduits() {
		calculerMontantTotal();
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}
	public Commande() {
		this.id = 0;
		this.date = LocalDateTime.now();
		this.status = "";
	
		this.montantTotalCommande = 0;
		this.restauranteur = null;
		
	}

	public void ajouterProduit(Produit produit , int quantite){
		produit.setQuantite(quantite);
		this.produits.add(produit);
	}
	

	public void addProduit(Produit produit){
		if (produit != null) {
		//this.selectedProduit = produit;
		this.produits.add(produit);
		calculerMontantTotal();
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	public void removeProduit(Produit produit) {
        if (produits.contains(produit)) {
            produits.remove(produit);
            produit.getCommandes().remove(this);
        }
    }
	//removeLigne
	public void removeLigneCommande(LigneCommande ligneCommande) {
			if (lignesCommande.contains(ligneCommande)) {
            lignesCommande.remove(ligneCommande);
            calculerMontantTotal();
			}
	}
	
	public StringProperty getIdProperty() {
		calculerMontantTotal();
		return new SimpleStringProperty(Integer.toString(id));
	}

	
	
		
}
