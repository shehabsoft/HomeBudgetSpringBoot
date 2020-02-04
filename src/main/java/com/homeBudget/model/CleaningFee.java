package com.homeBudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cleaning_fees database table.
 * 
 */
@Entity
@Table(name="cleaning_fees")
@NamedQuery(name="CleaningFee.findAll", query="SELECT c FROM CleaningFee c")
public class CleaningFee implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@SequenceGenerator(name="CLEANING_FEE_ID_GENERATOR", sequenceName="SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLEANING_FEE_ID_GENERATOR")
	private int id;

	@Column(name="created_by")
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="fee_amount")
	private double feeAmount;

	@Column(name="fees_name_ar")
	private String feesNameAr;

	@Column(name="fees_name_en")
	private String feesNameEn;

	@Column(name="updated_by")
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updateed_date")
	private Date updateedDate;

	//bi-directional many-to-one association to OrdersProduct
	@JsonIgnore
	@OneToMany(mappedBy="cleaningFee")
	private List<OrdersProduct> ordersProducts;

	public CleaningFee() {
	}



	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public double getFeeAmount() {
		return this.feeAmount;
	}

	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getFeesNameAr() {
		return this.feesNameAr;
	}

	public void setFeesNameAr(String feesNameAr) {
		this.feesNameAr = feesNameAr;
	}

	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdateedDate() {
		return this.updateedDate;
	}

	public void setUpdateedDate(Date updateedDate) {
		this.updateedDate = updateedDate;
	}

	public List<OrdersProduct> getOrdersProducts() {
		return this.ordersProducts;
	}

	public void setOrdersProducts(List<OrdersProduct> ordersProducts) {
		this.ordersProducts = ordersProducts;
	}

	public OrdersProduct addOrdersProduct(OrdersProduct ordersProduct) {
		getOrdersProducts().add(ordersProduct);
		ordersProduct.setCleaningFee(this);

		return ordersProduct;
	}

	public OrdersProduct removeOrdersProduct(OrdersProduct ordersProduct) {
		getOrdersProducts().remove(ordersProduct);
		ordersProduct.setCleaningFee(null);

		return ordersProduct;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFeesNameEn() {
		return feesNameEn;
	}

	public void setFeesNameEn(String feesNameEn) {
		this.feesNameEn = feesNameEn;
	}
}