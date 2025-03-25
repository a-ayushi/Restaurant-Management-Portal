document.addEventListener("DOMContentLoaded", function () {
    const role = localStorage.getItem("role"); // Get user role
    const params = new URLSearchParams(window.location.search);
    const restaurantId = params.get("restaurantId");

    if (!restaurantId) {
        alert("No restaurant selected!");
        window.location.href = "home.html";
        return;
    }

    fetch(`http://localhost:8080/menus/${restaurantId}`)
        .then(response => response.json())
        .then(menuItems => {
            const menuList = document.getElementById("menu-list");
            menuList.innerHTML = "";

            if (menuItems.length === 0) {
                menuList.innerHTML = "<p>No Food available</p>";
            } else {
                menuItems.forEach(item => {
                    const div = document.createElement("div");
                    console.log(restaurantId);
                    div.innerHTML = `
                    <img src="${item.imageUrl}" alt="${item.name}" class="menu-image"> <!--  Display menu image -->
                    <div class="menu-content">
                        <p>${item.name} - ₹${item.price.toFixed(2)}</p>
                    </div>
                    ${role === "CUSTOMER" ? `<button class="add-to-cart-btn" onclick="addToCart(${item.id}, '${item.name}', ${item.price}, ${restaurantId})">Add to Cart</button>` : ""}

                    `;
                    menuList.appendChild(div);
                });
            }
        })
        .catch(error => console.error("Error fetching menu:", error));
});

function addToCart(menuItemId,itemName,itemPrice,restaurantId) {

 console.log("Add to Cart Clicked!");  // ✅ Check if function is triggered
    console.log(`MenuItemId: ${menuItemId}, Name: ${itemName}, Price: ${itemPrice}, RestaurantId: ${restaurantId}`);


    const userId = localStorage.getItem("userId");

    if (!userId) {
        alert("Please log in to add items to the cart.");
        return;
    }
     const quantity = 1; //  quantity (Default to 1)

    fetch("http://localhost:8080/cart/add", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userId, itemName,itemPrice, menuItemId,quantity,restaurantId })
    })
     .then(response => response.json())
        .then(data => {
                console.log("Response from server:", data);  // Debug server response

            if (data) {
                alert("Item added to cart successfully!");
            } else {
                alert("Failed to add item to cart.");
            }
        })
        .catch(error => console.error("Error adding to cart:", error));
}
