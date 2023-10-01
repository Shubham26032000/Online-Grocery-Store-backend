package com.virtusa.online_grocery_store.services;


import com.virtusa.online_grocery_store.dto.CartDto;
import com.virtusa.online_grocery_store.entity.Cart;

import java.util.List;

public interface CartService {
    CartDto addToCart(CartDto cart,String jwtToken);

    String deleteCart(long id,String jwtToken);

    Cart editCart(long id, int quantity,String jwtToken);

    List<CartDto> getCartListByUserId(String jwtToken);

    String deleteCartByUserId(String jwtToekn);

    Cart getCartById(Long cartId);
}
