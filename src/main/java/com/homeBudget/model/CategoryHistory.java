package com.homeBudget.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the category_history database table.
 * 
 */
@Entity
@Table(name="category_history")
public class CategoryHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORY_HISTORY_ID_GENERATOR", sequenceName="SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORY_HISTORY_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="actual_value")
	private double actualValue;

	@Column(name="CATEGORY_STATUS", nullable=false)
	private int categoryStatus;

	@Column(name="category_type_id", nullable=false)
	private int categoryTypeId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	private Date creationDate;
	@Column(name="limit_value", nullable=false)
	private double limitValue;

	@Column(name="PARENT_CATEGORY_ID")
	private int parentCategoryId;

	@Column(name="planed_value")
	private double planedValue;

	@Column(name="user_id")
	private int userId;
 



	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	public CategoryHistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getActualValue() {
		return this.actualValue;
	}

	public void setActualValue(double actualValue) {
		this.actualValue = actualValue;
	}

	public int getCategoryStatus() {
		return this.categoryStatus;
	}

	public void setCategoryStatus(int categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public int getCategoryTypeId() {
		return this.categoryTypeId;
	}

	public void setCategoryTypeId(int categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}

	public double getLimitValue() {
		return this.limitValue;
	}

	public void setLimitValue(double limitValue) {
		this.limitValue = limitValue;
	}

	public int getParentCategoryId() {
		return this.parentCategoryId;
	}

	public void setParentCategoryId(int parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public double getPlanedValue() {
		return this.planedValue;
	}

	public void setPlanedValue(double planedValue) {
		this.planedValue = planedValue;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}