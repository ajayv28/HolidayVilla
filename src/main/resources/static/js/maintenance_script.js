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
    const api = "https://holidayvilla-production.up.railway.app/api/maintenance/post-maintenance-job";
    postingFunction(this, api, "Below is your mainteance job detail", "Your maintenance job is created successfully", heading, message, popup);
});

document.getElementById("formUpdateFollowupsByMaintenanceId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("updateFollowupsByMaintenanceIdMaintenanceId").value;
    const msg = document.getElementById("updateFollowupsByMaintenanceIdNewFollowup").value;
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/maintenance/update-followups-by-maintenanceId?maintenanceId=${id}&newFollowup=${encodeURIComponent(msg)}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Entered followup message is updated successfully");
});

document.getElementById("formAllVacantRoomsDueForMaintenance").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/maintenance/all-vacant-rooms-due-for-maintenance";
    getOrPutFunction("GET", api, table, popup, heading, "All Vacant Rooms due for Maintenance");
});

document.getElementById("formAllRoomsWithFollowups").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/maintenance/all-rooms-with-followups";
    getOrPutFunction("GET", api, table, popup, heading, "All Guest Rooms with FollowUps");
});

document.getElementById("formAllMaintenanceWithFollowupsByRoomNo").addEventListener("submit", function(event){ 
    event.preventDefault();
    const room = document.getElementById("allMaintenanceWithFollowupsByRoomNo").value;
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/maintenance/all-maintenance-with-followups-by-roomNo?roomNo=${room}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Maintenance with FollowUps in Room No. ${room}`);
});

document.getElementById("formAllMaintenanceBetweenDates").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("allMaintenanceBetweenDatesFrom").value;
    const to = document.getElementById("allMaintenanceBetweenDatesTo").value;
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/maintenance/all-maintenance-between-dates?fromDate=${from}&toDate=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, `All maintenance conducted between ${from} and ${to}`);
});

document.getElementById("formAllMaintenanceByLoggedInStaffBetweenDates").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("allMaintenanceByLoggedInStaffBetweenDatesFrom").value;
    const to = document.getElementById("allMaintenanceByLoggedInStaffBetweenDatesTo").value;
    const heading = document.getElementById("maintenance-getter-responseHeading");
    const table = document.getElementById("maintenance-getter-responseTable");
    const popup = document.getElementById("maintenance-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/maintenance/all-maintenance-by-logged-in-staff-between-dates?fromDate=${from}&toDate=${to}`;
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
    const api = `https://holidayvilla-production.up.railway.app/api/maintenance/all-maintenance-by-staffEmail-between-dates?fromDate=${from}&toDate=${to}&staffEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, `All maintenance conducted by given staff between ${from} and ${to}`);
});









