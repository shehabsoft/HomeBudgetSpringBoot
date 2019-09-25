package com.homeBudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the approved_purchases database table.
 * 
 */
@Entity
@Table(name="approved_purchases")
@NamedQuery(name="ApprovedPurchases.findAll", query="SELECT a FROM ApprovedPurchases a")
public class ApprovedPurchases implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APPROVED_PURCHASES_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APPROVED_PURCHASES_ID_GENERATOR")
	private int id;

	@Column(name="arabic_description")
	private String arabicDescription;

	private String details;

	@Column(name="english_description")
	private String englishDescription;
	@JsonIgnore
	//bi-directional many-to-one association to Category
	@ManyToOne
	private Category category;
    @JsonIgnore
	//bi-directional many-to-one association to Purchase
	@OneToMany(mappedBy="approvedPurchas")
	private List<Purchase> purchases;

	public ApprovedPurchases() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArabicDescription() {
		return this.arabicDescription;
	}

	public void setArabicDescription(String arabicDescription) {
		this.arabicDescription = arabicDescription;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getEnglishDescription() {
		return this.englishDescription;
	}

	public void setEnglishDescription(String englishDescription) {
		this.englishDescription = englishDescription;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Purchase> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Purchase addPurchas(Purchase purchas) {
		getPurchases().add(purchas);
		purchas.setApprovedPurchas(this);

		return purchas;
	}

	public Purchase removePurchas(Purchase purchas) {
		getPurchases().remove(purchas);
		purchas.setApprovedPurchas(null);

		return purchas;
	}

}