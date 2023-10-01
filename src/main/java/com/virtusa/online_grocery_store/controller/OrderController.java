package com.virtusa.online_grocery_store.controller;


import com.virtusa.online_grocery_store.dto.CartDto;
import com.virtusa.online_grocery_store.dto.OrderDto;
import com.virtusa.online_grocery_store.entity.Cart;
import com.virtusa.online_grocery_store.entity.Order;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.*;
import com.virtusa.online_grocery_store.pojo.ApiResponse;
import com.virtusa.online_grocery_store.pojo.Paid;
import com.virtusa.online_grocery_store.services.OrderService;
import com.virtusa.online_grocery_store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/order/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class.getName());
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @PostMapping("/orderall")
    public ResponseEntity<ApiResponse> addOrder(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken)
    {
        try {
            orderService.addOrder(jwtToken);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.OK);
        }
         return new ResponseEntity<>(new ApiResponse(true, "Orders added successfully"), HttpStatus.OK);
    }


    /***
     * It takes cart Object to add it to order
     */
    @PostMapping("/placeOrder")
    public ResponseEntity<ApiResponse> placeOrder(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken,@RequestParam("cartId") long cartId)
    {
        try {
            orderService.placeOrder(cartId,jwtToken);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Order place successfully!"),HttpStatus.OK);
    }


    /***
     *
     * @return All orders of users
     */
    @GetMapping("")
    public ResponseEntity<Object> getOrderByUserId(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken)
    {
//        try {
           User user= userService.getUserById(jwtToken);
//        }catch (UserNotFoundException e){
//            logger.error("User not found, Wrong user ID");
//            return new ResponseEntity<>(new ApiResponse(false,"User not found with ID : "+id), HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        List<OrderDto> orderList = orderService.getOrdersByUserId(jwtToken);
        return new ResponseEntity<>(orderList,HttpStatus.OK);
    }
    
    /***
     * 
     * @param id (OrderId)
     * @param paid (Paid Status)
     * @return
     */
    @PutMapping("/paid/{id}")
    public ResponseEntity<ApiResponse> updatePaidStatus(@PathVariable("id") Long id, @RequestBody Paid paid)
    {
        try {
            orderService.getOrderOfUser(id);
        }catch (OrderNotFoundException e)
        {
            logger.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, "Order not exist with given id : "+id),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        orderService.updatePaidStatus(id,paid);

        return new ResponseEntity<>(new ApiResponse(true, "Order updated successfully!"),HttpStatus.OK);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<Object> cancelOrder(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken, @RequestParam("orderId") long orderId)
    {
        try {
            orderService.cancelOrder(orderId,jwtToken);
        }catch (UserNotFoundException | OrderNotFoundException | OrderCannotDelete e)
        {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse(true, "Order cancelled successfully!"),HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Object> getOrderById(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken,@PathVariable long id)
    {
        OrderDto orderDto = null;
        try{
            orderService.getOrderById(id,jwtToken);
            orderDto = orderService.getOrderOfUser(id);
        }catch (Exception e){
          return   new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }
}