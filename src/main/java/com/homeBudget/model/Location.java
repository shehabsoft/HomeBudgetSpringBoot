package com.homeBudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the location database table.
 * 
 */
@Entity
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOCATION_ID_GENERATOR", sequenceName="SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOCATION_ID_GENERATOR")
	private int id;

	@Column(name="arabic_name")
	private String arabicName;

	@Column(name="english_name")
	private String englishName;

	//bi-directional many-to-one association to Purchase
	@OneToMany(mappedBy="location")
	private List<Purchase> purchases;
	@JsonIgnore
	//bi-directional many-to-one association to PurchaseHistory
	@OneToMany(mappedBy="location")
	private List<PurchaseHistory> purchaseHistories;

	public Location() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArabicName() {
		return this.arabicName;
	}

	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}

	public String getEnglishName() {
		return this.englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public List<Purchase> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Purchase addPurchas(Purchase purchas) {
		getPurchases().add(purchas);
		purchas.setLocation(this);

		return purchas;
	}

	public Purchase removePurchas(Purchase purchas) {
		getPurchases().remove(purchas);
		purchas.setLocation(null);

		return purchas;
	}

	public List<PurchaseHistory> getPurchaseHistories() {
		return this.purchaseHistories;
	}

	public void setPurchaseHistories(List<PurchaseHistory> purchaseHistories) {
		this.purchaseHistories = purchaseHistories;
	}

	public PurchaseHistory addPurchaseHistory(PurchaseHistory purchaseHistory) {
		getPurchaseHistories().add(purchaseHistory);
		purchaseHistory.setLocation(this);

		return purchaseHistory;
	}

	public PurchaseHistory removePurchaseHistory(PurchaseHistory purchaseHistory) {
		getPurchaseHistories().remove(purchaseHistory);
		purchaseHistory.setLocation(null);

		return purchaseHistory;
	}

}