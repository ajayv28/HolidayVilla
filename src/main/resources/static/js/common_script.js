



// FUNCTIONS RELATED TO - POSTING & GET OR PUT FUNCTION  *************************************************************************

export function camelCaseToNormal(camelCaseString) {
    const result = camelCaseString.replace(/([a-z])([A-Z])/g, "$1 $2"); //to insert space between each caps
    return result.replace(/\b\w/g, char => char.toUpperCase()); //making first letter in caps
}

export function jsonToText(headerText, json, space) {
    let extraSpace = "";
    for (let i = 0; i <space; i++) {
        extraSpace += "."; 
    }
    let text = `${extraSpace}${camelCaseToNormal(headerText)}\n`; 

    for (const [key, value] of Object.entries(json)) {
        const formattedKey = camelCaseToNormal(key);
        if (typeof value === "object" && value !== null) {
            text += jsonToText(formattedKey, value, space + 1);       //for nested obj
        } else {
            text += `${extraSpace} ${formattedKey}: ${value}\n`; // for normal case, Add two spaces before the key-value pair
        }
    }
    return text; 
}


// FUNCTIONS RELATED TO - POSTING FUNCTION  *************************************************************************

export async function postingFunction(thisElement, api, customMessage, headingMessage, heading, message, popup) {
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


        if (response.status !== 201) {
            const errorResponse = await response.json();
            throw new Error(`${errorResponse.message} (Error code - ${response.status})`); 
        }

        const jsonResponse = await response.json();
        heading.innerText = headingMessage;
        message.innerText = jsonToText(customMessage, jsonResponse, 0);
        popup.style.display = "block";
               
    }catch(error){
        heading.innerText = `Error - ${error.message}`;
        message.innerText = "Kindly contact the admin / IT team to resolve this. \n Feel free to drop an email with screenshot of this page to info@holidayvilla.com";
        popup.style.display = "block";
    }
}




// FUNCTIONS RELATED TO - GET / PUT  FUNCTION    *****************************************************************


export function extractKeys(obj, headers) {
    for (const key in obj) {
        if (obj.hasOwnProperty(key)) {
            headers.add(key); 
        }
    }
}

export function getValue(obj, key) {
    return obj[key];
}


export async function getOrPutFunction(getOrPut, api, table, popup, heading, headingMessage) {  

    try { 
    const responseValue = await fetch(api, {
    method: getOrPut, 
    });
    
    
        if (!responseValue.ok) {
            const errorResponse = await responseValue.json();
            throw new Error(`${errorResponse.message} (Error code - ${responseValue.status})`); 
        }


        let responseList = await responseValue.json();

        if (!responseList) {
            responseList = []; // Handle null or undefined

        } else if (typeof responseList === "object" && !Array.isArray(responseList)) {
            responseList = [responseList]; // Wrap single object in an array
        }

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
                    td.textContent = jsonToText("Guest Details", guestDetails, 0);
                } else if (header.includes("roomResponse")) {
                    const roomDetails = response.roomResponse;
                    td.textContent = jsonToText("Room Details", roomDetails, 0); 
                } else if (header.includes("bookingResponse")) {
                    const bookingDetails = response.bookingResponse;
                    td.textContent = jsonToText("Booking Details", bookingDetails, 0); 
                } else if (header.includes("couponResponse")) {
                    const couponDetails = response.couponResponse;
                    td.textContent = jsonToText("Coupon Details", couponDetails, 0); 
                } else if (header.includes("foodOrderResponse")) {
                    const foodOrderDetails = response.foodOrderResponse;
                    td.textContent = jsonToText("Food Order Details", foodOrderDetails, 0); 
                } else if (header.includes("maintenanceResponse")) {
                    const maintenanceDetails = response.maintenanceResponse;
                    td.textContent = jsonToText("Maintenance Details", maintenanceDetails, 0); 
                } else if (header.includes("materialResponse")) {
                    const materialDetails = response.materialResponse;
                    td.textContent = jsonToText("Material Details", materialDetails, 0); 
                } else if (header.includes("materialRequisitionResponse")) {
                    const materialRequisitionDetails = response.materialRequisitionResponse;
                    td.textContent = jsonToText("Material Requisition Details", materialRequisitionDetails, 0); 
                } else if (header.includes("staffResponse")) {
                    const staffDetails = response.staffResponse;
                    td.textContent = jsonToText("Staff Details", staffDetails, 0); 
                } else if (header.includes("transactionResponse")) {
                    const transactionDetails = response.transactionResponse;
                    td.textContent = jsonToText("Transaction Details", transactionDetails, 0); 
                } else {
                    td.textContent = value !== undefined ? value : "N/A"; 
                }
                row.appendChild(td);
            });
            tbody.appendChild(row);
        });
        table.appendChild(tbody);

        popup.style.display = "block";  

    }catch(error){ 
        heading.innerText = `Error: ${error.message}`; 
        table.innerHTML = ""; 
        popup.style.display = "block";
    }
}

