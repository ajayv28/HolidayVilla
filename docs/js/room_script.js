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
    const api = `${backendUrl}/api/room/check-in-with-bookingId?bookingId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Successfully Checked In");
});

document.getElementById("formCheckOutWithBookingId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("checkOutWithBookingId").value;
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `${backendUrl}/api/room/check-out-with-bookingId?bookingId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Successfully Checked Out");
});

document.getElementById("formEarlyCheckOutWithBookingId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("earlyCheckOutWithBookingId").value;
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `${backendUrl}/api/room/early-check-out-with-bookingId?bookingId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Successfully (Early) Checked Out");
});

document.getElementById("formChangeRoomStatusByRoomNo").addEventListener("submit", function(event){ 
    event.preventDefault();
    const room = document.getElementById("changeRoomStatusByRoomNoRoomNo").value;
    const status = document.getElementById("changeRoomStatusByRoomNoStatus").value;
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `${backendUrl}/api/room/change-room-status-by-roomNo?roomNo=${room}&roomStatus=${status}`;
    getOrPutFunction("PUT", api, table, popup, heading, `Successfully Room No. ${room}'s Status Changed to ${status}`);
});

document.getElementById("formGetAllTodayInHouseRoom").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `${backendUrl}/api/room/get-all-today-inhouse-room`;
    getOrPutFunction("GET", api, table, popup, heading, "Today's In-House Rooms:");
});

document.getElementById("formGetCountOfTodayInHouseRoom").addEventListener("submit", async function(event){ 
    event.preventDefault();
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `${backendUrl}/api/room/get-count-of-today-inhouse-room`;
    //getOrPutFunction("GET", api, table, popup, heading, `Today's Total In-House Rooms: `);
    const response = await fetch(api, {
        method: "GET", 
        });

    const value = await response.text();
    
    heading.innerText = `Today's Total In-House Rooms: ${value}`;
    table.innerHTML = "";
    popup.style.display = "block";
});

document.getElementById("formGetAllTodayInHouseGuest").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `${backendUrl}/api/room/get-all-today-inhouse-guest`;
    getOrPutFunction("GET", api, table, popup, heading, "Today's In-House Guests:");
});

document.getElementById("formGetAllRoomByRoomStatus").addEventListener("submit", function(event){ 
    event.preventDefault();
    const status = document.getElementById("getAllRoomByRoomStatus").value;
    const heading = document.getElementById("room-getter-responseHeading");
    const table = document.getElementById("room-getter-responseTable");
    const popup = document.getElementById("room-getter-popup");
    const api = `${backendUrl}/api/room/get-all-room-by-room-status?roomStatus=${status}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Rooms with Status - ${status}`);
});


document.getElementById("formOrderCompensationFoodRoomController").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("room-posting-responseHeading");
    const message = document.getElementById("room-posting-responseMessage");
    const popup = document.getElementById("room-posting-popup");
    postingFunction(this, `${backendUrl}/api/food-order/order-compensation-food`, "Below is order detail", "Food order is created successfully", heading, message, popup);
});








