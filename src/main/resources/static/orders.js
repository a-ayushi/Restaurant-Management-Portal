document.addEventListener("DOMContentLoaded", function () {
    const userId = localStorage.getItem("userId");

 const role = localStorage.getItem("role");
 if (!role) {
     console.warn("User role is not set!");
 }


    if (!userId) {
        alert("Please log in first.");
        window.location.href = "login.html";
        return;
    }
       if (role === "OWNER") {
            fetchRestaurantOrders(userId); //  Fetch orders for restaurant owner
        } else {
            fetchOrders(userId); //  Fetch customer orders
        }

//    fetchOrders(userId);
});

//  Fetch Orders for customers
 function fetchOrders(userId) {
    fetch(`http://localhost:8080/orders/user/${userId}`)
        .then(response => response.json())
        .then(displayOrders)
        .catch(error => console.error("Error fetching orders:", error));
}


// Fetch Orders for Restaurant Owners
function fetchRestaurantOrders(ownerId) {
    fetch(`http://localhost:8080/orders/restaurant/${ownerId}`)
        .then(response => response.json())
        .then(displayOwnerOrders)
        .catch(error => console.error("Error fetching restaurant orders:", error));
}


// Display Orders for Customers
 function displayOrders(orders) {
    const ordersList = document.getElementById("orders-list");
    ordersList.innerHTML = "";

    if (orders.length === 0) {
        ordersList.innerHTML = "<p>You have no orders.</p>";
        return;
    }

    orders.forEach(order => {
        const div = document.createElement("div");
        div.classList.add("order-item");

        div.innerHTML = `
            <h3>Order ${order.id}</h3>
            <p><strong>Total Price:</strong> ₹${order.totalPrice}</p>
            <p><strong>Status:</strong> ${order.status}</p>
            ${order.status === "PENDING" ? `<button onclick="cancelOrder(${order.id})">Cancel Order</button>` : ""}
        `;

        ordersList.appendChild(div);
    });
}

// Display Orders for Restaurant Owners (Allow Status Updates)
function displayOwnerOrders(orders) {
    const ordersList = document.getElementById("orders-list");
    ordersList.innerHTML = "";

    if (orders.length === 0) {
        ordersList.innerHTML = "<p>No orders for your restaurant.</p>";
        return;
    }

    orders.forEach(order => {
        const div = document.createElement("div");
        div.classList.add("order-item");

//<h3>Order ${order.id}</h3>

        div.innerHTML = `
            <p><strong>Customer:</strong> ${order.userId}</p>
            <p><strong>Total Price:</strong> ₹${order.totalPrice}</p>
            <p><strong>Status:</strong> ${order.status}</p>

            <select id="status-${order.id}">
                <option value="PENDING" ${order.status === "PENDING" ? "selected" : ""}>Pending</option>
                <option value="PREPARING" ${order.status === "PREPARING" ? "selected" : ""}>Preparing</option>
                <option value="DELIVERED" ${order.status === "DELIVERED" ? "selected" : ""}>Delivered</option>
                <option value="CANCELLED" ${order.status === "CANCELLED" ? "selected" : ""}>Cancelled</option>
            </select>
            <button onclick="updateOrderStatus(${order.id})">Update Status</button>
        `;

        ordersList.appendChild(div);
    });
}

//  Update Order Status (Owner Only)
function updateOrderStatus(orderId) {
    const status = document.getElementById(`status-${orderId}`).value;
    const restaurantOwnerId = localStorage.getItem("userId");

    fetch(`http://localhost:8080/orders/${orderId}?status=${status}&restaurantOwnerId=${restaurantOwnerId}`, {
        method: "PUT",
    })
    .then(response => response.json())
    .then(() => {
        alert("Order status updated!");
        fetchRestaurantOrders(restaurantOwnerId); // Refresh Orders
    })
    .catch(error => console.error("Error updating order status:", error));
}

// Cancel Order (Only if Pending)
function cancelOrder(orderId) {
    const userId = localStorage.getItem("userId");

    fetch(`http://localhost:8080/orders/${orderId}/cancel?userId=${userId}`, {
        method: "DELETE",
    })
    .then(response => response.text())
    .then(message => {
        alert(message);
        fetchOrders(userId); // Refresh Orders
    })
    .catch(error => console.error("Error canceling order:", error));
}

// Logout Function
function logout() {
    localStorage.removeItem("userId");
    localStorage.removeItem("role");
    alert("Logged out successfully!");
    window.location.href = "login.html";
}
