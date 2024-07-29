package com.rms.model;

import java.io.Serializable;

import com.hibernate.dao.impl.UtilisateurHbnDaoImpl;
import com.hibernate.factories.ConcreteFactory;
import com.hibernate.factories.UtilisateurFactory;
import com.rms.exceptions.BadFormatAuthentificationDataException;
import com.rms.exceptions.DAOException;
import com.rms.exceptions.NullAuthentificationDataException;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


@Entity(name = "T_Utilisateurs")
////@MappedSuperclass
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "profil_utilisateur", discriminatorType = DiscriminatorType.STRING, length = 16)
public class Utilisateur implements Serializable {
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	private static final long serialVersionUID = 6155742639274593659L;
	//ignore the variable
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id ;
	
    private String nom;
	
    private String prenom;
    
    private String email;
    
    private String telephone;
    @Column(name = "login", unique = true)
    private String login;
    
    private String password;
    
    private String role;
    public Utilisateur() {
    	//id
    	this.id =  0 ;
    	this.nom = new String("") ;
    	this.prenom = new String("") ;
    	this.email = new String("") ;
    	this.telephone = new String("") ;
    	this.login = new String("") ;
    	this.password = new String("") ;
    	this.role = new String("") ;
    }
    public Utilisateur(String nom, String prenom, String email, String telephone, Role role) {
        this.nom = new String(nom);
        this.prenom = new String(prenom);
        this.email = new String(email);
        this.telephone = new String(telephone);

        this.login = new String(prenom.trim().toLowerCase() + "." +
                nom.trim().toLowerCase());
        this.password = new String("p@Ss3R");
        this.role = new String(role.toString());

    }
    
    public Utilisateur(String login, String password) {
    	this.login = login ;
		this.password = password;
	}
	
	public Utilisateur(String nom, String prenom, String login, String password, String email, String telephone,
			Role role) {
		this.id = 0 ;
		this.nom = new String(nom);
        this.prenom = new String(prenom);
        this.email = new String(email);
        this.telephone = new String(telephone);
        this.login = new String(login);
        this.password = new String(password);
        this.role = new String(role.toString());
	}
	
	public static void genererAdminUser() {
	    UtilisateurHbnDaoImpl utilisateurHbnDaoImpl = ConcreteFactory.getFactory(UtilisateurFactory.class).getUtilisateurDao(UtilisateurHbnDaoImpl.class);

	    String adminLogin = "kodjo";
	    String adminPassword = "kodjo";
	    String adminNom = "Kodjo";
	    String adminPrenom = "Admin";
	    String adminEmail = "admin@example.com";
	    String adminTelephone = "123456789";
	    
	    try {
	        if (!utilisateurHbnDaoImpl.isUserExists(adminLogin)) {
	            Utilisateur admin = new Utilisateur(adminNom, adminPrenom, adminLogin, adminPassword, adminEmail, adminTelephone, Role.ADMINISTRATEUR);
	            utilisateurHbnDaoImpl.create(admin);
	        }
	    } catch (DAOException e) {
	        e.printStackTrace();
	    }
	}

	public boolean authenticate() throws Exception {
		UtilisateurHbnDaoImpl utilisateurHbnDaoImpl = ConcreteFactory.getFactory(UtilisateurFactory.class).getUtilisateurDao(UtilisateurHbnDaoImpl.class);
        boolean isAuthenticate = utilisateurHbnDaoImpl.authenticate(this);
		return isAuthenticate ;
	}

	public StringProperty getNomProperty() {
		return new SimpleStringProperty(nom);
	}
	public int getId() {
		return this.id ;
	}

	public StringProperty getPrenomProperty() {
		return new SimpleStringProperty(prenom);
	}

	public StringProperty getEmailProperty() {
		return new SimpleStringProperty(email);
	}

	public StringProperty getTelephoneProperty() {
		return new SimpleStringProperty(telephone);
	}

	public StringProperty getLoginProperty() {
		return new SimpleStringProperty(login);
	}

	public StringProperty getPasswordProperty() {
		return new SimpleStringProperty(password);
	}

	public StringProperty getRoleProperty() {
		return new SimpleStringProperty(role);
	}
	//
	
	
	public void setId(int id) {
		this.id = id ;
	}
	public void setNom(String nom) {
		this.nom = nom ;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public String getEmail() {
		return email;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ",\n prenom=" + prenom + ", email=" + email
				+ ", telephone=" + telephone + ",\n login=" + login + ",\n password=" + password + ", role=" + role + "]";
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Utilisateur otherUser = (Utilisateur) obj;
	    return this.id == otherUser.id;
	}
	public String getNomComplet() {
		// TODO Auto-generated method stub
		return nom + " " + prenom ;
	}


}
