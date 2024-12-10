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






