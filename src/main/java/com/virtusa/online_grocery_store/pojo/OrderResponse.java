package com.virtusa.online_grocery_store.pojo;

import java.util.Date;

public class OrderResponse {
    private long id;
    private long userId;
    private int quantity;
    private boolean paid;
    private boolean delivered;
    private long productId;

    private Date date;

    public OrderResponse(long id, long userId, int quantity, boolean paid, boolean delivered, long productId, Date date) {
        this.id = id;
        this.userId = userId;
        this.quantity = quantity;
        this.paid = paid;
        this.delivered = delivered;
        this.productId = productId;
        this.date = date;
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
        return "OrderResponse{" +
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
