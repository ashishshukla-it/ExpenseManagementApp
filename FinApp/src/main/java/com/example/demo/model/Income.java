package com.example.demo.model;

import java.util.Date;

public class Income {
	private String incomeMonth;
	private String description;
	private long amount;
	private Date timestamp;
	public String getIncomeMonth() {
		return incomeMonth;
	}
	public void setIncomeMonth(String incomeMonth) {
		this.incomeMonth = incomeMonth;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
