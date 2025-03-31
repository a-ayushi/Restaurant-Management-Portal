package com.restaurant.Restaurant.Management.Portal.model;

import jakarta.persistence.*;

//each orderItem represents a single menu item within an order
//order_items represents the individual items in an order

    @Entity
    @Table(name = "order_items")
    public class OrderItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne // many-to-one relationship with order entity
        @JoinColumn(name = "order_id", nullable = false) //order_id work as foreign key
        private Order order;

        @Column(nullable = false)
        private Long menuId;

        @Column(nullable = false)
        private int quantity;

        @Column(nullable = false)
        private long price;

        //default constructor
        public OrderItem(){}

        //parameterized constructor - creates new OrderItem instance
        public OrderItem(Long id, Long menuId, Order order, long price, int quantity) {
            this.id = id;
            this.menuId = menuId; //id of menu item orders
            this.order = order;//associated order object that this item belongs to
            this.price = price;
            this.quantity = quantity;
        }


        public OrderItem(Order order, Menu menuItem, int quantity, double price) {
        }


        // Getters and Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getMenuId() {
            return menuId;
        }

        public void setMenuId(Long menuId) {
            this.menuId = menuId;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
