package com.rms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
@Entity(name = "Administrateurs")
public class Administrateur extends Utilisateur implements Serializable{
	    public Administrateur() {
	    }
	    
	}