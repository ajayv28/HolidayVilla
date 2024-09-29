document.getElementById("logoutButton").addEventListener("click", async function() {
    await fetch('http://localhost:8081/logout', {
        method: 'POST',
        credentials: 'include'
    });
    window.location.href = '/guest.html';  
});


// FUNCTIONS RELATED TO - POSTING & GET OR PUT FUNCTION  *************************************************************************

function camelCaseToNormal(camelCaseString) {
    const result = camelCaseString.replace(/([a-z])([A-Z])/g, '$1 $2'); //inserting space between each caps
    return result.replace(/\b\w/g, char => char.toUpperCase()); //capitalizing all first letter
}

function jsonToText(headerText, json, space) {
    let extraSpace = "";
    for (let i = 0; i <space; i++) {
        extraSpace += " "; 
    }
    let text = `${extraSpace}${camelCaseToNormal(headerText)}\n`; 

    for (const [key, value] of Object.entries(json)) {
        const formattedKey = camelCaseToNormal(key);
        if (typeof value === 'object' && value !== null) {
            text += jsonToText(formattedKey, value, space + 1); 
        } else {
            text += `${extraSpace} ${formattedKey}: ${value}\n`; // Add two spaces before the key-value pair
        }
    }
    return text.trim(); 
}


// FUNCTIONS RELATED TO - POSTING FUNCTION  *************************************************************************

async function postingFunction(thisElement, api, customMessage, headingMessage, heading, message, popup) {
    const formData = new FormData(thisElement);
    const data = Object.fromEntries(formData.entries());
    try{
        const response = await fetch(api, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        switch(response.status){
            case 201:
                const jsonResponse = await response.json();
                  heading.innerText = headingMessage;
                  message.innerText = jsonToText(customMessage, jsonResponse, 0);
                  popup.style.display = 'block';
                  break;

            default:
                const jsonBodyResponse = await response.json();
                heading.innerText = `Error code - ${jsonBodyResponse.status}`;
                message.innerText = `${jsonBodyResponse.message}` + "\n" + "Kindly contact the admin / IT team if this error keeps on generating";
                popup.style.display = 'block';
                break;
        }
    }catch(error){
        heading.innerText = `Error - ${error.message}`;
        message.innerText = "Kindly contact the admin / IT team to resolve this. \n Feel free to drop an email with screenshot of this page to info@holidayvilla.com";
        popup.style.display = 'block';
    }
}

document.getElementById('guest-posting-closePopup').addEventListener('click', function() {
    document.getElementById('guest-posting-popup').style.display = 'none';
});




// FUNCTIONS RELATED TO - GET / PUT  FUNCTION    *****************************************************************

document.getElementById('guest-getter-closePopup').addEventListener('click', function() {
    document.getElementById('guest-getter-popup').style.display = 'none';
});

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
        table.innerHTML = ''; 

        const headers = new Set();
        responseList.forEach(response => {
            extractKeys(response, headers);
        });

        const thead = document.createElement('thead');
        const headerRow = document.createElement('tr');
        headers.forEach(header => {
            const th = document.createElement('th');
            th.textContent = camelCaseToNormal(header).replace(/Response/g, '');
            headerRow.appendChild(th);
        });
        thead.appendChild(headerRow);
        table.appendChild(thead);

        const tbody = document.createElement('tbody');
        responseList.forEach(response => {
            const row = document.createElement('tr');
            headers.forEach(header => {
                const td = document.createElement('td');
                const value = getValue(response, header);
                if (header.includes('guestResponse')) {
                    const guestDetails = response.guestResponse;
                    td.textContent = jsonToText('Guest Details', guestDetails, 0); 
                } else if (header.includes('roomResponse')) {
                    const roomDetails = response.roomResponse;
                    td.textContent = jsonToText('Room Details', roomDetails, 0); 
                } else {
                    td.textContent = value !== undefined ? value : 'N/A'; 
                }
                row.appendChild(td);
            });
            tbody.appendChild(row);
        });
        table.appendChild(tbody);

        popup.style.display = 'block';
        break;

    default: 
        const response = await response.json();
        heading.innerText = `${response.message} (Error code - ${response.status})`;
        table.innerHTML = ''; 
        popup.style.display = 'block';
        break;
    }
    }catch(error){ 
        heading.innerText = `Error: ${error.message}`; 
        table.innerHTML = ''; 
        popup.style.display = 'block';
    }
}



// JS CODE FOR GUEST.HTML    *****************************************************************


document.getElementById("guestRegisterForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById('guest-posting-responseHeading');
    const message = document.getElementById('guest-posting-responseMessage');
    const popup = document.getElementById('guest-posting-popup');
    postingFunction(this, 'http://localhost:8081/api/guest/register', "Below is your account detail", "Your account is created successfully", heading, message, popup);
});

document.getElementById("bookingRegisterForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById('guest-posting-responseHeading');
    const message = document.getElementById('guest-posting-responseMessage');
    const popup = document.getElementById('guest-posting-popup');
    postingFunction(this, 'http://localhost:8081/api/guest/create-booking', "Below is your registration detail", "Your booking is created successfully", heading, message, popup);
});

document.getElementById("foodRegisterForm").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById('guest-posting-responseHeading');
    const message = document.getElementById('guest-posting-responseMessage');
    const popup = document.getElementById('guest-posting-popup');
    postingFunction(this, 'http://localhost:8081/api/guest/order-food', "Below is your order detail", "Your food order is created successfully", heading, message, popup);
});


document.getElementById("guestCancelLastBooking").addEventListener("click", function() { 
    const confirmation = confirm("Are you sure you want to cancel your last booking?");
    if (!confirmation) {
        return;
    }

    const heading = document.getElementById('guest-getter-responseHeading');
    const table = document.getElementById('guest-getter-responseTable');
    const popup = document.getElementById('guest-getter-popup');
    getOrPutFunction('PUT', 'http://localhost:8081/api/guest/cancel-last-booking', table, popup, heading, "Your Booking is CANCELLED successfully");
});


document.getElementById("guestGetAllBooking").addEventListener("click", async function() { 
    const heading = document.getElementById('guest-getter-responseHeading');
    const table = document.getElementById('guest-getter-responseTable');
    const popup = document.getElementById('guest-getter-popup');
    getOrPutFunction('GET', 'http://localhost:8081/api/guest/get-all-my-booking', table, popup, heading, "Below is the list of all bookings by you");
});


document.getElementById("guestCheckedOutBooking").addEventListener("click", async function() { 
    const heading = document.getElementById('guest-getter-responseHeading');
    const table = document.getElementById('guest-getter-responseTable');
    const popup = document.getElementById('guest-getter-popup');
    getOrPutFunction('GET', 'http://localhost:8081/api/guest/get-all-my-checked_out-booking', table, popup, heading, "Below is the list of all checked-out bookings by you");
});

document.getElementById("guestCancelledBooking").addEventListener("click", async function() { 
    const heading = document.getElementById('guest-getter-responseHeading');
    const table = document.getElementById('guest-getter-responseTable');
    const popup = document.getElementById('guest-getter-popup');
    getOrPutFunction('GET', 'http://localhost:8081/api/guest/get-all-my-cancelled-booking', table, popup, heading, "Below is the list of all cancelled bookings by you");
});


document.getElementById("guestFoodOrder").addEventListener("click", async function() { 
    const heading = document.getElementById('guest-getter-responseHeading');
    const table = document.getElementById('guest-getter-responseTable');
    const popup = document.getElementById('guest-getter-popup');
    getOrPutFunction('GET', 'http://localhost:8081/api/guest/get-all-my-food-order', table, popup, heading, "Below is the list of all food order made by you");
});





// JS CODE FOR _____.HTML    *****************************************************************


/*document.getElementById("couponRequestForm").addEventListener("submit", async function(event){ 

    event.preventDefault();
    
    
    const formData = new FormData(this); 
    const data = Object.fromEntries(formData.entries());
    
    
    try {
    const response = await fetch('http://localhost:8081/api/coupon/register', { 
    method: 'POST', 
    headers: { 
    'Content-Type': 'application/json',
    },
    body: JSON.stringify(data), 
    }); 
    
    
    //if (!response.ok && !response.created) {
   // throw new Error('Network response was not ok ' + response.statusText);
    //}
    
    const jsonResponse = await response.json();
    document.getElementById("couponResponseOutput").textContent = JSON.stringify(jsonResponse, null, 2);  
    
    } catch(error) { 
    document.getElementById("couponResponseOutput").textContent = 'Error: ' + error.message;
    }
    
    }); 





document.getElementById("couponChangeQuantityForm").addEventListener("submit", async function(event){ 

        event.preventDefault();
        
        
       
        const couponCode = document.getElementById('couponCode').value; 
        const quantity = document.getElementById('newQuantity').value; 
        const data = { couponCode, quantity };
        
        
        try {
        const response = await fetch(`http://localhost:8081/api/coupon/changeQuantity?couponCode=${couponCode}&newQuantity=${quantity}`, { 
        method: 'PUT', 
        }); 
        
        
        if (!response.ok) { 
        throw new Error('Network response was not ok ' + response.statusText); 
        }
        
        const jsonResponse = await response.json();
        document.getElementById("couponChangeQuantityOutput").textContent = JSON.stringify(jsonResponse, null, 2);  
        
        } catch(error) { 
        document.getElementById("couponChangeQuantityOutput").textContent = 'Error: ' + error.message;
        }
        
});

     */