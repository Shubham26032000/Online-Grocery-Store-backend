package com.virtusa.online_grocery_store.pojo;

public class CartRequest {

    private long id;
    private int quantity;
    private long productId;
    private long userId;

    public CartRequest(int quantity, long productId, long userId) {
        this.quantity = quantity;
        this.productId = productId;
        this.userId = userId;
    }

    public CartRequest(long id, int quantity, long productId, long userId) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.userId = userId;
    }

    public CartRequest() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CartRequest{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productId=" + productId +
                ", userId=" + userId +
                '}';
    }
}
