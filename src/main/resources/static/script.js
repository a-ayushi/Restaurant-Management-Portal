function redirectToLogin() {
    window.location.href = "login.html"; // Redirect to login page
}

function redirectToRegister() {
    window.location.href = "register.html"; // Redirect to register page
}


//show msg logic
function showMessage(message, type = "") {
    const messageBox = document.getElementById("message-box");
    if (!messageBox) return;

    messageBox.textContent = message;
    messageBox.className = `message-box ${type}`.trim(); // Add class only if type is provided
    messageBox.style.display = "block";

    setTimeout(() => {
        messageBox.style.display = "none";
    }, 5000);
}