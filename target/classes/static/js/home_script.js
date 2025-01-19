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
    //window.location.href = "/home.html";  
    window.location.reload(true);  
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
    postingFunction(this, "https://holidayvilla-production.up.railway.app/api/guest/register", "Below is your account detail", "Your account is created successfully", heading, message, popup);
});





