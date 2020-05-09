package com.example.demo.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.Repository;
import com.example.demo.Utility.FinAppUtility;
import com.example.demo.model.Expense;
import com.example.demo.model.Income;

@Service
public class FinAppService {
	@Autowired
	Repository repository;
	
	@Autowired
	FinAppUtility utility;

	public Map<String,Object> getMonthlySummary(String monthDate) throws IOException, ParseException {
		try {
			long food = 0;
			long miscellaneous = 0;
			long mandatory = 0;
			long fashion = 0;
			long travel = 0;
			long entertainment = 0;
			long netIncome = 0;
			long netExpense = 0;List<Expense> expenseList = new ArrayList<Expense>();
			expenseList = repository.getExpenses(monthDate);
			
			List<Income> incomeList = new ArrayList<Income>(); 
			incomeList = repository.getIncomes(monthDate);
		
			for (Expense e : expenseList) {
				netExpense = netExpense + e.getExpenseAmount();
				switch (e.getCategory().toLowerCase()) {
				case "miscellaneous":
					miscellaneous = miscellaneous + e.getExpenseAmount();
					break;
				case "food":
					food = food + e.getExpenseAmount();
					break;
				case "mandatory":
					mandatory = mandatory + e.getExpenseAmount();
					break;
				case "travel":
					travel = travel + e.getExpenseAmount();
					break;
				case "fashion":
					fashion = fashion + e.getExpenseAmount();
					break;
				case "entertainment":
					entertainment = entertainment + e.getExpenseAmount();
					break;
				}
			}
			for (Income i : incomeList) {
				netIncome = netIncome + i.getAmount();
			}
			
			long netProfit = netIncome - netExpense;
			Map<String, Object> datamap = new HashMap<String,Object>();
			datamap.put("entertainment", entertainment);
			datamap.put("netExpense", netExpense);
			datamap.put("fashion", fashion);
			datamap.put("food", food);
			datamap.put("netIncome", netIncome);
			datamap.put("mandatory", mandatory);
			datamap.put("miscellaneous", miscellaneous);
			datamap.put("monthDate", monthDate);
			datamap.put("netProfit", netProfit);
			datamap.put("travel", travel);
			datamap.put("timestamp", utility.timestampGenerator(monthDate));
			return datamap;
		}
		catch(NullPointerException e) {
			System.out.println("null pointer exception found !!!");
		}
		return null;
	}
}
