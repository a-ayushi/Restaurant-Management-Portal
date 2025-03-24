// Show Register Form, Hide Login Form
document.addEventListener("DOMContentLoaded", function () {
    showRegister();
});

function showRegister() {
    let registerContainer = document.getElementById("register-container");
    if (registerContainer) {
        registerContainer.style.display = "block";
    } else {
        console.error("register-container not found!");
    }
}

// Handle Registration Submission
document.getElementById("register-form").addEventListener("submit", function (event) {
    event.preventDefault();
    const email = document.getElementById("register-email").value;
    const password = document.getElementById("register-password").value;
    const role = document.getElementById("register-role").value;

    if (!role) {
        alert("Please select a role!");
        return;
    }

    fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password, role })
    })
    .then(response => {
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            return response.json().then(data => ({ type: "json", data, status: response.status }));
        } else {
            return response.text().then(text => ({ type: "text", data: text, status: response.status }));
        }
    })
    .then(({ type, data, status }) => {
        console.log("Response Status:", status);
        // Check if the status code indicates success (200-299)
        if (status >= 200 && status < 300) {
            alert("Registration Successful! Redirecting to login...");
            window.location.href = "login.html";
        } else {
            let errorMsg = type === "json" ? (data.error || "Unknown error") : data;
            alert("Registration failed: " + errorMsg);
        }
    })
    .catch(error => {
        console.error("Fetch Error:", error);
        alert("Registration failed due to a network error.");
    });
});
