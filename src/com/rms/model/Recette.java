package com.rms.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity(name = "T_Recettes")
public class Recette {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private LocalDateTime date;
//    private double montant;
//    
//	private String produit;
//	private int quantite;
//    
//	@OneToOne(mappedBy = "recette")
//    private Commande commande;
//	public Recette() {
//        this.date = LocalDateTime.now();
//    }
//    //new Recette(LocalDateTime.now(), "Produit B", 5, 50.0),
//    public Recette(LocalDateTime date, String produit, int quantite, double montant) {
//    	this.date = date;
//        this.produit = produit;
//        this.quantite = quantite;
//        this.montant = montant;
//    }
//    public int getId() {
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public LocalDateTime getDateCreation() {
//        return date;
//    }
//
//    public void setDateCreation(LocalDateTime dateCreation) {
//        this.date = dateCreation;
//    }
//
//    public double getMontantTotal() {
//        return montant;
//    }
//
//    public void setMontantTotal(double montantTotal) {
//        this.montant = montantTotal;
//    }
//    // Autres méthodes utiles pour la gestion de la recette...
//	public LocalDateTime getDate() {
//		return date;
//	}
//	public void setDate(LocalDateTime date) {
//		this.date = date;
//	}
//	public double getMontant() {
//		return montant;
//	}
//	public void setMontant(double montant) {
//		this.montant = montant;
//	}
//	public Commande getCommande() {
//		return commande;
//	}
//	public void setCommande(Commande commande) {
//		this.commande = commande;
//	}
//	public String getProduit() {
//		return produit;
//	}
//	public void setProduit(String produit) {
//		this.produit = produit;
//	}
//	public int getQuantite() {
//		return quantite;
//	}
//	public void setQuantite(int quantite) {
//		this.quantite = quantite;
//	}
//	@Override
//	public String toString() {
//		return "Recette [produit=" + produit + "]";
//	}
//	
    
}
