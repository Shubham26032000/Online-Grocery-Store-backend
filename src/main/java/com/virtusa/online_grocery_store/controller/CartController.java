package com.virtusa.online_grocery_store.controller;

import com.virtusa.online_grocery_store.dto.CartDto;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.CartNotFoundException;
import com.virtusa.online_grocery_store.exceptions.ProductNotFoundException;
import com.virtusa.online_grocery_store.exceptions.ProductStockLimitedException;
import com.virtusa.online_grocery_store.exceptions.UserNotFoundException;
import com.virtusa.online_grocery_store.pojo.ApiResponse;
import com.virtusa.online_grocery_store.services.CartService;
import com.virtusa.online_grocery_store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user/cart/")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class.getName());

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @PostMapping("/addToCart")
    public ResponseEntity<Object> addToCart(@Valid @RequestBody CartDto cart,@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken)
    {
        CartDto cartEntity;
        try {
            cartEntity = cartService.addToCart(cart,jwtToken);
        }catch (UserNotFoundException e){
            logger.error("Invalid user ID");
            return new ResponseEntity<>(new ApiResponse(false,"Invalid User ID"),HttpStatus.NOT_FOUND);
        }catch (ProductNotFoundException e)
        {
            logger.error("Invalid Product ID");
            return new  ResponseEntity<>(new ApiResponse(false,"Invalid Product ID"),HttpStatus.NOT_FOUND);
        }catch (ProductStockLimitedException e)
        {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(cartEntity, HttpStatus.OK);
    }

    
    @PutMapping("/editCart/{id}")
    public ResponseEntity<ApiResponse> updateCart(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken,@PathVariable("id")Long cartId, @RequestParam int quantity)
    {
        try {
            cartService.getCartById(cartId);
            cartService.editCart(cartId,quantity,jwtToken);
        }catch (CartNotFoundException  | ProductStockLimitedException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Cart Updated Successfully!"),HttpStatus.OK);
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCart(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken,@PathVariable("id") long id)
    {
        try {
            cartService.getCartById(id);
            cartService.deleteCart(id,jwtToken);
        }catch (CartNotFoundException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new ApiResponse(true, "Cart Deleted Successfully!"),HttpStatus.OK);
    }

    

    @Transactional
    @DeleteMapping("/empty")
    public ResponseEntity<ApiResponse> removeCart(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken)
    {
//        try {
//         User user = userService.getUserById(jwtToken);
//        }catch (UserNotFoundException e){
//            return new ResponseEntity<>(new ApiResponse(false,"User not exist with provided ID"),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        cartService.deleteCartByUserId(jwtToken);
        return new ResponseEntity<>(new ApiResponse(true, "Successfully remove carts"),HttpStatus.OK);
    }

    /***
     * This will return cart list of specific user.
     * @return
     */
    @PostMapping("/getCartList")
    public ResponseEntity<Object> getAllCartOfUser(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken)
    {
//        try {
//         User user = userService.getUserById(jwtToken);
//        }catch (UserNotFoundException e){
//            logger.error("Invalid User ID");
//            return new ResponseEntity<>(new ApiResponse(false,"Invalid User ID"), HttpStatus.NOT_FOUND);
//        }
        List<CartDto> cartList = cartService.getCartListByUserId(jwtToken);
        return new ResponseEntity<>(cartList, HttpStatus.OK);
    }
}
