import { camelCaseToNormal, jsonToText, postingFunction, extractKeys, getValue, getOrPutFunction} from './common_script.js';

const links = document.querySelectorAll(".header a");

links.forEach(link => {
    if (link.href === window.location.href) {
        link.classList.add("active"); 
    }
}); //to show active in header


document.getElementById("logoutButton").addEventListener("click", function() {
    window.location.href = "/logout";
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
    const api = "https://holidayvilla-production.up.railway.app/api/staff/onBoard";
    postingFunction(this, api, "Below is the onboarded staff detail", "Staff is on-boarded successfully", heading, message, popup);
});

document.getElementById("formOffBoardStaff").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("offBoardStaff").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/staff/offBoard?staffEmail=${mail}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Staff Off-Boarded Successfully");
});

document.getElementById("formGetStaffByStaffEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getStaffByStaffEmail").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/staff/get-staff-by-staffEmail?staffEmail=${mail}`;
    getOrPutFunction("GET", api, table, popup, heading, "Staff Detail:");
});

document.getElementById("formResetPassword").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("resetPasswordEmail").value;
    const pass = document.getElementById("resetPasswordPassword").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/staff/reset-password?staffEmail=${mail}&newPassword=${pass}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Password Reset Successful:");
});

document.getElementById("formChangeDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("changeDepartmentStaffEmail").value;
    const dept = document.getElementById("changeDepartmentDepartment").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/staff/change-department?staffEmail=${mail}&department=${dept}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Department Change Successful:");
});

document.getElementById("getAllCurrentStaff").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/staff/all-current-staff";
    getOrPutFunction("GET", api, table, popup, heading, "All Current Staff");
});


document.getElementById("getAllExStaff").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/staff/all-ex-staff";
    getOrPutFunction("GET", api, table, popup, heading, "All Ex Staff");
});

document.getElementById("formGetAllCurrentStaffByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllCurrentStaffByDepartment").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/staff/all-current-staff-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Current Staff of ${dept}` );
});

document.getElementById("formGetAllExStaffByDepartment").addEventListener("submit", function(event){ 
    event.preventDefault();
    const dept = document.getElementById("getAllExStaffByDepartment").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/staff/all-ex-staff-by-department?department=${dept}`;
    getOrPutFunction("GET", api, table, popup, heading, `All Ex Staff of ${dept}` );
});

document.getElementById("formGetStaffSalaryByStaffEmail").addEventListener("submit", async function(event){ 
    event.preventDefault();
    const mail = document.getElementById("getStaffSalaryByStaffEmail").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/staff/get-staff-salary-by-staffEmail?staffEmail=${mail}`;
    const response = await fetch(api, {
        method: "GET", 
        });

    const value = await response.text(); // Use .text() since it's a raw value, not JSON
    const doubleValue = parseFloat(value);

    heading.innerText = `Salary of given staff: ${doubleValue}`;
    table.innerHTML = "";
    popup.style.display = "block";
});

document.getElementById("formChangeStaffSalaryByStaffEmail").addEventListener("submit", function(event){ 
    event.preventDefault();
    const mail = document.getElementById("changeStaffSalaryByStaffEmailStaffEmail").value;
    const salary = document.getElementById("changeStaffSalaryByStaffEmailNewSalary").value;
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = `https://holidayvilla-production.up.railway.app/api/staff/change-staff-salary-by-staffEmail?staffEmail=${mail}&newSalary=${salary}`;
    getOrPutFunction("PUT", api, table, popup, heading, "Salary of given staff updated successfully" );
});

document.getElementById("createTransactionForPayroll").addEventListener("submit", function(event){ 
    event.preventDefault();
    const heading = document.getElementById("staff-getter-responseHeading");
    const table = document.getElementById("staff-getter-responseTable");
    const popup = document.getElementById("staff-getter-popup");
    const api = "https://holidayvilla-production.up.railway.app/api/staff/create-transaction-for-payroll";
    getOrPutFunction("PUT", api, table, popup, heading, "Salary Transactions has been created for all current staff successfully" );
});






