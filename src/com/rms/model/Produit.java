package com.rms.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.type.descriptor.java.IntegerJavaType;

import com.rms.RMSApplication;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableObjectValue;

@Entity(name = "T_Produits")
public class Produit implements Serializable {
	

	public List<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	private int id;
	    private String intitule;
	    private double prix;
	    int quantiteStock =0 ;
		double total ;
		private String description;
	    @Lob
	    @Column(length = 5242880)
	    private byte[] image; 
	    
	    @ManyToMany(mappedBy = "produits", cascade = CascadeType.DETACH)
	    private List<Commande> commandes = new ArrayList<Commande>();
	    
	    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
	    private List<LigneCommande> lignesCommande = new ArrayList<>();  
	    
	    @ManyToOne
	    private Categorie categorie = new Categorie() ;
//	    @ManyToOne
//	    @JoinColumn(name = "commande_id")
//	    private Commande commande = null ;
	    
	    public Categorie getCategorie() {
			return categorie;
		}

		public void setCategorie(Categorie categorie) {
			this.categorie = categorie;
		}
		public byte[] getImage() {
			return image;
		}

		public void setImage(byte[] image) {
			this.image = image;
		}

		public Produit(int id, String nom, double prix, String description) {
	        this.id = id;
	        this.intitule = nom;
	        this.prix = prix;
	        this.description = description;
	        
	    }
		public Produit(String nom, double prix, String description) {
	        
	        this.intitule = nom;
	        this.prix = prix;
	        this.description = description;
	        
	        
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
			Produit other = (Produit) obj;
			return id == other.id;
		}

		public Produit() {
			
			this.intitule = "";
			this.prix = 0;
			this.description = "";
			this.quantiteStock = 0 ;
			
			this.categorie = null;
			
		}

		public int getId() {
			return id;
		}

		public String getIntitule() {
			return intitule;
		}

		
		
		
		public int getQuantiteStock() {
			return quantiteStock;
		}

		public void setQuantiteStock(int quantiteStock) {
			this.quantiteStock = quantiteStock;
		}

		public List<LigneCommande> getLignesCommande() {
			return lignesCommande;
		}

		public void setLignesCommande(List<LigneCommande> lignesCommande) {
			this.lignesCommande = lignesCommande;
		}

		public double getPrix() {
			return prix;
		}

		public String getDescription() {
			return description;
		}

		
		//all getters property
		public StringProperty getIdProperty() {
			return new SimpleStringProperty(id+"");
		}
		public StringProperty getIntituleProperty() {
			return new SimpleStringProperty(intitule);
		}
        public StringProperty getPrixProperty() {
            return new SimpleStringProperty(Double.toString(prix));
        }
		public void setPrix(double prix) {
			this.prix = prix;
			total = prix*quantiteStock ;
		}
		public StringProperty getDescriptionProperty() {
            return new SimpleStringProperty(description);
        }
        
		
		public void setId(int id) {
			this.id = id;
		}
		
		

		public int getQuantite() {
			return quantiteStock;
		}

	    public void setQuantite(int quantite) {
			this.quantiteStock = quantite;
			total = prix*quantite ;
		}
		public double getTotal() {
			return total;
		}

		public void setTotal(double total) {
			this.total = total;
		}

//		public Commande getCommande() {
//			return commande;
//		}

//		public void setCommande(Commande commande) {
//			this.commande = commande;
//		}

		public void addCommande(Commande commande) {
			this.commandes.add(commande);
			
		}
	
		 public void removeCommande(Commande commande) {
		   commandes.remove(commande);
		   commande.getProduits().remove(this);
		}
		public void setIntitule(String intitule) {
			this.intitule = intitule;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		 
		public ObservableObjectValue<String> getImageProduitProperty() {
			// 
			return null;
		}
		@Override
		public String toString() {
			return intitule.toUpperCase() ;
		}

		public void addQuantite(Integer valueOf) {
			this.quantiteStock += valueOf;
			total = prix*quantiteStock ;
		}
		public void removeQuantite(int quantite) {
	        if (quantiteStock >= quantite) {
	            this.quantiteStock -= quantite;
	        }
	    }
		public Produit(String intitule, double prix, int quantite, double total) {
			super();
			
			this.intitule = intitule;
			this.prix = prix;
			this.quantiteStock = quantite;
			this.total = total;
		}

		public StringProperty getQuantiteProperty() {
            return new SimpleStringProperty(Double.toString(quantiteStock));
        }
		
		
}
