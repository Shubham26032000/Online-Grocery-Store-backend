package com.virtusa.online_grocery_store.repositories;

import com.virtusa.online_grocery_store.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(long id);

}
