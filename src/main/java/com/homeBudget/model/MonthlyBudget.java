package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the monthly_budget database table.
 * 
 */
@Entity
@Table(name="monthly_budget")
@NamedQueries({@NamedQuery(name="getActiveMonthlyBudgetByUserId", query="SELECT m FROM MonthlyBudget m where m.user.id=:id and m.status=2"),
	@NamedQuery(name="deActivePreviosMonthlyBudget",query="update  MonthlyBudget m set m.status=1  where m.user.id=:id")})

public class MonthlyBudget implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MONTHLY_BUDGET_ID_GENERATOR", sequenceName="SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MONTHLY_BUDGET_ID_GENERATOR")
	private int id;

	 
	@Column(name="creation_date")
	private Date creationDate;
	private int status;
 
	@Column(name="end_date")
	private Date endDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	private Date startDate;

	@Column(name="total_expenses")
	private double totalExpenses;

	@Column(name="total_income")
	private double totalIncome;

	//bi-directional many-to-many association to Category
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Category> categories;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;



	
	//bi-directional many-to-one association to Purchase
	@OneToMany(mappedBy="monthlyBudget",fetch = FetchType.LAZY)
	private List<Purchase> purchases;
	
	
	public MonthlyBudget() {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public double getTotalExpenses() {
		return this.totalExpenses;
	}

	public void setTotalExpenses(double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public double getTotalIncome() {
		return this.totalIncome;
	}

	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}



}