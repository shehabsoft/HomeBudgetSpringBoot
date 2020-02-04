package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the products_sellers database table.
 * 
 */
@Entity
@Table(name="products_sellers")
@NamedQuery(name="ProductsSeller.findAll", query="SELECT p FROM ProductsSeller p")
public class ProductsSeller implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUCTS_SELLERS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCTS_SELLERS_ID_GENERATOR")
	private int id;

	@Column(name="creation_date")
	private String creationDate;

	private double sale;

	@Column(name="update_date")
	private String updateDate;

	//bi-directional many-to-one association to Product
	@ManyToOne
	private Product product;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public ProductsSeller() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public double getSale() {
		return this.sale;
	}

	public void setSale(double sale) {
		this.sale = sale;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}