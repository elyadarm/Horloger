package com.horlogernet.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.codehaus.jackson.map.annotate.JsonView;

import com.horlogernet.views.JsonViews;
@javax.persistence.Entity
public class Client implements Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@Column	   
	private String firstname;
	@Column	   
	private String lastname;
	@Column
	private String phone;
	@OneToMany(fetch=FetchType.EAGER)
	private List<Commande> commandes;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public List<Commande> getCommandes() {
		return commandes;
	}
	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void addCommande(Commande c){
		commandes.add(c);
	}
	public Client(String firstname, String lastname, String phone) {
		super();	
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
	}
	public Client() {
		super();
		this.commandes = new ArrayList<Commande>();
	}
	public Client(long id, String firstname, String lastname, String phone, List<Facture> factures,
			List<Commande> commandes) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.commandes = commandes;
	}
	
	
}
