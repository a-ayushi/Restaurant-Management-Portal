document.addEventListener("DOMContentLoaded", function () {
    const ownerId = localStorage.getItem("userId"); // Get owner ID
    fetch(`http://localhost:8080/restaurants/user/${ownerId}`) // Get restaurant ID of owner
        .then(response => response.json())
        .then(restaurant => {
            if (restaurant) {
                fetchOrdersForRestaurant(restaurant.id); // Fetch orders
            } else {
                alert("No restaurant found for this owner!");
            }
        })
        .catch(error => console.error("Error fetching restaurant:", error));
});

//  Function to Fetch Orders
function fetchOrdersForRestaurant(restaurantId) {
    fetch(`http://localhost:8080/orders/restaurant/${restaurantId}`)
        .then(response => response.json())
        .then(orders => {
            const ordersContainer = document.getElementById("orders-container");
            ordersContainer.innerHTML = "";

            if (orders.length === 0) {
                ordersContainer.innerHTML = "<p>No orders available.</p>";
                return;
            }

            orders.forEach(order => {
                const div = document.createElement("div");
                div.classList.add("order-item");

                div.innerHTML = `
                    <p><strong>Order ID:</strong> ${order.id}</p>
                    <p><strong>Total Price:</strong> ₹${order.totalPrice}</p>
                    <p><strong>Status:</strong> <span id="status-${order.id}">${order.status}</span></p>
                    <select id="status-select-${order.id}">
                        <option value="PENDING">Pending</option>
                        <option value="PREPARING">Preparing</option>
                        <option value="DELIVERED">Delivered</option>
                        <option value="CANCELLED">Cancelled</option>
                    </select>
                    <button onclick="updateOrderStatus(${order.id})">Update Status</button>
                `;

                ordersContainer.appendChild(div);
            });
        })
        .catch(error => console.error("Error fetching orders:", error));
}
function updateOrderStatus(orderId) {
    const ownerId = localStorage.getItem("userId"); // Get owner ID
    const newStatus = document.getElementById(`status-select-${orderId}`).value; // Get new status

    fetch(`http://localhost:8080/orders/${orderId}?status=${newStatus}&restaurantOwnerId=${ownerId}`, {
        method: "PUT"
    })
    .then(response => response.json())
    .then(updatedOrder => {
        alert("Order status updated successfully!");
        document.getElementById(`status-${orderId}`).innerText = updatedOrder.status;
    })
    .catch(error => console.error("Error updating order status:", error));
}
