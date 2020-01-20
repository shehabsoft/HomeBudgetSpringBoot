package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ORDERS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDERS_ID_GENERATOR")
	private int id;

	private String address1;

	private String address2;



	@OneToOne
	@JoinColumn(name="country_id")
	private Country country;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;

	@Column(name="phone_number")
	private String phoneNumber;

	private int status;

	private double total;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;

	@OneToOne
	@JoinColumn(name="user_id")
	private User user;

	private String zip;

	//bi-directional many-to-one association to OrdersProduct
	@OneToMany(mappedBy="order")
	private List<OrdersProduct> ordersProducts;

	public Order() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}



	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<OrdersProduct> getOrdersProducts() {
		return this.ordersProducts;
	}

	public void setOrdersProducts(List<OrdersProduct> ordersProducts) {
		this.ordersProducts = ordersProducts;
	}

	public OrdersProduct addOrdersProduct(OrdersProduct ordersProduct) {
		getOrdersProducts().add(ordersProduct);
		ordersProduct.setOrder(this);

		return ordersProduct;
	}

	public OrdersProduct removeOrdersProduct(OrdersProduct ordersProduct) {
		getOrdersProducts().remove(ordersProduct);
		ordersProduct.setOrder(null);

		return ordersProduct;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}