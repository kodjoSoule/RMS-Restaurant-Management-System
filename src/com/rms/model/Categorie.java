package com.rms.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import javafx.beans.property.SimpleStringProperty;

@Entity(name = "T_Categories")
public class Categorie implements Serializable {
		@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public int idCategorie;
    public String libelle;
    public String description;
    
    @OneToMany(mappedBy ="categorie", cascade = CascadeType.DETACH)
    private List<Produit> produits = new ArrayList<Produit>();
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	
    public Categorie(int idCategorie, String libelle, List<Produit> produits) {
		super();
		this.idCategorie = idCategorie;
		this.libelle = libelle;
		this.produits = produits;
	}

	public Categorie() {
		this.idCategorie = 0 ;
		this.libelle = "";
		this.description = "";
    }

    public Categorie(String libelle) {
		super();
		this.libelle = libelle;
	}

	public Categorie(int idCategorie, String libelle) {
		super();
		this.idCategorie = idCategorie;
		this.libelle = libelle;
	}

	public Categorie(int idCategorie, String libelle, String description) {
		this.idCategorie = idCategorie;
		this.libelle = libelle;
		this.description = description;
		}

	public Categorie ajouterCategorie() {
        
        return null;
    }

    
    public void modifierCategorie(Categorie categorie) {
        
        
    }

    
    public void supprimerCategorie(Categorie categorie) {
        
        
    }

    
    public List<Categorie> listCategorie() {
       
        return null;
    }

	@Override
	public String toString() {
		return libelle.toUpperCase();
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public String getLibelle() {
		return libelle;
	}
	//getLibelleProperty
	public SimpleStringProperty getLibelleProperty() {
		return new SimpleStringProperty(libelle);
	}
	//getDescriptionProperty
	public SimpleStringProperty getDescriptionProperty() {
        return new SimpleStringProperty(description);
    }
	//getIdProperty
	
	
	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	@Override
	public int hashCode() {
		return Objects.hash(idCategorie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		return idCategorie == other.idCategorie;
	}



}