package com.rms.model;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity( name = "Chefcusinier")
public class ChefCuisinier extends Utilisateur implements Serializable{
	
	public ChefCuisinier() {}
//	public ChefCuisinier(Utilisateur utilisateur) {
//	    	//int id, String nom, String prenom,String login, String password, String email, String telephone, Role role
//	    super(utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getLogin(), utilisateur.getPassword(),
//	    			utilisateur.getEmail(), utilisateur.getTelephone(),Role.valueOf(utilisateur.getRole()));
//	
//	}
//	
//	public Produit createProduct(String nom, double prix, String description) {
//	    return null;
//	}
//	public void updateProduct(Produit produit) {
//		
//	}
//
//	public void deleteProduit(Produit produit) {
//	}
//	public List<Produit> listProduits() {
//	    return null;
//	}

	}