package com.virtusa.online_grocery_store.repositories;

import com.virtusa.online_grocery_store.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUserId(Long userId);

    void  deleteByUserId(Long userId);

    Cart findByProductId(long id);

    List<Cart> findByProductIdAndUserId(long productId,long userId);
}
