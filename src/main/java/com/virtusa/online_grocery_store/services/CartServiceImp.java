package com.virtusa.online_grocery_store.services;


import com.virtusa.online_grocery_store.dto.CartDto;
import com.virtusa.online_grocery_store.entity.Cart;
import com.virtusa.online_grocery_store.entity.Product;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.CartNotFoundException;
import com.virtusa.online_grocery_store.exceptions.ProductNotFoundException;
import com.virtusa.online_grocery_store.exceptions.ProductStockLimitedException;
import com.virtusa.online_grocery_store.exceptions.UserNotFoundException;
import com.virtusa.online_grocery_store.repositories.CartRepository;
import com.virtusa.online_grocery_store.repositories.ProductRepostitory;
import com.virtusa.online_grocery_store.repositories.UserRepository;
import com.virtusa.online_grocery_store.security.jwtutils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImp implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImp.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepostitory productRepostitory;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public CartDto addToCart(CartDto cart,String jwtToken) {

        Cart cartEntity;
        User user = getUserFromToken(jwtToken);
        List<Cart> existingCart = cartRepository.findByProductIdAndUserId(cart.getProductId(),user.getId());
        // check whether cart is already present or not
        if (!existingCart.isEmpty()) {
            cartEntity = existingCart.get(0);
            cartEntity.setQuantity(cartEntity.getQuantity() + cart.getQuantity());
        } else {
            cartEntity = new Cart();
            cartEntity.setQuantity(cart.getQuantity());
        }

//        Optional<User> userOptional = userRepository.findById(cart.getUserId());
//        //check for valid user
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            cartEntity.setUser(user);
//        } else {
//            throw new UserNotFoundException("User not found");
//        }


        cartEntity.setUser(user);
        Optional<Product> productOptional = productRepostitory.findById(cart.getProductId());
        //Check for valid productID
        if (canAddQuantity(cart.getProductId(), cart.getQuantity())) {
            Product product = productOptional.get();
            cartEntity.setProduct(product);
        } else {
            throw new ProductStockLimitedException("Please add quantity within stock , current stock = " + productOptional.get().getStock());
        }


        Cart savCart = this.cartRepository.save(cartEntity);
        cart.setId(savCart.getId());
        logger.info("Cart added : " + cart);
        return cart;
    }

    @Override
    public String deleteCart(long id,String jwtToken) {
        User user = getUserFromToken(jwtToken);
        Cart cart = getCartById(id);
        if(user.getId() != cart.getUser().getId())
        {
            throw new CartNotFoundException("You're not authorized person to do this action as this cart it not associated with you");
        }

        try {
            cartRepository.deleteById(id);
        } catch (Exception e) {
            throw new CartNotFoundException("Error occurs while deleting cart" + id);
        }

        logger.info("Cart deleted with ID : " + id);
        return "Deleted Successfully";
    }

    @Override
    public Cart editCart(long id, int quantity,String jwtToken) {
        Cart cart;
        Optional<Cart> cartOptional = cartRepository.findById(id);
        User user = getUserFromToken(jwtToken);
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            logger.error("Error while editing cart ");
            throw new CartNotFoundException("Cart not found !! with ID " + id);
        }
        //Check whether the givenCart is associated with currentUser
        if (user.getId() != cart.getUser().getId())
        {
            throw new CartNotFoundException("You're not authorized user to edit this card");
        }
        if (!canAddQuantity(cart.getProduct().getId(), quantity))
            throw new ProductStockLimitedException("Please add quantity within stock , current stock = " + cart.getProduct().getStock());
        cart.setQuantity(quantity);
        logger.info("Cart updated : " + cart);

        return this.cartRepository.save(cart);
    }

    @Override
    public List<CartDto> getCartListByUserId(String jwtToken) {
        List<CartDto> cartList = new ArrayList<>();
        User user = getUserFromToken(jwtToken);
        cartRepository.findByUserId(user.getId()).forEach(cart -> {
            cartList.add(new CartDto(cart.getId(), cart.getQuantity(), cart.getProduct().getId()));
        });
        logger.info("Cart list of userId = " + user.getId() + " --> \n" + cartList);
        return cartList;
    }

    @Override
    public String deleteCartByUserId(String jwtToken) {
        User user = getUserFromToken(jwtToken);
        try {
            cartRepository.deleteByUserId(user.getId());
        } catch (Exception e) {
            throw new CartNotFoundException("Cart not present with user id !" +user.getId());
        }
        logger.info("All Cart deleted of userId = " + user.getId());
        return "Deleted Successfully ";
    }

    @Override
    public Cart getCartById(Long cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        Cart cart = null;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
            return cart;
        } else {
            throw new CartNotFoundException("Invalid Cart ID");
        }
    }

    private boolean canAddQuantity(long productId, long quantity) {
        Optional<Product> productOptional = productRepostitory.findById(productId);
        if (productOptional.isPresent()) {
            if (productOptional.get().getStock() < quantity)
                return false;
        } else {
            throw new ProductNotFoundException("Product Not found!!");
        }

        return true;
    }

    public User getUserFromToken(String jwtToken) {
        String token = jwtToken.substring(7);
        String email = jwtUtil.extractUsername(token);
        return userRepository.findByEmail(email);
    }
}
