package com.rms.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "T_Paiements")
public class Paiement implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	public double montantPayer;
	public double montantRecue;
	public double montantRendue;
	
	private LocalDateTime datePaiement;
	
	private String modePaiement; 
	
	@OneToOne
	private Commande commande;
	public Paiement() {
		this.id = 0;
		this.montantPayer = 0;
		this.montantRecue = 0;
		this.montantRendue = 0;
		this.datePaiement = LocalDateTime.now();
		this.modePaiement = "";
		this.commande = null;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDateTime getDatePaiement() {
		return datePaiement;
	}
	public void setDatePaiement(LocalDateTime datePaiement) {
		this.datePaiement = datePaiement;
	}
	public String getModePaiement() {
		return modePaiement;
	}
	public void setModePaiement(Mode_Paiement modePaiement) {
		this.modePaiement = modePaiement.name();
	}
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
		this.montantPayer = commande.getMontantTotal();
	}
	public double getMontantPayer() {
		return montantPayer;
	}
	public void setMontantPayer(double montantPayer) {
		this.montantPayer = montantPayer;
	}
	public double getMontantRecue() {
		return montantRecue;
	}
	public void setMontantRecue(double montantRecue) {
		this.montantRecue = montantRecue;
	}
	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}
	public double getMontantRendue() {
		return montantRendue;
	}
	public void setMontantRendue(double montantRendue) {
		this.montantRendue = montantRendue;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paiement other = (Paiement) obj;
		return id == other.id;
	}
	
	
}