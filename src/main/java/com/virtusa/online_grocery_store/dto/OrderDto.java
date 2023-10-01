package com.virtusa.online_grocery_store.dto;

import javax.validation.constraints.Min;
import java.util.Date;

public class OrderDto {
    private long id;

    @Min(value = 1,message = "Please provide userId")
    private long userId;
    @Min(value = 1,message = "Please provide quantity")
    private int quantity;
    private boolean paid;
    private boolean delivered;
    @Min(value = 1,message = "Please provide product Id")
    private long productId;

    private Date date;

    public OrderDto(long id, long userId, int quantity, boolean paid, boolean delivered, long productId, Date date) {
        this.id = id;
        this.userId = userId;
        this.quantity = quantity;
        this.paid = paid;
        this.delivered = delivered;
        this.productId = productId;
        this.date = date;
    }

    public OrderDto() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", paid=" + paid +
                ", delivered=" + delivered +
                ", productId=" + productId +
                ", date=" + date +
                '}';
    }
}
