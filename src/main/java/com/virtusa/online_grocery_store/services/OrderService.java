package com.virtusa.online_grocery_store.services;

import com.virtusa.online_grocery_store.dto.CartDto;
import com.virtusa.online_grocery_store.dto.OrderDto;
import com.virtusa.online_grocery_store.entity.Order;
import com.virtusa.online_grocery_store.pojo.Delivered;
import com.virtusa.online_grocery_store.pojo.Paid;

import java.util.List;

public interface OrderService {
    boolean addOrder(String jwtToken);

    String updatePaidStatus(Long id, Paid paid);

    String updateDeliveredStatus(Long id, Delivered delivered);

    List<OrderDto> getAllOrders();

    List<OrderDto> getOrdersByUserId(String jwtToken);

    OrderDto getOrderById(long orderId,String jwtToken);

    OrderDto getOrderOfUser(long orderId);

    Order placeOrder(long cartId, String jwtToken);

    boolean cancelOrder(long orderId,String jwtToken);
}
