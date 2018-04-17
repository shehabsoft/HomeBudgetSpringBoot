package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the monthly_budget_category database table.
 * 
 */
@Embeddable
public class MonthlyBudgetCategoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int monthlyBudget_ID;

	@Column(insertable=false, updatable=false)
	private int categories_ID;

	public MonthlyBudgetCategoryPK() {
	}
	public int getMonthlyBudget_ID() {
		return this.monthlyBudget_ID;
	}
	public void setMonthlyBudget_ID(int monthlyBudget_ID) {
		this.monthlyBudget_ID = monthlyBudget_ID;
	}
	public int getCategories_ID() {
		return this.categories_ID;
	}
	public void setCategories_ID(int categories_ID) {
		this.categories_ID = categories_ID;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MonthlyBudgetCategoryPK)) {
			return false;
		}
		MonthlyBudgetCategoryPK castOther = (MonthlyBudgetCategoryPK)other;
		return 
			(this.monthlyBudget_ID == castOther.monthlyBudget_ID)
			&& (this.categories_ID == castOther.categories_ID);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.monthlyBudget_ID;
		hash = hash * prime + this.categories_ID;
		
		return hash;
	}
}