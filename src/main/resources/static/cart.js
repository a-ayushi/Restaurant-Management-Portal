document.addEventListener("DOMContentLoaded", function () {
    const userId = localStorage.getItem("userId");

    if (!userId) {
        alert("Please log in first.");
        window.location.href = "login.html";
        return;
    }

    fetchCartItems(userId);
});

// ✅ Function to Fetch Cart Items
function fetchCartItems(userId) {
    fetch(`http://localhost:8080/cart/${userId}`)
        .then(response => response.json())
        .then(cartItems => {
            const cartContainer = document.getElementById("cart-items");
            const totalPriceElement = document.getElementById("total-price");
            let totalPrice = 0;

            cartContainer.innerHTML = ""; // Clear previous items

            if (cartItems.length === 0) {
                cartContainer.innerHTML = "<p>Your cart is empty.</p>";
                return;
            }

            let restaurantName = cartItems[0].restaurantName; // ✅ Get restaurant name from first item

            cartItems.forEach(item => {
                const div = document.createElement("div");
                div.classList.add("cart-item");
                div.innerHTML = `
                    <p><strong>${item.itemName}</strong></p>
                    <p>Price: ₹${item.itemPrice} | Quantity: ${item.quantity}</p>
                    <p>Restaurant: ${item.restaurant.name}</p>
                    <button onclick="removeFromCart(${item.id})">Remove</button>
                `;
                cartContainer.appendChild(div);

                totalPrice += item.itemPrice * item.quantity; // ✅ Calculate total price
            });

            totalPriceElement.textContent = totalPrice.toFixed(2); // ✅ Update total price
        })
        .catch(error => console.error("Error fetching cart items:", error));
}

// ✅ Function to Remove Item from Cart
function removeFromCart(cartItemId) {
    fetch(`http://localhost:8080/cart/remove/${cartItemId}`, { method: "DELETE" })
        .then(response => response.text())
        .then(message => {
            alert(message);
            fetchCartItems(localStorage.getItem("userId")); // ✅ Refresh cart after removal
        })
        .catch(error => console.error("Error removing item:", error));
}

// ✅ Function for Checkout
function checkout() {
    alert("Proceeding to checkout... (Payment integration needed)");
}

// ✅ Function to Logout
function logout() {
    localStorage.removeItem("userId");
    localStorage.removeItem("role");
    alert("Logged out successfully!");
    window.location.href = "login.html";
}
