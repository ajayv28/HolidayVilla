import { camelCaseToNormal, jsonToText, postingFunction, extractKeys, getValue, getOrPutFunction, backendUrl} from './common_script.js';

const links = document.querySelectorAll(".header a");

links.forEach(link => {
    if (link.href === window.location.href) {
        link.classList.add("active"); 
    }
}); //to show active in header


document.getElementById("logoutButton").addEventListener("click", function() {
    window.location.href = "/logout";
});

// JS CODE FOR COUPON.HTML    *****************************************************************

document.getElementById("coupon-posting-closePopup").addEventListener("click", function() {
    document.getElementById("coupon-posting-popup").style.display = "none";
});

document.getElementById("coupon-getter-closePopup").addEventListener("click", function() {
    document.getElementById("coupon-getter-popup").style.display = "none";
});

document.getElementById("couponRequestForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("coupon-posting-responseHeading");
    const message = document.getElementById("coupon-posting-responseMessage");
    const popup = document.getElementById("coupon-posting-popup");
    postingFunction(this, `${backendUrl}/api/coupon/register", "Below is your coupon detail`, "Your coupon is created successfully", heading, message, popup);
});


document.getElementById("couponChangeQuantityForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const code = document.getElementById("couponCode").value;
    const qty = document.getElementById("newQuantity").value;
    const heading = document.getElementById("coupon-getter-responseHeading");
    const table = document.getElementById("coupon-getter-responseTable");
    const popup = document.getElementById("coupon-getter-popup");
    const api = `${backendUrl}/api/coupon/changeQuantity?couponCode=${code}&newQuantity=${qty}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Coupon is edited successfully");
});








