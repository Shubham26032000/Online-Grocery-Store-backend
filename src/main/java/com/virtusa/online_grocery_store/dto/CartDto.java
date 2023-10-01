package com.virtusa.online_grocery_store.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CartDto {

    private long id;
    @Min(value = 1,message = "Please provide quantity")
    private int quantity;

    @Min(value = 1,message = "Please provide productId")
    private long productId;
//    @Min(value = 1,message = "Please provide userId")
//    private long userId;

//    public CartDto(int quantity, long productId, long userId) {
//        this.quantity = quantity;
//        this.productId = productId;
//        this.userId = userId;
//    }
    public CartDto(int quantity, long productId) {
        this.quantity = quantity;
        this.productId = productId;
    }
//    public CartDto(long id, int quantity, long productId, long userId) {
//        this.id = id;
//        this.quantity = quantity;
//        this.productId = productId;
//        this.userId = userId;
//    }
    public CartDto(long id, int quantity, long productId) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
    }
    public CartDto() {
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

//    public long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productId=" + productId +
//                ", userId=" + userId +
                '}';
    }
}
