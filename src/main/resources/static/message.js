document.addEventListener("DOMContentLoaded", function () {
    // Check if the message box exists (only add if it's missing)
    if (!document.getElementById("message-box")) {
        fetch("message.html")
            .then(response => response.text())
            .then(html => {
                document.body.insertAdjacentHTML("beforeend", html);
                addMessageBoxEvents(); // Attach event listener after inserting HTML
            })
            .catch(error => console.error("Error loading message box:", error));
    } else {
        addMessageBoxEvents();
    }
});

// Function to show a message
function showMessage(message, type = "success") {

console.log("i am called")
    let messageBox = document.getElementById("message-box");
    let messageText = document.getElementById("message-text");

    if (!messageBox || !messageText) return;

    messageText.textContent = message;
    messageBox.className = `message-box ${type}`; // Add class for styling
    messageBox.style.display = "flex"; // Make it visible
console.log("i am called")

        addMessageBoxEvents(); // Call function to attach close event


    setTimeout(() => {
        messageBox.style.display = "none";
    }, 5000);
}

// Function to attach close event
function addMessageBoxEvents() {
    let closeBtn = document.getElementById("close-message");
    let messageBox = document.getElementById("message-box");

    if (closeBtn && messageBox) {
        closeBtn.addEventListener("click", function () {
            messageBox.style.display = "none";
        });
    }
}
