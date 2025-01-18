import { camelCaseToNormal, jsonToText, postingFunction, extractKeys, getValue, getOrPutFunction} from './common_script.js';

const links = document.querySelectorAll(".header a");

links.forEach(link => {
    if (link.href === window.location.href) {
        link.classList.add("active"); 
    }
}); //to show active in header


document.getElementById("logoutButton").addEventListener("click", async function() {
    await fetch("http://localhost:8081/logout", {
        method: "POST",
        credentials: "include"
    });
    window.location.href = "/home.html";  
});

// JS CODE FOR TRANSACTION.HTML    *****************************************************************

document.getElementById("transaction-posting-closePopup").addEventListener("click", function() {
    document.getElementById("transaction-posting-popup").style.display = "none";
});

document.getElementById("transaction-getter-closePopup").addEventListener("click", function() {
    document.getElementById("transaction-getter-popup").style.display = "none";
});


document.getElementById("formNewTransaction").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("transaction-posting-responseHeading");
    const message = document.getElementById("transaction-posting-responseMessage");
    const popup = document.getElementById("transaction-posting-popup");
    const api = "http://localhost:8081/api/transaction/create";
    postingFunction(this, api, "Below is the transaction detail", "New transaction is created successfully", heading, message, popup);
});

document.getElementById("formGetTransactionsByTransactionId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("getTransactionsByTransactionId").value;
    const heading = document.getElementById("transaction-getter-responseHeading");
    const table = document.getElementById("transaction-getter-responseTable");
    const popup = document.getElementById("transaction-getter-popup");
    const api = `http://localhost:8081/api/transaction/get-transaction-by-transactionId?transactionId=${id}`;
    getOrPutFunction("GET", api, table, popup, heading, "Transaction Details:");
});

document.getElementById("formGetTransactionsByTypeAndPeriod").addEventListener("submit", function(event){ 
    event.preventDefault();
    const type = document.getElementById("getTransactionsByTypeAndPeriodFundType").value;
    const period = document.getElementById("getTransactionsByTypeAndPeriodPeriod").value;
    const heading = document.getElementById("transaction-getter-responseHeading");
    const table = document.getElementById("transaction-getter-responseTable");
    const popup = document.getElementById("transaction-getter-popup");
    const api = `http://localhost:8081/api/transaction/get-transactions-by-type-and-period?period=${period}&fundType=${type}`;
    getOrPutFunction("GET", api, table, popup, heading, "Transaction Details for given period:");
});

document.getElementById("formGetTransactionsByDepartmentAndPeriod").addEventListener("submit", function(event){ 
    event.preventDefault();
    const department = document.getElementById("getTransactionsByDepartmentAndPeriodDepartment").value;
    const period = document.getElementById("getTransactionsByDepartmentAndPeriodPeriod").value;
    const heading = document.getElementById("transaction-getter-responseHeading");
    const table = document.getElementById("transaction-getter-responseTable");
    const popup = document.getElementById("transaction-getter-popup");
    const api = `http://localhost:8081/api/transaction/get-transactions-by-department-and-period?period=${period}&department=${department}`;
    getOrPutFunction("GET", api, table, popup, heading, "Transaction Details for given period:");
});

document.getElementById("formGetTransactionsMadeBetweenDates").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("getTransactionsMadeBetweenDatesFrom").value;
    const to = document.getElementById("getTransactionsMadeBetweenDatesTo").value;
    const heading = document.getElementById("transaction-getter-responseHeading");
    const table = document.getElementById("transaction-getter-responseTable");
    const popup = document.getElementById("transaction-getter-popup");
    const api = `http://localhost:8081/api/transaction/get-transactions-made-between-dates?fromDate=${from}&toDate=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, `Transaction made between ${from} and ${to}:`);
});

document.getElementById("formGetTransactionsMadeBetweenAmount").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("getTransactionsMadeBetweenAmountFrom").value;
    const to = document.getElementById("getTransactionsMadeBetweenAmountTo").value;
    const heading = document.getElementById("transaction-getter-responseHeading");
    const table = document.getElementById("transaction-getter-responseTable");
    const popup = document.getElementById("transaction-getter-popup");
    const api = `http://localhost:8081/api/transaction/get-transactions-made-between-amounts?fromAmount=${from}&toAmount=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, `Transaction value between ${from} and ${to}:`);
});


