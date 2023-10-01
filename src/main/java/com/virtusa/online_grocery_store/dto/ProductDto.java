package com.virtusa.online_grocery_store.dto;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ProductDto {

    private long id;

    @NotEmpty(message = "Please provide product name , product name must not empty")
    private String name;

    @NotNull(message = "Please provide price , price must not empty")
    private int price;

    @NotNull(message = "Please provide stock , stock must not empty")

    private int stock;

    @NotEmpty(message = "Please provide product imageUrl , product imageUrl must not empty")
    private String imageUrl;


    private Date uploadDate;

    @NotEmpty(message = "Please provide category , category must not empty")
    private String category;

    @NotNull(message = "Please provide sellerId , sellerId must not empty")
    private long sellerId;



    public ProductDto() {
    }

    public ProductDto(String name, int price, int stock, String imageUrl, Date uploadDate, String category, long sellerId) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.uploadDate = uploadDate;
        this.category = category;
        this.sellerId = sellerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }
}
