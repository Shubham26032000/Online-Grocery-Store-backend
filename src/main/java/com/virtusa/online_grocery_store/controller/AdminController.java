package com.virtusa.online_grocery_store.controller;


import com.virtusa.online_grocery_store.dto.OrderDto;
import com.virtusa.online_grocery_store.dto.ProductDto;
import com.virtusa.online_grocery_store.entity.Order;
import com.virtusa.online_grocery_store.entity.Product;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.OrderException;
import com.virtusa.online_grocery_store.exceptions.OrderNotFoundException;
import com.virtusa.online_grocery_store.exceptions.ProductNotFoundException;
import com.virtusa.online_grocery_store.pojo.ApiResponse;
import com.virtusa.online_grocery_store.pojo.Delivered;
import com.virtusa.online_grocery_store.services.OrderService;
import com.virtusa.online_grocery_store.services.ProductService;
import com.virtusa.online_grocery_store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class.getName());
    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    /***
     * It will return all the users in System
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser()
    {
        List<User> userList = userService.findUserByRole("USER");
        return new  ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    
    /**
     * All Sellers in System
     * @return
     */
    @GetMapping("/sellers")
    public ResponseEntity<List<User>> getAllSellers()
    {
        List<User> userList = userService.findUserByRole("SELLER");
        return new  ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders()
    {
        List<OrderDto> orderList =orderService.getAllOrders();
        return new ResponseEntity<>(orderList,HttpStatus.OK);
    }

    
    
    @PutMapping("/orders/delivery/{orderId}")
    public ResponseEntity<ApiResponse> updateDeliveryStatus(@PathVariable("orderId") Long orderId, @RequestBody Delivered delivered)
    {
        try {
            orderService.getOrderOfUser(orderId);
        }catch (OrderNotFoundException  | OrderException exception){
            logger.error(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,"Order not exist!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        orderService.updateDeliveredStatus(orderId,delivered);
        return new ResponseEntity<>(new ApiResponse(true, "Delivery Status updated successfully!"),HttpStatus.OK);
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return new ResponseEntity<>( productService.getAllProducts(), HttpStatus.OK);
    }

    
    
    @PutMapping("/products/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") Long productId)
    {
        try {
            productService.getProduct(productId);
            //Check for product whether it exist or not
        }catch (ProductNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, "Product with provided ID is not present!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Product product = new Product();
        BeanUtils.copyProperties(productDto,product);
        productService.updateProduct(product,productId);
        return new ResponseEntity<>(new ApiResponse(true, "Product Updated!"), HttpStatus.OK);
    }

    
    
//    @Transactional
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") Long id){
        try {
            productService.getProduct(id); //fetch product from repository
            productService.deleteProduct(id);
        }catch(ProductNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, "Product with given ID not exist!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false,"Cannot delete product, as it present in some orders"),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse(true, "Product Deleted Successfully"), HttpStatus.OK);
    }


}
