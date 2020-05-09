package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.example.demo.Utility.FinAppUtility;


public class ExcelReader {
	static RestHighLevelClient restHighLevelClient = null;
	
	public static void main(String args[]) throws IOException, ParseException {
		FileInputStream file = new FileInputStream(new File("src/main/resources/finApp.xls"));
		FinAppUtility utility = new FinAppUtility();
		HSSFWorkbook wb = new HSSFWorkbook(file);
		makeConnection();
		HSSFSheet sheet = wb.getSheetAt(0);
		int rowCount = 0;
		Map <String, Object> datamap = new HashMap<String, Object>();
		for (Row row : sheet) {
			int count = 0;
			for (Cell cell : row) {
				switch (count) {
				case 0:
					String monthDate = cell.getStringCellValue();
					Date date = utility.timestampGenerator(monthDate);
					datamap.put("timestamp", date);
					datamap.put("expenseMonth", monthDate);
					break;
				case 1:
					datamap.put("description", cell.getStringCellValue());
					break;
				case 2:
					datamap.put("expenseAmount",cell.getNumericCellValue());
					break;
				case 3:
					datamap.put("category", cell.getStringCellValue());
					break;
				}
				count++;
			}
			IndexRequest indexRequest = new IndexRequest("expense", "finAppData", "").source(datamap);
			IndexResponse response = restHighLevelClient.index(indexRequest);
			rowCount++;
			System.out.println((response.toString()));
			System.out.println(rowCount);
			
		}
		closeConnection();

	}
	
	private static synchronized RestHighLevelClient makeConnection() {

		if (restHighLevelClient == null) {
			restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
		}
		System.out.println("connection established");
		return restHighLevelClient;
	}
	
	private static synchronized void closeConnection() throws IOException {
		restHighLevelClient.close();
		restHighLevelClient = null;
		System.out.println("connection closed");
	}
}
