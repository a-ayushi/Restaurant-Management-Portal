function redirectToLogin() {
    window.location.href = "login.html"; // Redirect to login page
}

function redirectToRegister() {
    window.location.href = "register.html"; // Redirect to register page
}


//
//
//document.addEventListener("DOMContentLoaded", function () {
//    fetchRestaurants();
//});
//
//function fetchRestaurants() {
//    fetch("http://localhost:8080/restaurants")
//       .then(response => response.json())
//              .then(data => {
//                  if (document.getElementById("restaurant-list")) {
//                      displayRestaurants(data);
//                  }
//              })
//        .catch(error => {
//            console.error("Error fetching restaurants:", error);
//            document.getElementById("restaurant-list").innerHTML =
//                "<p style='color: red;'>Failed to load restaurants. Try again later.</p>";
//        });
//}
//
//function displayRestaurants(restaurants) {
//    let restaurantContainer = document.getElementById("restaurant-list");
//    restaurantContainer.innerHTML = ""; // Clear previous data
//
//    if (restaurants.length === 0) {
//        restaurantContainer.innerHTML = "<p>No restaurants available.</p>";
//        return;
//    }
//
//    restaurants.forEach(restaurant => {
//        let restaurantCard = document.createElement("div");
//        restaurantCard.classList.add("restaurant-card");
//
//        restaurantCard.innerHTML = `
//                       <h3>${restaurant.name}</h3><p>${restaurant.address}</p>`;
//            <button onclick="viewMenu(${restaurant.id})">View Menu</button>
//        ;
//
//        restaurantContainer.appendChild(restaurantCard);
//    });
//}
//
//function viewMenu(restaurantId) {
//    window.location.href = `menu.html?restaurant=${restaurantId}`;
//}
//
/////////////////////////////////////
////// /////Show Login Form, Hide Register Form
////function showLogin() {
////    document.getElementById("login-container").style.display = "block";
////    document.getElementById("register-container").style.display = "none";
////}
////
////// Show Register Form, Hide Login Form
////function showRegister() {
////    document.getElementById("login-container").style.display = "none";
////    document.getElementById("register-container").style.display = "block";
////}
////
////// Handle Login Submission
////document.getElementById("login-form").addEventListener("submit", function (event) {
////    event.preventDefault();
////    let email = document.getElementById("login-email").value;
////    let password = document.getElementById("login-password").value;
////
////
//////After login the restaurant owner will be redirected to dashboard page
////fetch("http://localhost:8080/restaurants", {
////        method: "POST",
////        headers: { "Content-Type": "application/json" },
////        body: JSON.stringify({ email, password })
////    })
////    .then(response => response.json())
////    .then(data => {
////        if (data.role === "OWNER") {
////            window.location.href = "dashboard.html"; // Redirect to the dashboard
////        } else {
////
////            window.location.href = "index.html"; // Redirect customers to the homepage
////        }
////    })
////    .catch(error => console.error("Login failed:", error));
////});
////
////// Handle Registration Submission
////document.getElementById("register-form").addEventListener("submit", function (event) {
////    event.preventDefault();
////    let email = document.getElementById("register-email").value;
////    let password = document.getElementById("register-password").value;
////    let role = document.getElementById("register-role").value;
////
////// if user do not select it then give an alert
////    if (role === "") {
////        alert("Please select a role!");
////        return;
////    }
////
////    fetch("http://localhost:8080/auth/login", {
////            method: "POST",
////            headers: { "Content-Type": "application/json" },
////            body: JSON.stringify({ email, password, role })
////        })
////        .then(response => response.json())
////        .then(data => {
////            alert("Registration successful! Redirecting to login...");
////           showLogin();
//////            window.location.href = "login.html"; // Redirect to Login Page
////        })
////        .catch(error => console.error("Registration failed:", error));
////});
//
////// ////////restaurant dashboard things
//// Show Add/Edit Restaurant Form
//function showAddForm() {
//    document.getElementById("restaurant-form").style.display = "block";
//}
//
//// Hide Form
//function hideForm() {
//    document.getElementById("restaurant-form").style.display = "none";
//}
//
//// Submit New or Updated Restaurant
//function submitRestaurant() {
//    let name = document.getElementById("restaurant-name").value;
//    let location = document.getElementById("restaurant-location").value;
//    let description = document.getElementById("restaurant-description").value;
//
//    let restaurantData = { name, location, description };
//
//    fetch("http://localhost:8080/api/restaurants", { // Replace with actual backend API
//        method: "POST",
//        headers: { "Content-Type": "application/json" },
//        body: JSON.stringify(restaurantData)
//    })
//    .then(response => response.json())
//    .then(data => {
//        alert("Restaurant added successfully!");
//        fetchRestaurants();
//        hideForm();
//    })
//    .catch(error => console.error("Error adding restaurant:", error));
//}
//
//// Delete a Restaurant
//function deleteRestaurant(restaurantId) {
//    fetch(`http://localhost:8080/api/restaurants/${restaurantId}`, {
//        method: "DELETE"
//    })
//    .then(() => {
//        alert("Restaurant deleted!");
//        fetchRestaurants();
//    })
//    .catch(error => console.error("Error deleting restaurant:", error));
//}
//
//
/////MEnu management logic
//document.addEventListener("DOMContentLoaded", function () {
//    if (document.getElementById("menu-list")) {
//        fetchMenuItems();
//    }
//});
//
//// Fetch Menu Items
//function fetchMenuItems() {
//    fetch("http://localhost:8080/menu")
//        .then(response => response.json())
//        .then(data => displayMenu(data))
//        .catch(error => console.error("Error fetching menu:", error));
//}
//
//// Display Menu Items
//function displayMenu(menuItems) {
//    let menuContainer = document.getElementById("menu-list");
//    menuContainer.innerHTML = "";
//
//    if (menuItems.length === 0) {
//        menuContainer.innerHTML = "<p>No menu items available.</p>";
//        return;
//    }
//
//    menuItems.forEach(item => {
//        let menuCard = document.createElement("div");
//        menuCard.classList.add("menu-card");
//
//        menuCard.innerHTML = `
//            <h3>${item.name}</h3>
//            <p><strong>Price:</strong> $${item.price}</p>
//            <button onclick="editMenuItem(${item.id})">Edit</button>
//            <button onclick="deleteMenuItem(${item.id})">Delete</button>
//        `;
//
//        menuContainer.appendChild(menuCard);
//    });
//}
//
//// Show Add/Edit Menu Item Form
//function showAddMenuForm() {
//    document.getElementById("menu-form").style.display = "block";
//}
//
//// Hide Form
//function hideMenuForm() {
//    document.getElementById("menu-form").style.display = "none";
//}
//
//// Submit New Menu Item
//function submitMenuItem() {
//    let name = document.getElementById("menu-name").value;
//    let price = document.getElementById("menu-price").value;
//
//    let menuItemData = { name, price };
//
//    fetch("http://localhost:8080/api/menu", {
//        method: "POST",
//        headers: { "Content-Type": "application/json" },
//        body: JSON.stringify(menuItemData)
//    })
//    .then(response => response.json())
//    .then(() => {
//        alert("Menu item added successfully!");
//        fetchMenuItems();
//        hideMenuForm();
//    })
//    .catch(error => console.error("Error adding menu item:", error));
//}
//
//// Delete a Menu Item
//function deleteMenuItem(itemId) {
//    fetch(`http://localhost:8080/api/menu/${itemId}`, { method: "DELETE" })
//    .then(() => {
//        alert("Menu item deleted!");
//        fetchMenuItems();
//    })
//    .catch(error => console.error("Error deleting menu item:", error));
//}
// ////////////////////////
//
//
// let cart = JSON.parse(localStorage.getItem("cart")) || [];
//
// document.addEventListener("DOMContentLoaded", function () {
//     if (document.getElementById("cart-items")) {
//         displayCart();
//     }
// });
//
// // Add Item to Cart
// function addToCart(item) {
//     let existingItem = cart.find(cartItem => cartItem.id === item.id);
//
//     if (existingItem) {
//         existingItem.quantity += 1;
//     } else {
//         cart.push({ ...item, quantity: 1 });
//     }
//
//     localStorage.setItem("cart", JSON.stringify(cart));
//     alert("Item added to cart!");
// }
//
// // Display Cart Items
// function displayCart() {
//     let cartContainer = document.getElementById("cart-items");
//     let totalPrice = 0;
//     cartContainer.innerHTML = "";
//
//     if (cart.length === 0) {
//         cartContainer.innerHTML = "<p>Your cart is empty.</p>";
//         return;
//     }
//
//     cart.forEach((item, index) => {
//         let cartItem = document.createElement("div");
//         cartItem.classList.add("cart-item");
//
//         totalPrice += item.price * item.quantity;
//
//         cartItem.innerHTML = `
//             <h3>${item.name}</h3>
//             <p>Price: $${item.price}</p>
//             <div class="quantity">
//                 <button onclick="updateQuantity(${index}, -1)">-</button>
//                 <span>${item.quantity}</span>
//                 <button onclick="updateQuantity(${index}, 1)">+</button>
//             </div>
//             <button onclick="removeFromCart(${index})">Remove</button>
//         `;
//
//         cartContainer.appendChild(cartItem);
//     });
//
//     document.getElementById("total-price").textContent = totalPrice.toFixed(2);
// }
//
// // Update Quantity
// function updateQuantity(index, change) {
//     if (cart[index].quantity + change <= 0) {
//         cart.splice(index, 1);
//     } else {
//         cart[index].quantity += change;
//     }
//
//     localStorage.setItem("cart", JSON.stringify(cart));
//     displayCart();
// }
//
// // Remove Item from Cart
// function removeFromCart(index) {
//     cart.splice(index, 1);
//     localStorage.setItem("cart", JSON.stringify(cart));
//     displayCart();
// }
//
// // Proceed to Checkout
// function checkout() {
//     alert("Proceeding to checkout...");
//     localStorage.removeItem("cart"); // Clear cart after checkout
//     displayCart();
// }
/////////////////////////////////////////
/////////ORDERS LOGIN//////////////
//document.addEventListener("DOMContentLoaded", function () {
//    if (document.getElementById("orders-list")) {
//        fetchOrders();
//    }
//});
//
//// Fetch Orders for Logged-in User
//function fetchOrders() {
//    let userId = localStorage.getItem("userId"); // Assuming userId is stored in localStorage after login
//
//    fetch(`http://localhost:8080/api/orders/user/${userId}`) // Replace with actual backend API
//        .then(response => response.json())
//        .then(data => displayOrders(data))
//        .catch(error => console.error("Error fetching orders:", error));
//}
//
//// Display Orders
//function displayOrders(orders) {
//    let ordersContainer = document.getElementById("orders-list");
//    ordersContainer.innerHTML = "";
//
//    if (orders.length === 0) {
//        ordersContainer.innerHTML = "<p>You have no orders.</p>";
//        return;
//    }
//
//    orders.forEach(order => {
//        let orderCard = document.createElement("div");
//        orderCard.classList.add("order-card");
//
//        orderCard.innerHTML = `
//            <h3>Order #${order.id}</h3>
//            <p><strong>Items:</strong> ${order.items.map(item => item.name).join(", ")}</p>
//            <p><strong>Total Price:</strong> $${order.totalPrice}</p>
//            <p class="order-status"><strong>Status:</strong> ${order.status}</p>
//        `;
//
//        ordersContainer.appendChild(orderCard);
//    });
//}
