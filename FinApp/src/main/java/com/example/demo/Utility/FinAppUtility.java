package com.example.demo.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class FinAppUtility {
	public Date timestampGenerator(String date) throws ParseException {
		String month = date.split("\\s")[0];
		String year = date.split("\\s")[1];
		String monthInt="";
		switch (month) {
			case "january":
				monthInt = "01";
				break;
			case "february":
				monthInt = "02";
				break;
			case "march":
				monthInt = "03";
				break;
			case "april":
				monthInt = "04";
				break;
			case "may":
				monthInt = "05";
				break;
			case "june":
				monthInt = "06";
				break;
			case "july":
				monthInt = "07";
				break;
			case "august":
				monthInt = "08";
				break;
			case "september":
				monthInt = "09";
				break;
			case "october":
				monthInt = "10";
				break;
			case "november":
				monthInt = "11";
				break;
			case "december":
				monthInt = "12";
				break;
				
		}
		
		String dateString = year +"/" + monthInt + "/01";
		Date timestamp = new SimpleDateFormat("yyyy/MM/dd").parse(dateString);
		return timestamp;
	}
}
