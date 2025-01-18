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

// JS CODE FOR GUEST.HTML    *****************************************************************

document.getElementById("guest-posting-closePopup").addEventListener("click", function() {
    document.getElementById("guest-posting-popup").style.display = "none";
});

document.getElementById("guest-getter-closePopup").addEventListener("click", function() {
    document.getElementById("guest-getter-popup").style.display = "none";
});


document.getElementById("bookingRegisterForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("guest-posting-responseHeading");
    const message = document.getElementById("guest-posting-responseMessage");
    const popup = document.getElementById("guest-posting-popup");
    postingFunction(this, "http://localhost:8081/api/guest/create-booking", "Below is your registration detail", "Your booking is created successfully", heading, message, popup);
});

document.getElementById("foodRegisterForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("guest-posting-responseHeading");
    const message = document.getElementById("guest-posting-responseMessage");
    const popup = document.getElementById("guest-posting-popup");
    postingFunction(this, "http://localhost:8081/api/guest/order-food", "Below is your order detail", "Your food order is created successfully", heading, message, popup);
});


document.getElementById("guestCancelLastBooking").addEventListener("click", function() {                      ////////////////////////////////////////////////////
    const confirmation = confirm("Are you sure you want to cancel your last booking?");
    if (!confirmation) {
        return;
    }

    const heading = document.getElementById("guest-getter-responseHeading");
    const message = document.getElementById("guest-getter-responseMessage");
    const popup = document.getElementById("guest-getter-popup");
    getOrPutFunction("PUT", "http://localhost:8081/api/guest/cancel-last-booking", table, popup, heading, "Your Booking is CANCELLED successfully");
});


document.getElementById("guestGetAllBooking").addEventListener("click", async function() { 
    const heading = document.getElementById("guest-getter-responseHeading");
    const message = document.getElementById("guest-getter-responseMessage");
    const popup = document.getElementById("guest-getter-popup");
    getOrPutFunction("GET", "http://localhost:8081/api/guest/get-all-my-booking", table, popup, heading, "Below is the list of all bookings by you");
});


document.getElementById("guestCheckedOutBooking").addEventListener("click", async function() { 
    const heading = document.getElementById("guest-getter-responseHeading");
    const message = document.getElementById("guest-getter-responseMessage");
    const popup = document.getElementById("guest-getter-popup");
    getOrPutFunction("GET", "http://localhost:8081/api/guest/get-all-my-checked_out-booking", table, popup, heading, "Below is the list of all checked-out bookings by you");
});

document.getElementById("guestCancelledBooking").addEventListener("click", async function() { 
    const heading = document.getElementById("guest-getter-responseHeading");
    const message = document.getElementById("guest-getter-responseMessage");
    const popup = document.getElementById("guest-getter-popup");
    getOrPutFunction("GET", "http://localhost:8081/api/guest/get-all-my-cancelled-booking", table, popup, heading, "Below is the list of all cancelled bookings by you");
});


document.getElementById("guestFoodOrder").addEventListener("click", async function() { 
    const heading = document.getElementById("guest-getter-responseHeading");
    const message = document.getElementById("guest-getter-responseMessage");
    const popup = document.getElementById("guest-getter-popup");
    getOrPutFunction("GET", "http://localhost:8081/api/guest/get-all-my-food-order", table, popup, heading, "Below is the list of all food order made by you");
});






