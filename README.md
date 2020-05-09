# ExpenseManagementApp
Project to manage all monthly and annual expenses and to do analysis on the data

This project consists of 2 parts - 
	I. An excel reader that would read the file and will push ‘expense’ details in Elastic Search index names ‘expense’. Sample of file is also stored in this repository.
	II. An Rest bases service which have following mentioned APIs - 

1. postExpense
	URL - POST http://localhost:8080/finApp/postExpense?description=House rent&expenseAmount=7167&category=mandatory&expenseMonth=may 2020
	Parameters - description (String), expenseAmount (long), category (String), expenseMonth (String)
	Response - IndexResponse[index=expense,type=finAppData,id=mAdF73EBtQOSadhlMPhw,version=1,result=created,seqNo=0,primaryTerm=1,shards={"total":			2,"successful":1,"failed":0}]

2. postIncome
	URL - POST http://localhost:8080/finApp/postIncome?description=salary&incomeMonth=august 2018&amount=48630 
	Parameters - description (String), incomeMonth (String), amount (long)
	Response - IndexResponse[index=income,type=finAppData,id=mQcl9HEBtQOSadhlNvjs,version=1,result=created,seqNo=0,primaryTerm=1,shards={"total":			2,"successful":1,"failed":0}]

3. getMonthSummary
	URL - GET http://localhost:8080/finApp/getMonthSummary?monthDate=august 2018
	Parameters - monthDate (String)
	Response - {
    "monthDate": "august 2018",
    "miscellaneous": 683,
    "entertainment": 771,
    "netIncome": 48630,
    "travel": 263,
    "mandatory": 21466,
    "food": 2598,
    "netProfit": 17689,
    "netExpense": 30941,
    "fashion": 5160,
    "timestamp": "2018-07-31T18:30:00.000+0000"
}

4. storeMonthSummary
	URL - GET http://localhost:8080/finApp/storeMonthSummary?monthDate=august 2018
	Same as getMonthSummary, only difference is that it also stores data in ‘monthly-summary’ index 
