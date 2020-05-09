package com.example.demo.validation;

import org.springframework.stereotype.Component;

@Component
public class FinAppValidator {
	public String expenseValidator(String category, String expenseMonth) {
		if(validateCategory(category)!=null)
			return validateCategory(category);
		if(validateDate(expenseMonth)!=null)
			return validateDate(expenseMonth);
		return null;
		
	}
	
	public String validateCategory(String category) {
		switch(category) {
		case "miscellaneous": break;
		case "mandatory": break;
		case "travel": break;
		case "entertainment": break;
		case "fashion": break;
		case "food": break;
		default:
			return "Invalid category !!!, Please enter one amoung (miscellaneous, mandatory, travel, entertainment, fashion, food)";
		}
		return null;
	}
	
	public String validateDate(String date) {
		try {
			String month = date.split("\\s")[0];
			String year = date.split("\\s")[1];
			
			switch(month) {
				case "january": break;
				case "february": break;
				case "march": break;
				case "april": break;
				case "may": break;
				case "june": break;
				case "july": break;
				case "august": break;
				case "september": break;
				case "october": break;
				case "november": break;
				case "december": break;
				default:
					return "Invalid month !!!, Please enter it as month(String) YYYY";
			}
			
			switch(year) {
				case "2018": break;
				case "2019": break;
				case "2020": break;
				default:
					return "Invalid year !!!, Please enter it as month(String) YYYY and should be in range of 2018 - 2020";
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return "Please enter space between month and year";
		}
		return null;
	}
	
}
