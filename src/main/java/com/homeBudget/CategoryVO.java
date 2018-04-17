package com.homeBudget;


public class CategoryVO {
	
	private int Id;
	private boolean withenLimit;
	private String actualValueStr;
	private String statusDate;
	private double percentageExpenses;
	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	public boolean isWithenLimit() {
		return withenLimit;
	}

	public void setWithenLimit(boolean withenLimit) {
		this.withenLimit = withenLimit;
	}

	public void setEnglishDescription(String englishDescription) {
		this.englishDescription = englishDescription;
	}



	private String arabicDescription;
	
	public String getActualValueStr() {
		return actualValueStr;
	}

	public void setActualValueStr(String actualValueStr) {
		this.actualValueStr = actualValueStr;
	}



	private String englishDescription;
    private int userId;
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


    private double newValue;
	public double getNewValue() {
		return newValue;
	}

	public void setNewValue(double newValue) {
		this.newValue = newValue;
	}
	private int categoryId;
	private int categoryTypeId;
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(int categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}



	private double limitValue;
	
	private double planedValue;
	
	private double actualValue;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getArabicDescription() {
		return arabicDescription;
	}

	public void setArabicDescription(String arabicDescription) {
		this.arabicDescription = arabicDescription;
	}

	public String getEnglishDescription() {
		return englishDescription;
	}

	public void setEnglisDescription(String englisDescription) {
		this.englishDescription = englisDescription;
	}

	public int getParentCategoryId() {
		return categoryId;
	}

	public void setParentCategoryId(int parentCategoryId) {
		this.categoryId = parentCategoryId;
	}



	public double getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(double limitValue) {
		this.limitValue = limitValue;
	}

	public double getPlanedValue() {
		return planedValue;
	}

	public void setPlanedValue(double planedValue) {
		this.planedValue = planedValue;
	}

	public double getActualValue() {
		return actualValue;
	}

	public void setActualValue(double actualValue) {
		this.actualValue = actualValue;
	}

	public double getPercentageExpenses() {
		return percentageExpenses;
	}

	public void setPercentageExpenses(double percentageExpenses) {
		this.percentageExpenses = percentageExpenses;
	}

	public int getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(int categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	int categoryStatus;
}
