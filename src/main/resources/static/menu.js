document.addEventListener("DOMContentLoaded", function () {
    const menuList = document.getElementById("menu-list");
    const addMenuBtn = document.getElementById("add-menu-btn");
    const menuFormContainer = document.getElementById("menu-form-container");
    const menuForm = document.getElementById("menu-form");
    const menuSubmitBtn = menuForm.querySelector("button[type='submit']");
    const restaurantNameElement = document.getElementById("restaurant-name");

    let isUpdating = false;  //  Track whether we're in "update" mode
    let updatingMenuId = null;  // Store the ID of the menu being updated

    // Get restaurantId from URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    const restaurantId = urlParams.get("restaurantId");

    if (!restaurantId) {
        alert("No restaurant selected!");
        return;
    }

    // Fetch restaurant name
    fetch(`http://localhost:8080/restaurants/${restaurantId}`)
        .then(response => response.json())
        .then(data => {
            restaurantNameElement.textContent = data.name;
        })
        .catch(error => console.error("Error fetching restaurant:", error));

    // ✅ Fetch menu items and display Update & Delete buttons
    function fetchMenu() {
        fetch(`http://localhost:8080/menus/${restaurantId}`)
            .then(response => response.json())
            .then(menuItems => {
                menuList.innerHTML = "";
                addMenuBtn.style.display = "block";

                if (menuItems.length === 0) {
                    menuList.innerHTML = "<p>No menu items available.</p>";
                } else {
                    menuItems.forEach(item => {
                        const li = document.createElement("li");
                        li.classList.add("menu-item");

                        li.innerHTML = `
                        <img src="${item.imageUrl}" alt="${item.name}" class="menu-image">
                            <span><strong>${item.name}</strong> - ₹${item.price.toFixed(2)}</span>
                            <div class="menu-buttons">
                                <button class="update-btn" data-id="${item.id}" data-name="${item.name}" data-price="${item.price}">Update</button>
                                <button class="delete-btn" data-id="${item.id}">Delete</button>
                            </div>
                        `;

                        menuList.appendChild(li);
                    });

                    // Attach event listeners to buttons
                    document.querySelectorAll(".update-btn").forEach(button => {
                        button.addEventListener("click", function () {
                            showUpdateForm(this.dataset.id, this.dataset.name, this.dataset.price);
                        });
                    });

                    document.querySelectorAll(".delete-btn").forEach(button => {
                        button.addEventListener("click", function () {
                            deleteMenuItem(this.dataset.id);
                        });
                    });
                }
            })
            .catch(error => console.error("Error fetching menu:", error));
    }

    fetchMenu();

    // ✅ When "Add Menu" button is clicked
    addMenuBtn.addEventListener("click", () => {
        menuFormContainer.style.display = "block";
        menuForm.reset();
        isUpdating = false;  // Set to "Add" mode
        updatingMenuId = null;
        menuSubmitBtn.textContent = "Add Menu";  // Change button text
    });

    // ✅ Handle form submission (Add or Update based on mode)
    menuForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const name = document.getElementById("menu-name").value;
        const price = document.getElementById("menu-price").value;
        const imageFile = document.getElementById("menu-image").files[0];

        const formData = new FormData();
        formData.append("name", name);
        formData.append("price", price);
        if (imageFile) {
            formData.append("image", imageFile);
        }


        if (isUpdating) {
            // ✅ Update existing menu item
            fetch(`http://localhost:8080/menus/${updatingMenuId}`, {
                method: "PUT",
                body:formData
//                headers: { "Content-Type": "application/json" },
//                body: JSON.stringify({ name, price })
            })
            .then(response => response.json())
            .then(() => {
                menuFormContainer.style.display = "none";
                menuForm.reset();
                fetchMenu();
            })
            .catch(error => console.error("Error updating menu:", error));
        } else {
            // ✅ Add a new menu item
            fetch(`http://localhost:8080/menus/${restaurantId}`, {
                method: "POST",
                body:formData
//                headers: { "Content-Type": "application/json" },
//                body: JSON.stringify({ name, price })
            })
            .then(response => response.json())
            .then(() => {
                menuFormContainer.style.display = "none";
                menuForm.reset();
                fetchMenu();
            })
            .catch(error => console.error("Error adding menu:", error));
        }
    });

    // ✅ Show update form and switch to update mode
    function showUpdateForm(menuId, name, price) {
        menuFormContainer.style.display = "block";
        document.getElementById("menu-name").value = name;
        document.getElementById("menu-price").value = price;

        isUpdating = true;  // Switch to "Update" mode
        updatingMenuId = menuId;
        menuSubmitBtn.textContent = "Update Menu";  //  Change button text
    }

    //  Delete menu item
    function deleteMenuItem(menuId) {
        if (confirm("Are you sure you want to delete this item?")) {
            fetch(`http://localhost:8080/menus/${menuId}`, {
                method: "DELETE"
            })
            .then(() => {
                fetchMenu();
            })
            .catch(error => console.error("Error deleting menu:", error));
        }
    }
});
