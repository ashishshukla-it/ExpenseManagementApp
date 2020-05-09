package com.example.demo.model;

import java.util.Date;

public class MonthlySummary {
	private String monthDate;
	private long income;
	private long expense;
	private long netProfit;
	private long food;
	private long fashion;
	private long miscellaneous;
	private long mandatory;
	private long entertainment;
	private long travel;
	private Date timestamp;
	
	public String getMonthDate() {
		return monthDate;
	}
	public void setMonthDate(String monthDate) {
		this.monthDate = monthDate;
	}
	public long getIncome() {
		return income;
	}
	public void setIncome(long income) {
		this.income = income;
	}
	public long getExpense() {
		return expense;
	}
	public void setExpense(long expense) {
		this.expense = expense;
	}
	public long getFood() {
		return food;
	}
	public void setFood(long food) {
		this.food = food;
	}
	public long getFashion() {
		return fashion;
	}
	public void setFashion(long fashion) {
		this.fashion = fashion;
	}
	public long getMiscellaneous() {
		return miscellaneous;
	}
	public void setMiscellaneous(long miscellaneous) {
		this.miscellaneous = miscellaneous;
	}
	public long getMandatory() {
		return mandatory;
	}
	public void setMandatory(long mandatory) {
		this.mandatory = mandatory;
	}
	public long getEntertainment() {
		return entertainment;
	}
	public void setEntertainment(long entertainment) {
		this.entertainment = entertainment;
	}
	public long getTravel() {
		return travel;
	}
	public void setTravel(long travel) {
		this.travel = travel;
	}
	public long getNetProfit() {
		return netProfit;
	}
	public void setNetProfit(long netProfit) {
		this.netProfit = netProfit;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
