package com.horlogernet.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@javax.persistence.Entity
public class Reclamation implements Entity{
	@Id
	@GeneratedValue
	private long id;
	@Column
    private String reclamation;
	private Date dateCreation;
	@OneToOne
	private Commande commande;
	@OneToOne
	private Client client;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getReclamation() {
		return reclamation;
	}
	public void setReclamation(String reclamation) {
		this.reclamation = reclamation;
	}
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
}
