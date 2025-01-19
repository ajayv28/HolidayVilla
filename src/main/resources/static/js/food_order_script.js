import { camelCaseToNormal, jsonToText, postingFunction, extractKeys, getValue, getOrPutFunction} from './common_script.js';

const links = document.querySelectorAll(".header a");

links.forEach(link => {
    if (link.href === window.location.href) {
        link.classList.add("active"); 
    }
}); //to show active in header


document.getElementById("logoutButton").addEventListener("click", async function() {
    await fetch("https://holidayvilla-production.up.railway.app/logout", {
        method: "POST",
        credentials: "include"
    });
    window.location.href = "home.html";  
});

// JS CODE FOR FOOD_ORDER.HTML    *****************************************************************

document.getElementById("food_order-posting-closePopup").addEventListener("click", function() {
    document.getElementById("food_order-posting-popup").style.display = "none";
});

document.getElementById("food_order-getter-closePopup").addEventListener("click", function() {
    document.getElementById("food_order-getter-popup").style.display = "none";
});

document.getElementById("formOrderCompensationFood").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("food_order-posting-responseHeading");
    const message = document.getElementById("food_order-posting-responseMessage");
    const popup = document.getElementById("food_order-posting-popup");
    postingFunction(this, "https://holidayvilla-production.up.railway.app/api/food-order/order-compensation-food", "Below is order detail", "Food order is created successfully", heading, message, popup);
});

document.getElementById("formGetAllTodayFoodOrder").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/food-order/get-all-today-food-order";
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders dated Today:");
});

document.getElementById("formGetFoodOrderByOrderId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("getFoodOrderByOrderId").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/food-order/get-food-order-by-orderId?orderId=${id}`;
    getOrPutFunction("GET", api, table, popup, heading, "Food Orders detail of given Order ID:");
});

document.getElementById("formGetAllFoodOrderByFoodType").addEventListener("submit", function(event){ 
    event.preventDefault();
    const type = document.getElementById("getAllFoodOrderByFoodType").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/food-order/get-all-food-order-by-foodType?foodType=${type}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders of given food type:");
});

document.getElementById("formGetAllFoodOrderByOrderDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const date = document.getElementById("getAllFoodOrderByOrderDate").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/food-order/get-all-food-order-by-orderDate?date=${date}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders of given food type:");
});

document.getElementById("formGetAllFoodOrderByOrderDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const date = document.getElementById("getAllFoodOrderByOrderDate").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/food-order/get-all-food-order-by-orderDate?date=${date}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders on given order date:");
});

document.getElementById("formGetAllFoodOrderByRoomNo").addEventListener("submit", function(event){ 
    event.preventDefault();
    const room = document.getElementById("getAllFoodOrderByRoomNo").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/food-order/get-all-food-order-by-roomNo?roomNo=${room}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders on given room number:");
});

document.getElementById("formGetAllFoodOrderByGuestEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getAllFoodOrderByGuestEmail").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/food-order/get-all-food-order-by-guestEmail?guestEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders of given Guest:");
});














