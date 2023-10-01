package com.virtusa.online_grocery_store.services;

import com.virtusa.online_grocery_store.dto.OrderDto;
import com.virtusa.online_grocery_store.entity.Product;

import java.util.List;

public interface SellerService {

    List<OrderDto> getAllOrders(String jwtToken);

    List<Product> getAllProducts(String jwtToken);

    boolean  updateProduct(Product product, String jwtToken);

    Product addProduct(Product product, String jwtToken);

    boolean removeProductById(String jwtToken, long productId) throws Exception;
}
