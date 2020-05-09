package com.example.demo.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.Repository;
import com.example.demo.service.FinAppService;

@RequestMapping("/finApp")
@RestController
public class FinAppController {
	@Autowired
	Repository repository;

	@Autowired
	FinAppService service;

	@PostMapping("/postExpense")
	public String postExpense(@RequestParam(name = "description") String description,
			@RequestParam(name = "expenseAmount") long expenseAmount, @RequestParam(name = "category") String category,
			@RequestParam(name = "expenseMonth") String expenseMonth) throws IOException, ParseException {
		return repository.storeExpense(description, expenseAmount, category.toLowerCase(), expenseMonth);
	}

	@PostMapping("postIncome")
	public String postIncome(@RequestParam(name = "incomeMonth") String incomeMonth,
			@RequestParam(name = "description") String description, @RequestParam(name = "amount") long amount)
			throws IOException, ParseException {
		return repository.storeIncome(incomeMonth, description, amount);
	}

	@GetMapping("storeMonthSummary")
	public Map<String, Object> storeMonthSummary(@RequestParam(name = "monthDate") String monthDate) throws IOException, 
		ParseException {
		repository.storeMonthlySummary(monthDate, service.getMonthlySummary(monthDate));
		return service.getMonthlySummary(monthDate);

	}

	@GetMapping("getMonthSummary")
	public Map<String, Object> getMonthSummary(@RequestParam(name = "monthDate") String monthDate) throws IOException, 
		ParseException {
		return service.getMonthlySummary(monthDate);

	}
}
