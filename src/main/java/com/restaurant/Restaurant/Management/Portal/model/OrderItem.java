//one order can have multiple menu items
//many to one relationship between order and menu

package com.restaurant.Restaurant.Management.Portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) //  Each item belongs to one order
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false) //  Links to menu item
    private Menu menuItem;

//    private String itemName;
    private double Price;
    private int quantity;

    public OrderItem() {}

    public OrderItem(Order order, Menu menuItem, int quantity,double price) {
        this.order = order;
        this.menuItem = menuItem;
//        this.itemName = menuItem.getName();
        this.Price = price;
        this.quantity = quantity;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public Menu getMenuItem() { return menuItem; }
//    public String getItemName() { return itemName; }
    public double getPrice() { return Price; }
    public int getQuantity() { return quantity; }

    public void setId(Long id) { this.id = id; }
    public void setOrder(Order order) { this.order = order; }
    public void setMenuItem(Menu menuItem) { this.menuItem = menuItem; }
//    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setPrice(double price) { this.Price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

}
