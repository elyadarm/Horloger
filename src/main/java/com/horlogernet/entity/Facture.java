package com.horlogernet.entity;

import javax.persistence.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@javax.persistence.Entity
public class Facture implements Entity{
	@Id
	@GeneratedValue
	private long id;
	@Column
    private String number;
	@Column
    private float price;
	@Column
    private Etat etat;
	@OneToOne
	private Commande commande;
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}



	public Facture(String number, float price, Etat etat) {
		super();
		this.number = number;
		this.price = price;
		this.etat = etat;
	}


	public Facture() {
		super();

	}
	
}
