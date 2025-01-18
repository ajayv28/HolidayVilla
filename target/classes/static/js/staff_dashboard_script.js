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

