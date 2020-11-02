package com.homeBudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

/**
 * The persistent class for the orders_products database table.
 * 
 */
@Entity
@Table(name="orders_products")
@NamedQuery(name="OrdersProduct.findAll", query="SELECT o FROM OrdersProduct o")
public class OrdersProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ORDERS_PRODUCTS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDERS_PRODUCTS_ID_GENERATOR")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;

	private double quantity;
	@Cascade({SAVE_UPDATE})
    @Transient
	private Integer cleaningFeeId;

	private int status;

	//bi-directional many-to-one association to Order
	@JsonIgnore
	@ManyToOne
	private Order order;



	@ManyToOne( cascade = ALL)
	@JoinColumn(name = "cleaningFeeId", referencedColumnName = "id", nullable = true)
	private CleaningFee cleaningFee;

	//bi-directional many-to-one association to Product
	@ManyToOne
	private Product product;

	public OrdersProduct() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public CleaningFee getCleaningFee() {
		return cleaningFee;
	}

	public void setCleaningFee(CleaningFee cleaningFee) {
		this.cleaningFee = cleaningFee;
	}

	public Integer getCleaningFeeId() {
		return cleaningFeeId;
	}

	public void setCleaningFeeId(Integer cleaningFeeId) {
		this.cleaningFeeId = cleaningFeeId;
	}
}
