document.addEventListener("DOMContentLoaded", function () {
    showLogin();
});

//////Show Login Form, Hide Register Form
function showLogin() {
    document.getElementById("login-container").style.display = "block";
    document.getElementById("register-container").style.display = "none";
}


// Handle Login Submission

document.getElementById("login-form").addEventListener("submit", function (event) {
    event.preventDefault();

    let email = document.getElementById("login-email").value;
    let password = document.getElementById("login-password").value;


//After login the restaurant owner will be redirected to dashboard page
fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
     .then(response => {
            // Optionally check the Content-Type header to decide how to parse
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                return response.json().then(data => ({ type: "json", data, status: response.status }));
            } else {
                return response.text().then(text => ({ type: "text", data: text, status: response.status }));
            }
        })
         .then(({ type, data, status }) => {
//                console.log("Response Data:", data);
                console.log("Response Status:", status);
                if (type === "json") {
                    if (status === 200) {
                        alert("Login Successfully");

                // Store userId in localStorage
                if (data.userId) {
                    localStorage.setItem("userId", data.userId);
                    console.log("User ID saved:", data.userId);
                } else {
                    console.warn("User ID not found in response");
                }
                        window.location.href = "dashboard.html";
                    } else {
                        alert("Login failed: " + (data.error || "Unknown error"));
                    }
                } else { // Handling text response
                    if (data.includes("successful")) {
                        alert("Login Successful!");

                // Store userId in localStorage
                if (data.userId) {
                    localStorage.setItem("userId", data.userId);
                    console.log("User ID saved:", data.userId);
                } else {
                    console.warn("User ID not found in response");
                }
                        window.location.href = "dashboard.html";
                    } else {
                        alert("Login failed: " + data);
                    }
                }
            })
            .catch(error => {
                console.error("Fetch Error:", error);
                alert("Login failed due to a network error.");
        });

});
