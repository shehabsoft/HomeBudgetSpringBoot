package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the monthly_budget_category database table.
 * 
 */
@Entity
@Table(name="monthly_budget_categories")
public class MonthlyBudgetCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="actual_value")
	private double actualValue;

	@Column(name="limit_value")
	private double limitValue;

	@Column(name="planed_value")
	private double planedValue;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="categories_ID")
	private Category category;

	//bi-directional many-to-one association to MonthlyBudget
	@ManyToOne
	private MonthlyBudget monthlyBudget;

	public MonthlyBudgetCategory() {
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

	public double getLimitValue() {
		return this.limitValue;
	}

	public void setLimitValue(double limitValue) {
		this.limitValue = limitValue;
	}

	public double getPlanedValue() {
		return this.planedValue;
	}

	public void setPlanedValue(double planedValue) {
		this.planedValue = planedValue;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public MonthlyBudget getMonthlyBudget() {
		return this.monthlyBudget;
	}

	public void setMonthlyBudget(MonthlyBudget monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}

}