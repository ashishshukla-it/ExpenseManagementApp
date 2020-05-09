package com.example.demo.model;

import java.util.Date;

public class Expense {
	private String expenseMonth;
	private Date timestamp;
	private String category;
	private long expenseAmount;
	private String description;
	
	public String getExpenseMonth() {
		return expenseMonth;
	}
	public void setExpenseMonth(String expenseMonth) {
		this.expenseMonth = expenseMonth;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public long getExpenseAmount() {
		return expenseAmount;
	}
	public void setExpenseAmount(long expenseAmount) {
		this.expenseAmount = expenseAmount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
