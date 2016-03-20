package com.horlogernet.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.map.annotate.JsonView;

import com.horlogernet.views.JsonViews;

@javax.persistence.Entity
public class Montre implements Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	@Column
    private Statut statut;
	@Column
    private String information; 
	@ManyToOne
	private Commande commande;
	@ManyToOne
	private User user;
    public long getId() {
        return id;
    }
    	


	public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
    
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Montre(Statut statut, String information) {
        this.statut = statut;
        this.information = information;
    }

    public Montre() {
    }

    public Montre(long id, Statut statut, String information) {
        this.id = id;
        this.statut = statut;
        this.information = information;
    }
    public Montre(long id, Statut statut, String information, Commande commande) {
		super();
		this.id = id;
		this.statut = statut;
		this.information = information;
		this.commande = commande;
	}
    
    public Commande getCommande() {
		return commande;
	}



	public void setCommande(Commande commande) {
		this.commande = commande;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public void setId(long id) {
		this.id = id;
	}

	

	@Override
    public String toString() {
        return "Montre{" + "statut=" + statut + ", information=" + information + '}';
    }
}
