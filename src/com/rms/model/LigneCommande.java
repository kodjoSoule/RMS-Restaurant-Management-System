package com.rms.model;

import java.io.Serializable;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity(name = "T_LignesCommande")
public class LigneCommande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantite = 1;
    @ManyToOne
    private Produit produit;
    
//    @OneToOne
//    private Produit produit ;
    
    @ManyToOne
    private Commande commande;
    
    private double total= 0.0;
    public void setId(int id) {
        this.id = id;
    }
    
    public Produit getProduit() {
        return produit;
    }
    
    public void setProduit(Produit produit) {
    	if(produit!= null)
    	total = produit.getPrix() * quantite;
    	this.produit = produit;
    }
    
    public Commande getCommande() {
        return commande;
    }
    
    public void setCommande(Commande commande) {
        
    	this.commande = commande;
    }
    
    public int getQuantite() {
        return quantite;
    }
    
    
    
    public void addQuantite(int quantite) {
        this.quantite = quantite;
        total = produit.getPrix() * quantite;
    }
    
    public double getTotal(){
    	
    	return this.total ;
    }
    @Override
    public String toString() {
        return id + "\n" + commande.getClient().toString() + "\n" + produit.toString();
    }    
    
}
