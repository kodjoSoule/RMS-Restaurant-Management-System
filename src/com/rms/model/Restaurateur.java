package com.rms.model;

import java.io.Serializable;
import java.util.Date;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name="T_Restaurateurs")
public class Restaurateur extends Utilisateur implements Serializable {
//	
//	@OneToOne (cascade= {CascadeType.PERSIST})
//    @JoinColumn(name="commande_id")
//	private Commande commande;
//	public Restaurateur() {}
//	public Restaurateur(Utilisateur utilisateur) {
//    	//int id, String nom, String prenom,String login, String password, String email, String telephone, Role role
//    	super(utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getLogin(), utilisateur.getPassword(),
//    			utilisateur.getEmail(), utilisateur.getTelephone(),Role.valueOf(utilisateur.getRole()));
//    }
//	public List<Produit> consultProduits() {
//        // TODO implement here
//        return null;
//    }
//    public void takeOrder(Commande commande) {
//        // TODO implement here
//    }
//
//    
//    public void recordPayment(Paiement paiement) {
//        // TODO implement here
//    }
//
//  
//    public void trackRevenue(Date dateDebut, Date dateFin) {
//        // TODO implement here
//    }
//
//    
//    public void getTotalRevenueDaily() {
//        // TODO implement here
//    }
//
//    public void getTotalRevenueWeekly() {
//        // TODO implement here
//    }
//
//    public void getTotalRevenueMonthly() {
//        // TODO implement here
//    }
//
//    
//    public void getTotalRevenueByProductDaily() {
//        // TODO implement here
//    }
//
//    
//    public void getTotalRevenueByProductWeekly() {
//        // TODO implement here
//    }
//
//    public void getTotalRevenueByProductMonthly() {
//        // TODO implement here
//    }

}