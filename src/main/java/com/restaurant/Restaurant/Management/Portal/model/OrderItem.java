package com.restaurant.Restaurant.Management.Portal.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

    @Entity
    @Table(name = "order_items")
    public class OrderItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "order_id", nullable = false)
        private Order order;

        @Column(nullable = false)
        private Long menuId;

        @Column(nullable = false)
        private int quantity;

        @Column(nullable = false)
        private long price;

        public OrderItem(){}

        public OrderItem(Long id, Long menuId, Order order, long price, int quantity) {
            this.id = id;
            this.menuId = menuId;
            this.order = order;
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
