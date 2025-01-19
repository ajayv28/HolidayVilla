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
    window.location.href = "/home.html";  
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
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/get-material-requisition-by-requisitionId?requisitionId=${id}`;
    getOrPutFunction("GET", api, table, popup, heading, "Material Requisition details of given Requisition ID:");
});

document.getElementById("formMarkReceivedByRequisitionId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("markReceivedByRequisitionId").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/mark-received-by-requisitionId?requisitionId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Material Requisition is marked received for given Requisition ID:");
});

document.getElementById("formProcessRequisitionByRequisitionId").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("processRequisitionByRequisitionId").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/process-requisition-by-requisitionId?requisitionId=${id}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Material Requisition is processed for given Requisition ID:");
});

document.getElementById("formFollowUpOnAllElapsedRequisition").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/material-requisition/followUp-on-all-elapsed-requisition";
    getOrPutFunction("GET", api, table, popup, heading, "Followup mail sent to all elapsed delivery dated Material Requisitions");
});

document.getElementById("formChangeExpectedDeliveryDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("changeExpectedDeliveryDateRequisitionId").value;
    const date = document.getElementById("changeExpectedDeliveryDateNewDate").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/change-expected-delivery-date?requisitionId=${id}&newDate=${date}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Delivery Date changed for below Material Requisition:");
});

document.getElementById("formChangeRequisitionQuantity").addEventListener("submit", function(event){ 
    event.preventDefault();
    const id = document.getElementById("changeRequisitionQuantityRequisitionId").value;
    const qty = document.getElementById("changeRequisitionQuantityNewQuantity").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/change-requisition-quantity?requisitionId=${id}&newQuantity=${qty}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Quantity changed for below Material Requisition:");
});

document.getElementById("formGetAllInProgressMaterialRequisition").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-inprogress-material-requisition";
    getOrPutFunction("GET", api, table, popup, heading, "All In-Progress Material Requisitions:");
});

document.getElementById("formGetAllInProgressMaterialRequisitionByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllInProgressMaterialRequisitionByDepartment").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-inprogress-material-requisition-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All In-Progress Material Requisitions of ${dept}`);
});

document.getElementById("formGetAllNotProcessedMaterialRequisition").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-not-processed-material-requisition";
    getOrPutFunction("GET", api, table, popup, heading, "All Not-Processed Material Requisitions:");
});

document.getElementById("formGetAllNotProcessedMaterialRequisitionByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllNotProcessedMaterialRequisitionByDepartment").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-not-processed-material-requisition-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Not-Processed Material Requisitions of ${dept}`);
});

document.getElementById("formGetAllCancelledMaterialRequisition").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-cancelled-material-requisition";
    getOrPutFunction("GET", api, table, popup, heading, "All Cancelled Material Requisitions:");
});

document.getElementById("formGetAllCancelledMaterialRequisitionByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllCancelledMaterialRequisitionByDepartment").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-cancelled-material-requisition-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Cancelled Material Requisitions of ${dept}`);
});

document.getElementById("formGetAllMaterialRequisitionByDeliveryDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const date = document.getElementById("getAllMaterialRequisitionByDeliveryDate").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-material-requisition-by-deliveryDate?date=${date}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Material Requisition Delivery Expected on ${date}`);
});

document.getElementById("formGetAllMaterialRequisitionBetweenDeliveryDate").addEventListener("submit", function(event){ 
    event.preventDefault();
    const from = document.getElementById("getAllMaterialRequisitionBetweenDeliveryDateFrom").value;
    const to = document.getElementById("getAllMaterialRequisitionBetweenDeliveryDateTo").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-material-requisition-between-deliveryDate?fromDate=${from}&toDate=${to}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Material Requisition Delivery Expected between ${from} and ${to}`);
});

document.getElementById("formGetAllMaterialRequisitionByMaterialName").addEventListener("submit", function(event){ 
    event.preventDefault();
    const name = document.getElementById("getAllMaterialRequisitionByMaterialName").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-material-requisition-by-materialName?materialName=${name}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Material Requisition of ${name}`);
});

document.getElementById("formGetAllMaterialRequisitionByStaffEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getAllMaterialRequisitionByStaffEmail").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-material-requisition-by-staffEmail?staffEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "All Material Requisition requested by given staff");
});

document.getElementById("formGetAllReceivedMaterialRequisitionByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllReceivedMaterialRequisitionByDepartment").value;
    const heading = document.getElementById("materialRequisition-getter-responseHeading");
    const table = document.getElementById("materialRequisition-getter-responseTable");
    const popup = document.getElementById("materialRequisition-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/material-requisition/get-all-received-material-requisition-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Material Requisition requested for ${dept}`);
});



