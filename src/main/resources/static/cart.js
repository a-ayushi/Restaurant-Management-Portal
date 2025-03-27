document.addEventListener("DOMContentLoaded", function () {
    const userId = localStorage.getItem("userId");

    if (!userId) {
        alert("Please log in first.");
        window.location.href = "login.html";
        return;
    }

    fetchCartItems(userId);
});

//  Function to Fetch Cart Items
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

            let restaurantName = cartItems[0].restaurantName; // Get restaurant name from first item

            cartItems.forEach(item => {
                const div = document.createElement("div");
                div.classList.add("cart-item");

              div.innerHTML = `
                                  <img src="${item.imageUrl}" alt="${item.itemName}" class="cart-item-image">  <!--  Display food image -->
                                  <div class="cart-item-details">
                                      <p class="menu-name"><strong>${item.itemName}</strong></p>
                                      <p>Price: ₹${item.itemPrice} | Quantity: ${item.quantity}</p>
                                      <p>Restaurant: ${item.restaurant.name}</p>

                                  <div class="cart-btn">
                                  <button class="remove-btn" onclick="removeFromCart(${item.id})">Remove</button>
                                 </div>
                                 </div>
                              `;
                cartContainer.appendChild(div);

                totalPrice += item.itemPrice * item.quantity; //  Calculate total price
            });

            totalPriceElement.textContent = totalPrice.toFixed(2); //  Update total price
        })
        .catch(error => console.error("Error fetching cart items:", error));
}

//Place Order for All Cart Items
function placeOrderFromCart() {
     const userId = localStorage.getItem("userId");

    fetch(`http://localhost:8080/cart/${userId}`)
        .then(response => response.json())
        .then(cartItems => {
            if (!cartItems || cartItems.length === 0) {
                alert("Your cart is empty or could not be loaded!");
                return;
            }

            const restaurantId = cartItems[0].restaurant.id;
            const orderItems = cartItems.map(item => ({
                menuItemId: item.menuItemId,
                quantity: item.quantity,
                price: item.itemPrice
            }));

            const orderData = {
                userId: userId,
                restaurantId: restaurantId,
                orderItems: orderItems
            };

            fetch("http://localhost:8080/orders", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(orderData)
            })
            .then(response => response.json())
            .then(data => {
                alert("Order placed successfully!");
                window.location.href='orders.html';
            })
            .catch(error => console.error("Error placing order:", error));
        });
}

//  Function to Remove Item from Cart
function removeFromCart(cartItemId) {
    fetch(`http://localhost:8080/cart/remove/${cartItemId}`, { method: "DELETE" })
        .then(response => response.text())
        .then(message => {
            alert(message);
            fetchCartItems(localStorage.getItem("userId")); //  Refresh cart after removal
        })
        .catch(error => console.error("Error removing item:", error));
}

//  Function for Checkout
function checkout() {
    alert("Proceeding to checkout... (Payment integration needed)");
}

// Function to Logout
function logout() {
    localStorage.removeItem("userId");
    localStorage.removeItem("role");
    alert("Logged out successfully!");
    window.location.href = "login.html";
}
