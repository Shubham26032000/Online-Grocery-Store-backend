package com.virtusa.online_grocery_store.services;


import com.virtusa.online_grocery_store.dto.OrderDto;
import com.virtusa.online_grocery_store.entity.Product;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.ProductNotFoundException;
import com.virtusa.online_grocery_store.exceptions.SellerNotFoundException;
import com.virtusa.online_grocery_store.repositories.UserRepository;
import com.virtusa.online_grocery_store.security.jwtutils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SellerServiceImpl implements SellerService{

    private static final Logger logger = LoggerFactory.getLogger(SellerServiceImpl.class.getName());

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;


    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

   
    @Override
    public List<OrderDto> getAllOrders(String jwtToken) {
        if(!isSeller(jwtToken))
           throw new SellerNotFoundException("You're not authorized person to do this action");
        User user = userService.getUserFromToken(jwtToken);
        List<OrderDto> orderRespons = orderService.getAllOrders();
        List<OrderDto> orders = new ArrayList<>();
        //extract the orders which are related to current seller
        orderRespons.stream().forEach(o ->{
            Product product = productService.getProduct(o.getProductId());
            if(product.getSellerId() == user.getId())
                orders.add(o);
        });
        return orders;
    }

    @Override
    public List<Product> getAllProducts(String jwtToken) {
    	//validate seller
        User user = userService.getUserFromToken(jwtToken);
        if (!isSeller(jwtToken))
            return null;
        return productService.getProductsBySellerId(jwtToken);
    }

    @Override
    public boolean updateProduct(Product product, String jwtToken) {

        if (canUpdateProduct(jwtToken, product.getId()))
        {
            try {
                productService.updateProduct(product, product.getId());
                return true;
            }catch (ProductNotFoundException e)
            {
                logger.error(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public Product addProduct(Product product, String jwtToken) {
        if (!isSeller(jwtToken))
             throw new SellerNotFoundException("You are not authorized person to do this action");
        User user = userService.getUserFromToken(jwtToken);
        product.setSellerId(user.getId());
        return productService.addProduct(product);
    }

    @Override
    public boolean removeProductById(String jwtToken, long productId) throws Exception {
        if(isSeller(jwtToken) && canUpdateProduct(jwtToken,productId))
        {
            productService.deleteProduct(productId);
            return true;
        }
        return false;
    }

    /**
     * it check whether sellerId is valid or not
     * @return
     */
    private boolean isSeller(String jwtToken)
    {
        User user = userService.getUserFromToken(jwtToken);
//        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
//        List<User> seller = userRepository.findByRole("SELLER");
//        seller.forEach(currentUser ->{
//            if(currentUser.getId() == user.getId())
//                atomicBoolean.set(true);
//        });
        return user.getRole().equals("SELLER");
    }

    /***
     * This helper method check whether the current product is added by current seller.
     * @param productId
     * @return
     */
    private boolean canUpdateProduct(String jwtToken, long productId)
    {

        User user = userService.getUserFromToken(jwtToken);
        AtomicBoolean result = new AtomicBoolean(false);
        if(user.getRole().equals("SELLER"))
        {
            List<Product> products = productService.getProductsBySellerId(jwtToken);
            products.stream().forEach(product -> {
                if(product.getId() == productId)
                    result.set(true);
            });

        }
        return result.get();
    }
}
