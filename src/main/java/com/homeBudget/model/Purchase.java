package com.homeBudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the purchase database table.
 * 
 */
@Entity
@Table(name="purchase")
@NamedQuery(name="Purchase.findAll", query="SELECT p FROM Purchase p")
public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PURCHASE_ID_GENERATOR", sequenceName="SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PURCHASE_ID_GENERATOR")
	private int id;

	@Column(name="arabic_description")
	private String arabicDescription;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;

	private String details;

	@Column(name="english_description")
	private String englishDescription;


	private double price;

	private int status;

	//bi-directional many-to-one association to ApprovedPurchases
	@OneToOne
	@JoinColumn(name="approved_Purchase_ID")
	private ApprovedPurchases approvedPurchas;

	//bi-directional many-to-one association to Category
	@ManyToOne
	private Category category;

	//bi-directional many-to-one association to Location
	@ManyToOne
	private Location location;


	@ManyToOne
	private MonthlyBudget monthlyBudget;

	//bi-directional many-to-one association to PurchaseHistory
	@JsonIgnore
	@OneToMany(mappedBy="purchase")
	private List<PurchaseHistory> purchaseHistories;

	public Purchase() {
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

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
 

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ApprovedPurchases getApprovedPurchas() {
		return this.approvedPurchas;
	}

	public List<PurchaseHistory> getPurchaseHistories() {
		return this.purchaseHistories;
	}

	public void setApprovedPurchas(ApprovedPurchases approvedPurchas) {
		this.approvedPurchas = approvedPurchas;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setPurchaseHistories(List<PurchaseHistory> purchaseHistories) {
		this.purchaseHistories = purchaseHistories;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public MonthlyBudget getMonthlyBudget() {
		return monthlyBudget;
	}

	public void setMonthlyBudget(MonthlyBudget monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}

	public PurchaseHistory addPurchaseHistory(PurchaseHistory purchaseHistory) {
		getPurchaseHistories().add(purchaseHistory);
		purchaseHistory.setPurchase(this);

		return purchaseHistory;
	}

	public PurchaseHistory removePurchaseHistory(PurchaseHistory purchaseHistory) {
		getPurchaseHistories().remove(purchaseHistory);
		purchaseHistory.setPurchase(null);

		return purchaseHistory;
	}

}