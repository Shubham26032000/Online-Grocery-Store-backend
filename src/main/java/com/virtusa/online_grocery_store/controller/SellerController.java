package com.virtusa.online_grocery_store.controller;

import com.virtusa.online_grocery_store.dto.OrderDto;
import com.virtusa.online_grocery_store.dto.ProductDto;
import com.virtusa.online_grocery_store.entity.Product;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.pojo.ApiResponse;
import com.virtusa.online_grocery_store.services.ProductService;
import com.virtusa.online_grocery_store.services.SellerService;
import com.virtusa.online_grocery_store.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {


    @Autowired
    SellerService sellerService;

    @Autowired
    UserService userService;


    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken)
    {
        List<OrderDto> orderList = sellerService.getAllOrders(jwtToken);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken)
    {
        return new ResponseEntity<>(sellerService.getAllProducts(jwtToken),HttpStatus.OK);
    }
    

    @PutMapping("/updateProduct")
    public ResponseEntity<ApiResponse> updateQuantity(@RequestBody ProductDto productDto, @RequestHeader(value = "authorization",defaultValue = "123") String jwtToken )
    {
        Product product = new Product();
        BeanUtils.copyProperties(productDto,product);
        boolean updated = sellerService.updateProduct(product, jwtToken);
        if(!updated)
            return new ResponseEntity<>(new ApiResponse(false,"Your're not authorized person to do this action"),HttpStatus.FORBIDDEN);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product Updated Successfully"),HttpStatus.OK);
    }

    
    @PostMapping("/product/add")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDto productDto, @RequestHeader(value = "authorization",defaultValue = "123") String jwtToken)
    {
        Product product = new Product();
        BeanUtils.copyProperties(productDto,product);
        User user = userService.getUserFromToken(jwtToken);
        product.setSellerId(user.getId());
        Product productResponse = sellerService.addProduct(product,jwtToken);
        return  new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    
    @DeleteMapping("/product/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken, @PathParam("productId") long productId)
    {
        try {
            boolean result =  sellerService.removeProductById(jwtToken, productId);
            if(result)
                return new ResponseEntity<>(new ApiResponse(true,"Successfully deleted product!"),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false,"Cannot delete product as it associated with orders!!"),HttpStatus.BAD_REQUEST);
        }

       return new ResponseEntity<>(new ApiResponse(false,"You are not authorized to do this action"),HttpStatus.OK);
    }
}