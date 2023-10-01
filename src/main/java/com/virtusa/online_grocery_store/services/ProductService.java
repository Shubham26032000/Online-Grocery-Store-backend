package com.virtusa.online_grocery_store.services;
import com.virtusa.online_grocery_store.entity.Product;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import java.util.List;


public interface ProductService {
    Product addProduct(Product product);

    Product updateProduct(Product product, Long productId);

    List<Product> getAllProducts();

    Product getProduct(long id);

    String deleteProduct(long id) throws Exception;

    List<Product> getProductsBySellerId(String jwtToken);

    List<Product> getProductsByCategory(String category);

}

