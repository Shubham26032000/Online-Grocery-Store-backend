package com.virtusa.online_grocery_store.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;
    private int quantity;
    @Column(name = "paid")
    private boolean isPaid;
    @Column(name = "delivered")
    private boolean isDelivered;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id"
    )
    private Product product;

    @Column(name = "created_date")
    private Date date;

    public Order() {
        this.isPaid = false;
        this.isDelivered = false;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonBackReference(value = "user_orders")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonBackReference(value = "product_orders")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", quantity=" + quantity +
                ", isPaid=" + isPaid +
                ", isDelivered=" + isDelivered +
                ", product=" + product +
                ", date=" + date +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
