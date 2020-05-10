package com.example.demo.Repository;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Utility.FinAppUtility;
import com.example.demo.model.Expense;
import com.example.demo.model.Income;
import com.example.demo.validation.FinAppValidator;
import com.google.gson.Gson;

@Component
public class Repository {
	@Autowired
	FinAppValidator validator;
	
	@Autowired
	FinAppUtility utility;
	protected Logger logger = LoggerFactory.getLogger(Repository.class);
	private static final String HOST = "localhost";
	private static final int PORT = 9200;
	private static final String SCHEME = "http";
	private static RestHighLevelClient restHighLevelClient;
	private static final String TYPE = "finAppData";

	@PostConstruct
	private static synchronized RestHighLevelClient makeConnection() {

		if (restHighLevelClient == null) {
			restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(HOST, PORT, SCHEME)));
		}
		System.out.println("connection established");
		return restHighLevelClient;
	}

	@PreDestroy
	private static synchronized void closeConnection() throws IOException {
		restHighLevelClient.close();
		restHighLevelClient = null;
		System.out.println("connection closed");
	}

	public String storeExpense(String description, long expenseAmount, String category, String expenseMonth)
			throws IOException, ParseException {
		if(validator.expenseValidator(category, expenseMonth)!=null)
			return validator.expenseValidator(category, expenseMonth);
		Expense expense = new Expense();
		expense.setDescription(description);
		expense.setExpenseAmount(expenseAmount);
		expense.setCategory(category);
		expense.setExpenseMonth(expenseMonth);
		expense.setTimestamp(utility.timestampGenerator(expenseMonth));
		
		String index = "expense";
		Gson gson = new Gson();
		String json = gson.toJson(expense);
		IndexRequest indexRequest = new IndexRequest(index, TYPE, "").source(json, XContentType.JSON);
		IndexResponse response = restHighLevelClient.index(indexRequest);
		logger.info(response.toString());
		return response.toString();
	}

	public String storeIncome(String incomeMonth, String description, long amount) throws IOException, ParseException {
		if(validator.validateDate(incomeMonth)!=null)
			return validator.validateDate(incomeMonth);
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("incomeMonth", incomeMonth);
		datamap.put("description", description);
		datamap.put("amount", amount);
		datamap.put("timstamp", utility.timestampGenerator(incomeMonth));
		String index = "income";
		IndexRequest indexRequest = new IndexRequest(index, TYPE, "").source(datamap);
		IndexResponse response = restHighLevelClient.index(indexRequest);
		logger.info(response.toString());
		return response.toString();
	}
	
	public String storeMonthlySummary(String monthDate, Map<String,Object> monthSummary) throws IOException {
		if(validator.validateDate(monthDate)!=null)
			return validator.validateDate(monthDate);
		String index = "monthly-summary";
		IndexRequest indexRequest = new IndexRequest(index, TYPE, monthDate.toString()).source(monthSummary);
		IndexResponse response = restHighLevelClient.index(indexRequest);
		logger.info(response.toString());
		return response.toString();
	}

	public List<Expense> getExpenses(String monthDate) throws IOException {
		BoolQueryBuilder bool = QueryBuilders.boolQuery();
		bool.must().add(
				QueryBuilders.queryStringQuery(monthDate.toString()).field("expenseMonth").defaultOperator(Operator.AND));
		SearchRequest searchRequest = new SearchRequest("expense");
		SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource().query(bool).size(10000);
		searchRequest = searchRequest.source(searchSourceBuilder);
		SearchResponse response = restHighLevelClient.search(searchRequest);
		List<Expense> expenseList = new ArrayList<Expense>();
		for (SearchHit hits : response.getHits().getHits()) {
			Expense temp = new Expense();
			temp.setDescription(hits.getSourceAsMap().get("description").toString());
			 String stringToConvert = String.valueOf(hits.getSourceAsMap().get("expenseAmount"));
		     Double convertedDouble = Double.parseDouble(stringToConvert);
		     Long value = convertedDouble.longValue();
			temp.setExpenseAmount(value);
			temp.setExpenseMonth(hits.getSourceAsMap().get("expenseMonth").toString());
			temp.setCategory(hits.getSourceAsMap().get("category").toString());
			expenseList.add(temp);
		}
		return expenseList;
	}

	public List<Income> getIncomes(String monthDate) throws IOException{
		BoolQueryBuilder bool = QueryBuilders.boolQuery();
		bool.must().add(QueryBuilders.queryStringQuery(monthDate.toString()).field("incomeMonth").defaultOperator(Operator.AND));
		SearchRequest searchRequest = new SearchRequest("income");
		SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource().query(bool);
		searchRequest = searchRequest.source(searchSourceBuilder);
		SearchResponse response = restHighLevelClient.search(searchRequest);
		List<Income> incomeList = new ArrayList<Income>();
		for(SearchHit hits : response.getHits().getHits()) {
			Income temp = new Income();
			temp.setAmount((int) hits.getSourceAsMap().get("amount"));
			temp.setDescription(hits.getSourceAsMap().get("description").toString());
			temp.setIncomeMonth(hits.getSourceAsMap().get("incomeMonth").toString());
			incomeList.add(temp);
		}
		
		return incomeList;
	}

}
