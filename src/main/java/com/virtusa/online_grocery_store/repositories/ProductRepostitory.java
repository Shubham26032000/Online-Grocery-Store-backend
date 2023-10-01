package com.virtusa.online_grocery_store.repositories;

import com.virtusa.online_grocery_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepostitory extends JpaRepository<Product,Long> {

    List<Product> findBySellerId(long sellerId);

    List<Product> findByCategory(String category);

    List<Product> getBySellerId(long sellerId);
}
