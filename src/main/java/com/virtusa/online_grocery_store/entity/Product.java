package com.virtusa.online_grocery_store.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Please provide product name , product name must not empty")
    private String name;

    @NotNull(message = "Please provide price , price must not empty")
    private int price;

    @NotNull(message = "Please provide stock , stock must not empty")

    private int stock;

    @NotEmpty(message = "Please provide product imageUrl , product imageUrl must not empty")
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "upload_date")
    private Date uploadDate;

    @NotEmpty(message = "Please provide category , category must not empty")
    private String category;

    @NotNull(message = "Please provide sellerId , sellerId must not empty")
    @Column(name = "seller_id")
    private long sellerId;

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id"
    )
    private List<Order> orderEntities;

    public Product(String name, int price, int stock, String imageUrl, Date uploadDate, String category, long sellerId) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.uploadDate = uploadDate;
        this.category = category;
        this.sellerId = sellerId;
    }

    public Product() {
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

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", imageUrl='" + imageUrl + '\'' +
                ", uploadDate=" + uploadDate +
                ", category='" + category + '\'' +
                ", sellerId=" + sellerId +
                '}';
    }

}
