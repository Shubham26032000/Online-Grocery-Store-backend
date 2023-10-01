package com.virtusa.online_grocery_store.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user_carts")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart() {
    }

    public Cart(int quantity, User user, Product product) {
        this.quantity = quantity;
        this.user = user;
        this.product = product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonBackReference(value = "product_carts")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
