const links = document.querySelectorAll(".header a");

links.forEach(link => {
    if (link.href === window.location.href) {
        link.classList.add("active"); 
    }
});


document.getElementById("logoutButton").addEventListener("click", async function() {
    await fetch("http://localhost:8081/logout", {
        method: "POST",
        credentials: "include"
    });
    window.location.href = "/home.html";  
});



// FUNCTIONS RELATED TO - POSTING & GET OR PUT FUNCTION  *************************************************************************

function camelCaseToNormal(camelCaseString) {
    const result = camelCaseString.replace(/([a-z])([A-Z])/g, "$1 $2"); //inserting space between each caps
    return result.replace(/\b\w/g, char => char.toUpperCase()); //capitalizing all first letter
}

function jsonToText(headerText, json, space) {
    let extraSpace = "";
    for (let i = 0; i <space; i++) {
        extraSpace += "."; 
    }
    let text = `${extraSpace}${camelCaseToNormal(headerText)}\n`; 

    for (const [key, value] of Object.entries(json)) {
        const formattedKey = camelCaseToNormal(key);
        if (typeof value === "object" && value !== null) {
            text += jsonToText(formattedKey, value, space + 1); 
        } else {
            text += `${extraSpace} ${formattedKey}: ${value}\n`; // Add two spaces before the key-value pair
        }
    }
    return text; 
}


// FUNCTIONS RELATED TO - POSTING FUNCTION  *************************************************************************

async function postingFunction(thisElement, api, customMessage, headingMessage, heading, message, popup) {
    const formData = new FormData(thisElement);
    const data = Object.fromEntries(formData.entries());
    try{
        const response = await fetch(api, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        switch(response.status){
            case 201:
                const jsonResponse = await response.json();
                  heading.innerText = headingMessage;
                  message.innerText = jsonToText(customMessage, jsonResponse, 0);
                  popup.style.display = "block";
                  break;

            default:
                const jsonBodyResponse = await response.json();
                heading.innerText = `Error code - ${jsonBodyResponse.status}`;
                message.innerText = `${jsonBodyResponse.message}` + "\n" + "Kindly contact the admin / IT team if this error keeps on generating";
                popup.style.display = "block";
                break;
        }
    }catch(error){
        heading.innerText = `Error - ${error.message}`;
        message.innerText = "Kindly contact the admin / IT team to resolve this. \n Feel free to drop an email with screenshot of this page to info@holidayvilla.com";
        popup.style.display = "block";
    }
}




// FUNCTIONS RELATED TO - GET / PUT  FUNCTION    *****************************************************************


function extractKeys(obj, headers) {
    for (const key in obj) {
        if (obj.hasOwnProperty(key)) {
            headers.add(key); 
        }
    }
}

function getValue(obj, key) {
    return obj[key];
}


async function getOrPutFunction(getOrPut, api, table, popup, heading, headingMessage) {  

    try { 
    const responseValue = await fetch(api, {
    method: getOrPut, 
    });
    
    switch (responseValue.status) {
    case 200:
        const responseList = await responseValue.json();
        heading.innerText = headingMessage;
        table.innerHTML = ""; 

        const headers = new Set();
        responseList.forEach(response => {
            extractKeys(response, headers);
        });

        const thead = document.createElement("thead");
        const headerRow = document.createElement("tr");
        headers.forEach(header => {
            const th = document.createElement("th");
            th.textContent = camelCaseToNormal(header).replace(/Response/g, "");
            headerRow.appendChild(th);
        });
        thead.appendChild(headerRow);
        table.appendChild(thead);

        const tbody = document.createElement("tbody");
        responseList.forEach(response => {
            const row = document.createElement("tr");
            headers.forEach(header => {
                const td = document.createElement("td");
                const value = getValue(response, header);
                if (header.includes("guestResponse")) {
                    const guestDetails = response.guestResponse;
                    td.textContent = jsonToText("Guest Details", guestDetails, 0);                    /////////////////////////////////////////////////////////////
                } else if (header.includes("roomResponse")) {
                    const roomDetails = response.roomResponse;
                    td.textContent = jsonToText("Room Details", roomDetails, 0); 
                } else {
                    td.textContent = value !== undefined ? value : "N/A"; 
                }
                row.appendChild(td);
            });
            tbody.appendChild(row);
        });
        table.appendChild(tbody);

        popup.style.display = "block";
        break;

    default: 
        const response = await response.json();
        heading.innerText = `${response.message} (Error code - ${response.status})`;
        table.innerHTML = ""; 
        popup.style.display = "block";
        break;
    }
    }catch(error){ 
        heading.innerText = `Error: ${error.message}`; 
        table.innerHTML = ""; 
        popup.style.display = "block";
    }
}
















// JS CODE FOR BOOKING.HTML    *****************************************************************

document.getElementById("booking-posting-closePopup").addEventListener("click", function() {
    document.getElementById("booking-posting-popup").style.display = "none";
});

document.getElementById("booking-getter-closePopup").addEventListener("click", function() {
    document.getElementById("booking-getter-popup").style.display = "none";
});


document.getElementById("formChangeBookingRoomIfPossible").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("changeBookingRoomBookingId").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/change-booking-room-ifPossible?bookingId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Booking Room is changed successfully");
});

document.getElementById("formCancelBookingByBookingId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("cancelBookingByBookingId").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/cancel-booking-by-bookingId?bookingId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Booking is cancelled successfully");
});

document.getElementById("formCancelLastBookingByGuestEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const email = document.getElementById("cancelLastBookingByGuestEmail").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/cancel-last-booking-by-guestEmail?guestEmail=${email}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Booking is cancelled successfully");
});

document.getElementById("formGetBookingByBookingId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("getBookingByBookingId").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-booking-by-bookingId?bookingId=${id}`;
    getOrPutFunction("GET", api, table, popup, heading, "Booking Detail for given Booking ID:");
});

document.getElementById("formGetAllBookingByGuestEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getAllBookingByGuestEmail").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-booking-by-guestEmail?guestEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Bookings by given Guest:");
});

document.getElementById("formGetAllBookingBetweenDates").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("getAllBookingBetweenDatesFrom").value;
    const to = document.getElementById("getAllBookingBetweenDatesTo").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-booking-between-dates?fromDate=${from}&toDate=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Bookings between given dates:");
});

document.getElementById("formGetAllBookingOccupiedOnGivenDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const date = document.getElementById("getAllBookingOccupiedOnGivenDate").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-booking-occupied-on-given-date?date=${date}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Bookings on given date:");
});

document.getElementById("formGetAllUpcomingArrivalBooking").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = "http://localhost:8081/api/booking/get-all-upcoming-arrival-booking";
    getOrPutFunction("GET", api, table, popup, heading, "All Arrival Bookings:");
});

document.getElementById("formGetAllUpcomingArrivalBookingByRoomNo").addEventListener("submit", function(event){ 
    event.preventDefault();
    const room = document.getElementById("getAllUpcomingArrivalBookingByRoomNo").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-upcoming-arrival-booking-by-roomNo?roomNo=${room}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Arrival Bookings of given Room:");
});

document.getElementById("formGetAllUpcomingArrivalBookingByGuestEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getAllUpcomingArrivalBookingByGuestEmail").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-upcoming-arrival-booking-by-guestEmail?guestEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Arrival Bookings of given Guest:");
});

document.getElementById("formGetAllUpcomingArrivalStayMoreThanNDays").addEventListener("submit", function(event){ 
    event.preventDefault();
    const n = document.getElementById("getAllUpcomingArrivalStayMoreThanNDays").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-upcoming-arrival-stay-more-than-n-days?n=${n}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Arrival Bookings staying more than ${n} days:`);
});


document.getElementById("formGetAllCheckedOutBookingBetweenDates").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("getAllCheckedOutBookingBetweenDatesFrom").value;
    const to = document.getElementById("getAllCheckedOutBookingBetweenDatesTo").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-checkedOut-booking-between-dates?fromDate=${from}&toDate=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Checked Out Bookings between given dates:");
});

document.getElementById("formGetAllCheckedOutBookingByGuestEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getAllCheckedOutBookingByGuestEmail").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-checked_out-booking-by-guestEmail?guestEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Checked Out Bookings of given Guest:");
});

document.getElementById("formGetAllCancelledBookingBetweenDates").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("getAllCancelledBookingBetweenDatesFrom").value;
    const to = document.getElementById("getAllCancelledBookingBetweenDatesTo").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-cancelled-booking-between-dates?fromDate=${from}&toDate=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Cancelled Bookings between given dates:");
});

document.getElementById("formGetAllCancelledBookingByGuestEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getAllCancelledBookingByGuestEmail").value;
    const heading = document.getElementById("booking-getter-responseHeading");
    const table = document.getElementById("booking-getter-responseTable");
    const popup = document.getElementById("booking-getter-popup");
    const api = `http://localhost:8081/api/booking/get-all-cancelled-booking-by-guestEmail?guestEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Cancelled Bookings of given Guest:");
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
    postingFunction(this, "http://localhost:8081/api/coupon/register", "Below is your coupon detail", "Your coupon is created successfully", heading, message, popup);
});


document.getElementById("couponChangeQuantityForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const code = document.getElementById("couponCode").value;
    const qty = document.getElementById("newQuantity").value;
    const heading = document.getElementById("coupon-getter-responseHeading");
    const table = document.getElementById("coupon-getter-responseTable");
    const popup = document.getElementById("coupon-getter-popup");
    const api = `http://localhost:8081/api/coupon/changeQuantity?couponCode=${code}&newQuantity=${qty}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Coupon is edited successfully");
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
    postingFunction(this, "http://localhost:8081/api/food-order/order-compensation-food", "Below is order detail", "Food order is created successfully", heading, message, popup);
});

document.getElementById("formGetAllTodayFoodOrder").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = "http://localhost:8081/api/food-order/get-all-today-food-order";
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders dated Today:");
});

document.getElementById("formGetFoodOrderByOrderId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("getFoodOrderByOrderId").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `http://localhost:8081/api/food-order/get-food-order-by-orderId?orderId=${id}`;
    getOrPutFunction("GET", api, table, popup, heading, "Food Orders detail of given Order ID:");
});

document.getElementById("formGetAllFoodOrderByFoodType").addEventListener("submit", function(event){ 
    event.preventDefault();
    const type = document.getElementById("getAllFoodOrderByFoodType").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `http://localhost:8081/api/food-order/get-all-food-order-by-foodType?foodType=${type}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders of given food type:");
});

document.getElementById("formGetAllFoodOrderByOrderDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const date = document.getElementById("getAllFoodOrderByOrderDate").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `http://localhost:8081/api/food-order/get-all-food-order-by-orderDate?date=${date}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders of given food type:");
});

document.getElementById("formGetAllFoodOrderByOrderDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const date = document.getElementById("getAllFoodOrderByOrderDate").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `http://localhost:8081/api/food-order/get-all-food-order-by-orderDate?date=${date}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders on given order date:");
});

document.getElementById("formGetAllFoodOrderByRoomNo").addEventListener("submit", function(event){ 
    event.preventDefault();
    const room = document.getElementById("getAllFoodOrderByRoomNo").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `http://localhost:8081/api/food-order/get-all-food-order-by-roomNo?roomNo=${room}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders on given room number:");
});

document.getElementById("formGetAllFoodOrderByGuestEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getAllFoodOrderByGuestEmail").value;
    const heading = document.getElementById("food_order-getter-responseHeading");
    const table = document.getElementById("food_order-getter-responseTable");
    const popup = document.getElementById("food_order-getter-popup");
    const api = `http://localhost:8081/api/food-order/get-all-food-order-by-guestEmail?guestEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Food Orders of given Guest:");
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










// JS CODE FOR HOME.HTML    *****************************************************************

document.getElementById("home-posting-closePopup").addEventListener("click", function() {
    document.getElementById("home-posting-popup").style.display = "none";
});



document.getElementById("guestRegisterForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("home-posting-responseHeading");
    const message = document.getElementById("home-posting-responseMessage");
    const popup = document.getElementById("home-posting-popup");
    postingFunction(this, "http://localhost:8081/api/guest/register", "Below is your account detail", "Your account is created successfully", heading, message, popup);
});









// JS CODE FOR MAINTENANCE.HTML    *****************************************************************

document.getElementById("maintenance-posting-closePopup").addEventListener("click", function() {
    document.getElementById("maintenance-posting-popup").style.display = "none";
});

document.getElementById("maintenance-getter-closePopup").addEventListener("click", function() {
    document.getElementById("maintenance-getter-popup").style.display = "none";
});


document.getElementById("formPostMaintenanceJob").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("maintenance-posting-responseHeading");
    const message = document.getElementById("maintenance-posting-responseMessage");
    const popup = document.getElementById("maintenance-posting-popup");
    const api = "http://localhost:8081/api/maintenance/post-maintenance-job";
    postingFunction(this, api, "Below is your mainteance job detail", "Your maintenance job is created successfully", heading, message, popup);
});

document.getElementById("formUpdateFollowupsByMaintenanceId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("updateFollowupsByMaintenanceIdMaintenanceId").value;
    const msg = document.getElementById("updateFollowupsByMaintenanceIdNewFollowup").value;
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = `http://localhost:8081/api/maintenance/update-followups-by-maintenanceId?maintenanceId=${id}&newFollowup=${encodeURIComponent(msg)}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Entered followup message is updated successfully");
});

document.getElementById("formAllVacantRoomsDueForMaintenance").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = "http://localhost:8081/api/maintenance/all-vacant-rooms-due-for-maintenance";
    getOrPutFunction("GET", api, table, popup, heading, "All Vacant Rooms due for Maintenance");
});

document.getElementById("formAllRoomsWithFollowups").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = "http://localhost:8081/api/maintenance/all-rooms-with-followups";
    getOrPutFunction("GET", api, table, popup, heading, "All Guest Rooms with FollowUps");
});

document.getElementById("formAllMaintenanceWithFollowupsByRoomNo").addEventListener("submit", function(event){ 
    event.preventDefault();
    const room = document.getElementById("allMaintenanceWithFollowupsByRoomNo").value;
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = `http://localhost:8081/api/maintenance/all-maintenance-with-followups-by-roomNo?roomNo=${room}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Maintenance with FollowUps in Room No. ${room}`);
});

document.getElementById("formAllMaintenanceBetweenDates").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("allMaintenanceBetweenDatesFrom").value;
    const to = document.getElementById("allMaintenanceBetweenDatesTo").value;
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = `http://localhost:8081/api/maintenance/all-maintenance-between-dates?fromDate=${from}&toDate=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, `All maintenance conducted between ${from} and ${to}`);
});

document.getElementById("formAllMaintenanceByLoggedInStaffBetweenDates").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("allMaintenanceByLoggedInStaffBetweenDatesFrom").value;
    const to = document.getElementById("allMaintenanceByLoggedInStaffBetweenDatesTo").value;
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = `http://localhost:8081/api/maintenance/all-maintenance-by-logged-in-staff-between-dates?fromDate=${from}&toDate=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, `All maintenance conducted by you between ${from} and ${to}`);
});

document.getElementById("formAllMaintenanceByStaffEmailBetweenDates").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("allMaintenanceByStaffEmailBetweenDatesEmail").value;
    const from = document.getElementById("allMaintenanceByStaffEmailBetweenDatesFrom").value;
    const to = document.getElementById("allMaintenanceByStaffEmailBetweenDatesTo").value;
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = `http://localhost:8081/api/maintenance/all-maintenance-by-staffEmail-between-dates?fromDate=${from}&toDate=${to}&staffEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, `All maintenance conducted by given staff between ${from} and ${to}`);
});











// JS CODE FOR MATERIAL_REQUISITION.HTML    *****************************************************************

document.getElementById("materialRequisition-posting-closePopup").addEventListener("click", function() {
    document.getElementById("materialRequisition-posting-popup").style.display = "none";
});

document.getElementById("materialRequisition-getter-closePopup").addEventListener("click", function() {
    document.getElementById("materialRequisition-getter-popup").style.display = "none";
});

document.getElementById("formGetMaterialRequisitionByRequisitionId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("getMaterialRequisitionByRequisitionId").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/get-material-requisition-by-requisitionId?requisitionId=${id}`;
    getOrPutFunction("GET", api, table, popup, heading, "Material Requisition details of given Requisition ID:");
});

document.getElementById("formMarkReceivedByRequisitionId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("markReceivedByRequisitionId").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/mark-received-by-requisitionId?requisitionId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Material Requisition is marked received for given Requisition ID:");
});

document.getElementById("formProcessRequisitionByRequisitionId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("processRequisitionByRequisitionId").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/process-requisition-by-requisitionId?requisitionId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Material Requisition is processed for given Requisition ID:");
});

document.getElementById("formFollowUpOnAllElapsedRequisition").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = "http://localhost:8081/api/material-requisition/followUp-on-all-elapsed-requisition";
    getOrPutFunction("GET", api, table, popup, heading, "Followup mail sent to all elapsed delivery dated Material Requisitions");
});

document.getElementById("formChangeExpectedDeliveryDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("changeExpectedDeliveryDateRequisitionId").value;
    const date = document.getElementById("changeExpectedDeliveryDateNewDate").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/change-expected-delivery-date?requisitionId=${id}&newDate=${date}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Delivery Date changed for below Material Requisition:");
});

document.getElementById("formChangeRequisitionQuantity").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("changeRequisitionQuantityRequisitionId").value;
    const qty = document.getElementById("changeRequisitionQuantityNewQuantity").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/change-requisition-quantity?requisitionId=${id}&newQuantity=${qty}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Quantity changed for below Material Requisition:");
});

document.getElementById("formGetAllInProgressMaterialRequisition").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = "http://localhost:8081/api/material-requisition/get-all-inprogress-material-requisition";
    getOrPutFunction("GET", api, table, popup, heading, "All In-Progress Material Requisitions:");
});

document.getElementById("formGetAllInProgressMaterialRequisitionByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllInProgressMaterialRequisitionByDepartment").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/get-all-inprogress-material-requisition-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All In-Progress Material Requisitions of ${dept}`);
});

document.getElementById("formGetAllNotProcessedMaterialRequisition").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = "http://localhost:8081/api/material-requisition/get-all-not-processed-material-requisition";
    getOrPutFunction("GET", api, table, popup, heading, "All Not-Processed Material Requisitions:");
});

document.getElementById("formGetAllNotProcessedMaterialRequisitionByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllNotProcessedMaterialRequisitionByDepartment").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/get-all-not-processed-material-requisition-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Not-Processed Material Requisitions of ${dept}`);
});

document.getElementById("formGetAllCancelledMaterialRequisition").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = "http://localhost:8081/api/material-requisition/get-all-cancelled-material-requisition";
    getOrPutFunction("GET", api, table, popup, heading, "All Cancelled Material Requisitions:");
});

document.getElementById("formGetAllCancelledMaterialRequisitionByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllCancelledMaterialRequisitionByDepartment").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/get-all-cancelled-material-requisition-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Cancelled Material Requisitions of ${dept}`);
});

document.getElementById("formGetAllMaterialRequisitionByDeliveryDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const date = document.getElementById("getAllMaterialRequisitionByDeliveryDate").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/get-all-material-requisition-by-deliveryDate?date=${date}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Material Requisition Delivery Expected on ${date}`);
});

document.getElementById("formGetAllMaterialRequisitionBetweenDeliveryDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("getAllMaterialRequisitionBetweenDeliveryDateFrom").value;
    const to = document.getElementById("getAllMaterialRequisitionBetweenDeliveryDateTo").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/get-all-material-requisition-between-deliveryDate?fromDate=${from}&toDate=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Material Requisition Delivery Expected between ${from} and ${to}`);
});

document.getElementById("formGetAllMaterialRequisitionByMaterialName").addEventListener("submit", function(event){ 
    event.preventDefault();
    const name = document.getElementById("getAllMaterialRequisitionByMaterialName").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/get-all-material-requisition-by-materialName?materialName=${name}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Material Requisition of ${name}`);
});

document.getElementById("formGetAllMaterialRequisitionByStaffEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getAllMaterialRequisitionByStaffEmail").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/get-all-material-requisition-by-staffEmail?staffEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Material Requisition requested by given staff");
});

document.getElementById("formGetAllReceivedMaterialRequisitionByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllReceivedMaterialRequisitionByDepartment").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/get-all-received-material-requisition-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Material Requisition requested for ${dept}`);
});













// JS CODE FOR MATERIAL.HTML    *****************************************************************

document.getElementById("material-posting-closePopup").addEventListener("click", function() {
    document.getElementById("material-posting-popup").style.display = "none";
});

document.getElementById("material-getter-closePopup").addEventListener("click", function() {
    document.getElementById("material-getter-popup").style.display = "none";
});


document.getElementById("formAddMaterial").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("material-posting-responseHeading");
    const message = document.getElementById("material-posting-responseMessage");
    const popup = document.getElementById("material-posting-popup");
    const api = "http://localhost:8081/api/material/addMaterial";
    postingFunction(this, api, "Below is your material detail", "Your materials is added to database successfully", heading, message, popup);
});

document.getElementById("formEditSupplierEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const name = document.getElementById("editSupplierEmailMaterialName").value;
    const mail = document.getElementById("editSupplierEmailNewEmail").value;
    const heading = document.getElementById("material-getter-responseHeading");
    const table = document.getElementById("material-getter-responseTable");
    const popup = document.getElementById("material-getter-popup");
    const api = `http://localhost:8081/api/material/edit-supplier-email?materialName=${name}&newEmail=${mail}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Entered email is updated successfully");
});

document.getElementById("formEditSupplierName").addEventListener("submit", function(event){ 
    event.preventDefault();
    const name = document.getElementById("editSupplierNameMaterialName").value;
    const newName = document.getElementById("editSupplierNameNewName").value;
    const heading = document.getElementById("material-getter-responseHeading");
    const table = document.getElementById("material-getter-responseTable");
    const popup = document.getElementById("material-getter-popup");
    const api = `http://localhost:8081/api/material/edit-supplier-name?materialName=${encodeURIComponent(name)}&newName=${encodeURIComponent(newName)}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Entered new supplier name is updated successfully");
});

document.getElementById("formEditPrice").addEventListener("submit", function(event){ 
    event.preventDefault();
    const name = document.getElementById("editPriceMaterialName").value;
    const newPrice = document.getElementById("editPriceNewPrice").value;
    const heading = document.getElementById("material-getter-responseHeading");
    const table = document.getElementById("material-getter-responseTable");
    const popup = document.getElementById("material-getter-popup");
    const api = `http://localhost:8081/api/material/edit-price?materialName=${name}&newPrice=${newPrice}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Entered new price is updated successfully");
});












// JS CODE FOR ROOM.HTML    *****************************************************************

document.getElementById("room-posting-closePopup").addEventListener("click", function() {
    document.getElementById("room-posting-popup").style.display = "none";
});

document.getElementById("room-getter-closePopup").addEventListener("click", function() {
    document.getElementById("room-getter-popup").style.display = "none";
});



document.getElementById("formCheckInWithBookingId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("checkInWithBookingId").value;
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `http://localhost:8081/api/room/check-in-with-bookingId?bookingId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Successfully Checked In");
});

document.getElementById("formCheckOutWithBookingId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("checkOutWithBookingId").value;
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `http://localhost:8081/api/room/check-out-with-bookingId?bookingId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Successfully Checked Out");
});

document.getElementById("formEarlyCheckOutWithBookingId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("earlyCheckOutWithBookingId").value;
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `http://localhost:8081/api/room/early-check-out-with-bookingId?bookingId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Successfully (Early) Checked Out");
});

document.getElementById("formChangeRoomStatusByRoomNo").addEventListener("submit", function(event){ 
    event.preventDefault();
    const room = document.getElementById("changeRoomStatusByRoomNoRoomNo").value;
    const status = document.getElementById("changeRoomStatusByRoomNoStatus").value;
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `http://localhost:8081/api/room/change-room-status-by-roomNo?roomNo=${room}&roomStatus=${status}`;
    getOrPutFunction("PUT", api, table, popup, heading, `Successfully Room No. ${room}'s Status Changed to ${status}`);
});

document.getElementById("formGetAllTodayInHouseRoom").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = "http://localhost:8081/api/room/get-all-today-inhouse-room";
    getOrPutFunction("GET", api, table, popup, heading, "Today's In-House Rooms:");
});

document.getElementById("formGetCountOfTodayInHouseRoom").addEventListener("submit", async function(event){ 
    event.preventDefault();
    const heading = document.getElementById("room-getter-responseHeading");
    const popup = document.getElementById("room-getter-popup");
    const api = "http://localhost:8081/api/room/get-count-of-today-inhouse-room";
    //getOrPutFunction("GET", api, table, popup, heading, `Today's Total In-House Rooms: `);
    const response = await fetch(api, {
        method: "GET", 
        });
    heading.innerText = `Today's Total In-House Rooms: ${response}`;
    popup.style.display = "block";
});

document.getElementById("formGetAllTodayInHouseGuest").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = "http://localhost:8081/api/room/get-all-today-inhouse-guest";
    getOrPutFunction("GET", api, table, popup, heading, "Today's In-House Guests:");
});

document.getElementById("formGetAllRoomByRoomStatus").addEventListener("submit", function(event){ 
    event.preventDefault();
    const status = document.getElementById("getAllRoomByRoomStatus").value;
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `http://localhost:8081/api/room/get-all-room-by-room-status?roomStatus=${status}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Rooms with Status - ${status}`);
});


document.getElementById("formOrderCompensationFoodRoomController").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("room-posting-responseHeading");
    const message = document.getElementById("room-posting-responseMessage");
    const popup = document.getElementById("room-posting-popup");
    postingFunction(this, "http://localhost:8081/api/food-order/order-compensation-food", "Below is order detail", "Food order is created successfully", heading, message, popup);
});










// JS CODE FOR STAFF_DASHBOARD.HTML    *****************************************************************
document.getElementById("staffDashboard-posting-closePopup").addEventListener("click", function() {
    document.getElementById("staffDashboard-posting-popup").style.display = "none";
});

document.getElementById("staffDashboard-getter-closePopup").addEventListener("click", function() {
    document.getElementById("staffDashboard-getter-popup").style.display = "none";
});


document.getElementById("formMaterialRequisitionRegister").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("staffDashboard-posting-responseHeading");
    const message = document.getElementById("staffDashboard-posting-responseMessage");
    const popup = document.getElementById("staffDashboard-posting-popup");
    const api = "http://localhost:8081/api/material-requisition/raise-requisition";
    postingFunction(this, api, "Below is your material requisition detail", "Your material requisition is raised successfully", heading, message, popup);
});

document.getElementById("formCancelRequisition").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("cancelRequisition").value;
    const heading = document.getElementById("staffDashboard-getter-responseHeading");
    const table = document.getElementById("staffDashboard-getter-responseTable");
    const popup = document.getElementById("staffDashboard-getter-popup");
    const api = `http://localhost:8081/api/material-requisition/cancel-requisition?requisitionId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Entered email is updated successfully");
});















// JS CODE FOR STAFF.HTML    *****************************************************************

document.getElementById("staff-posting-closePopup").addEventListener("click", function() {
    document.getElementById("staff-posting-popup").style.display = "none";
});

document.getElementById("staff-getter-closePopup").addEventListener("click", function() {
    document.getElementById("staff-getter-popup").style.display = "none";
});


document.getElementById("staffRegisterForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("staff-posting-responseHeading");
    const message = document.getElementById("staff-posting-responseMessage");
    const popup = document.getElementById("staff-posting-popup");
    const api = "http://localhost:8081/api/staff/onBoard";
    postingFunction(this, api, "Below is the onboarded staff detail", "Staff is on-boarded successfully", heading, message, popup);
});

document.getElementById("formOffBoardStaff").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("offBoardStaff").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `http://localhost:8081/api/staff/offBoard?staffEmail=${mail}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Staff Off-Boarded Successfully");
});

document.getElementById("formGetStaffByStaffEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getStaffByStaffEmail").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `http://localhost:8081/api/staff/get-staff-by-staffEmail?staffEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "Staff Detail:");
});

document.getElementById("formResetPassword").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("resetPasswordEmail").value;
    const pass = document.getElementById("resetPasswordPassword").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `http://localhost:8081/api/staff/reset-password?staffEmail=${mail}&newPassword=${pass}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Password Reset Successful:");
});

document.getElementById("formChangeDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("changeDepartmentStaffEmail").value;
    const dept = document.getElementById("changeDepartmentDepartment").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `http://localhost:8081/api/staff/change-department?staffEmail=${mail}&department=${dept}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Department Change Successful:");
});

document.getElementById("getAllCurrentStaff").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = "http://localhost:8081/api/staff/all-current-staff";
    getOrPutFunction("GET", api, table, popup, heading, "All Current Staff");
});


document.getElementById("getAllExStaff").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = "http://localhost:8081/api/staff/all-ex-staff";
    getOrPutFunction("GET", api, table, popup, heading, "All Ex Staff");
});

document.getElementById("formGetAllCurrentStaffByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllCurrentStaffByDepartment").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `http://localhost:8081/api/staff/all-current-staff-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Current Staff of ${dept}` );
});

document.getElementById("formGetAllExStaffByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllExStaffByDepartment").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `http://localhost:8081/api/staff/all-ex-staff-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Ex Staff of ${dept}` );
});

document.getElementById("formGetStaffSalaryByStaffEmail").addEventListener("submit", async function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getStaffSalaryByStaffEmail").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const popup = document.getElementById("staff-getter-popup");
    const api = `http://localhost:8081/api/staff/get-staff-salary-by-staffEmail?staffEmail=${mail}`;
    const response = await fetch(api, {
        method: "GET", 
        });
    heading.innerText = `Salary of given staff: ${response}`;
    popup.style.display = "block";
});

document.getElementById("formChangeStaffSalaryByStaffEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("changeStaffSalaryByStaffEmailStaffEmail").value;
    const salary = document.getElementById("changeStaffSalaryByStaffEmailNewSalary").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `http://localhost:8081/api/staff/change-staff-salary-by-staffEmail?staffEmail=${mail}&newSalary=${salary}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Salary of given staff updated successfully" );
});

document.getElementById("createTransactionForPayroll").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = "http://localhost:8081/api/staff/create-transaction-for-payroll";
    getOrPutFunction("PUT", api, table, popup, heading, "Salary Transactions has been created for all current staff successfully" );
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