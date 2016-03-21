package com.horlogernet.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.codehaus.jackson.map.annotate.JsonView;

import com.horlogernet.views.JsonViews;

@javax.persistence.Entity
public class Commande implements Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@Column	   
	private String number;
	
	@Column
	private Date creationDate;
	
	@Column
	private Statut statut;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Client client;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<Montre> montres;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Facture facture;
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}



	public void setMontres(List<Montre> montres) {
		this.montres = montres;
	}
	

	public long getId() {
		return id;
	}



	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Statut getStatut() {
		return statut;
	}
	
	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public List<Montre> getMontres() {
		return montres;
	}
	public void setMontres(ArrayList<Montre> montres) {
		this.montres = montres;
	}
	public void addMontre(Montre m){
		montres.add(m);
	}
	
	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public Commande() {
		super();
		this.montres = new ArrayList<Montre>();
	}
	
	public Commande(long id, String number, Date creationDate, Statut statut, Client client, List<Montre> montres) {
		super();
		this.id = id;
		this.number = number;
		this.creationDate = creationDate;
		this.statut = statut;
		this.client = client;
		this.montres = montres;
	}
}
