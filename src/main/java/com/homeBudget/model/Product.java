package com.homeBudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Blob;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUCT_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCT_ID_GENERATOR")
	private int id;

	private String details;

	@Column(name="img_url")
	private String imgUrl;

	@Column(name="img_data")
	private Byte[] imgData;

	@Column(name="name_ar")
	private String nameAr;

	@Column(name="name_en")
	private String nameEn;

	private double price;

	@JsonIgnore
	//bi-directional many-to-one association to OrdersProduct
	@OneToMany(mappedBy="product")
	private List<OrdersProduct> ordersProducts;

	public Product() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getNameAr() {
		return this.nameAr;
	}

	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	public String getNameEn() {
		return this.nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<OrdersProduct> getOrdersProducts() {
		return this.ordersProducts;
	}

	public void setOrdersProducts(List<OrdersProduct> ordersProducts) {
		this.ordersProducts = ordersProducts;
	}

	public OrdersProduct addOrdersProduct(OrdersProduct ordersProduct) {
		getOrdersProducts().add(ordersProduct);
		ordersProduct.setProduct(this);

		return ordersProduct;
	}

	public OrdersProduct removeOrdersProduct(OrdersProduct ordersProduct) {
		getOrdersProducts().remove(ordersProduct);
		ordersProduct.setProduct(null);

		return ordersProduct;
	}

	public Byte[] getImgData() {
		return imgData;
	}

	public void setImgData(Byte[] imgData) {
		this.imgData = imgData;
	}
}