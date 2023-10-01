package com.virtusa.online_grocery_store.services;

import com.virtusa.online_grocery_store.dto.CartDto;
import com.virtusa.online_grocery_store.dto.OrderDto;
import com.virtusa.online_grocery_store.entity.Cart;
import com.virtusa.online_grocery_store.entity.Order;
import com.virtusa.online_grocery_store.entity.Product;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.*;
import com.virtusa.online_grocery_store.pojo.Delivered;
import com.virtusa.online_grocery_store.pojo.Paid;
import com.virtusa.online_grocery_store.repositories.CartRepository;
import com.virtusa.online_grocery_store.repositories.OrderRepository;
import com.virtusa.online_grocery_store.repositories.ProductRepostitory;
import com.virtusa.online_grocery_store.repositories.UserRepository;
import com.virtusa.online_grocery_store.security.jwtutils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepostitory productRepostitory;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CartService cartService;

    @Override
    public boolean addOrder(String jwtToken) {
        try {
           User user = userService.getUserById(jwtToken);
            List<Cart> cartList = cartRepository.findByUserId(user.getId());
            if(cartList.isEmpty())
                throw new EmptyCartException("Cart is empty");
            cartList.forEach(cart -> {
                Order order = new Order();
                order.setUser(cart.getUser());
                order.setProduct(cart.getProduct());
                order.setQuantity(cart.getQuantity());
                Date date = new Date();
                order.setDate(date);
                if (cart.getProduct().getStock() < cart.getQuantity())
                    throw new ProductStockLimitedException("Please Edit cart quantity of {"+cart.getId()+"} , quantity exceeds the product stock");
                orderRepository.save(order);
                cart.getProduct().setStock(cart.getProduct().getStock() - cart.getQuantity());
                productRepostitory.save(cart.getProduct());
                cartRepository.deleteById(cart.getId());
            });
        }catch (UserNotFoundException e){
            throw new UserNotFoundException("Invalid user ID!!");
        }catch (Exception e){
            throw new OrderException("Some error occurs while placing order!!");
        }
        logger.info("Order added successfully");
        return true;
    }

    @Override
    public String updatePaidStatus(Long id, Paid paid) {
        Order order;
            Optional<Order> orderOptional = orderRepository.findById(id);
            if(orderOptional.isPresent())
            {
                order = orderOptional.get();
            }else{
                throw new OrderNotFoundException("Order not found with given ID !");
            }


        logger.info("order paid status : "+paid);
        order.setPaid(paid.isStatus());
        orderRepository.save(order);
        return "Paid status updated successfully!!";
    }

    @Override
    public String updateDeliveredStatus(Long id, Delivered delivered) {
        Order order;
            Optional<Order> orderOptional = orderRepository.findById(id);
            //Check for valid order
            if(orderOptional.isPresent())
            {
                order = orderOptional.get();
            }else{
                throw new OrderNotFoundException("Order ID not present!");
            }
        order.setDelivered(delivered.isStatus());
        orderRepository.save(order);
        logger.info("Product id : "+id+" Delivery status : "+delivered.isStatus());
        return "Delivery status updated !";
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderDto> orderDto = new ArrayList<>();
        for (Order order:orderList)
        {
            orderDto.add(new OrderDto(order.getId(),order.getUser().getId(),order.getQuantity(),order.isPaid(),order.isDelivered(),order.getProduct().getId(),order.getDate()));
            logger.info(orderDto.toString());
        }
        logger.info("Order Fetched : "+orderList);
        return orderDto;
    }

    @Override
    public List<OrderDto> getOrdersByUserId(String jwtToken) {
        List<OrderDto> result = new ArrayList<>();
        User user = userService.getUserFromToken(jwtToken);
        orderRepository.findByUserId(user.getId()).forEach((order) ->{
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setUserId(order.getUser().getId());
            orderDto.setDate(order.getDate());
            orderDto.setDelivered(order.isDelivered());
            orderDto.setPaid(order.isPaid());
            orderDto.setQuantity(order.getQuantity());
            orderDto.setProductId(order.getProduct().getId());
            result.add(orderDto);
        });
        return result;
    }

    @Override
    public OrderDto getOrderById(long orderId, String jwtToken) {
        User user = userService.getUserFromToken(jwtToken);
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        //check for valid orderid
        if(orderOptional.isPresent())
        {
            Order order = orderOptional.get();
            //check for authorize user to fetch order
            if(user.getId() != order.getUser().getId())
                throw new InvalidCredentialException("You're not authorize person , this order is not associated with you");
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setDate(order.getDate());
            orderDto.setDelivered(order.isDelivered());
            orderDto.setPaid(order.isPaid());
            orderDto.setQuantity(order.getQuantity());
            orderDto.setProductId(order.getProduct().getId());
            orderDto.setUserId(order.getUser().getId());
            return orderDto;
        }else {
            throw new OrderNotFoundException("Invalid order id");
        }
    }

    @Override
    public OrderDto getOrderOfUser(long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isPresent())
        {
            Order order = orderOptional.get();
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setDate(order.getDate());
            orderDto.setDelivered(order.isDelivered());
            orderDto.setPaid(order.isPaid());
            orderDto.setQuantity(order.getQuantity());
            orderDto.setProductId(order.getProduct().getId());
            orderDto.setUserId(order.getUser().getId());
            return orderDto;
        }else {
            throw new OrderNotFoundException("Invalid order id");
        }
    }

    @Override
    public Order placeOrder(long cartId, String jwtToken) {
        Cart cart = cartService.getCartById(cartId);
        User user = userService.getUserFromToken(jwtToken);
        if(cart.getUser().getId() != user.getId())
        {
            throw new CartNotFoundException("You're not authorized person as this card is not associated with you");
        }
         Order orderEntity;
            orderEntity = new Order();
            orderEntity.setUser(cart.getUser());
            orderEntity.setProduct(cart.getProduct());
            orderEntity.setQuantity(cart.getQuantity());
            orderEntity.setDate(new Date());
        Order order = orderRepository.save(orderEntity);
        logger.info("Order saved ! Order = "+order);
        
        //Delete the cart if order place successfully
        if(orderRepository.existsById(order.getId()))
        {
            cart.getProduct().setStock(cart.getProduct().getStock()-cart.getQuantity());
            productRepostitory.save(cart.getProduct());
            cartRepository.deleteById(cart.getId());
        }
        return order;

    }

    @Override
    public boolean cancelOrder(long orderId, String jwtToken) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        //check for valid id
        if(!orderOptional.isPresent())
        {
            throw new OrderNotFoundException("Order not found with Order ID "+orderId);
        }

        OrderDto order = getOrderOfUser(orderId);
        List<OrderDto> orderList = getOrdersByUserId(jwtToken);
        boolean canDeleteOrder = orderList.stream().anyMatch(o -> o.getId() == orderId);
        if(canDeleteOrder) {
            //If cancelled order then update product stock
            Product product = productRepostitory.findById(order.getProductId()).get();
            product.setStock(product.getStock()+order.getQuantity());
            productRepostitory.save(product);
            orderRepository.deleteById(orderId);
        }else {
            throw new OrderCannotDelete("Can't delete the order as it's not associated with userId : " + userService.getUserFromToken(jwtToken));
        }
        return true;
    }


}
