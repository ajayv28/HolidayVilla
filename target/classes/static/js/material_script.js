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






