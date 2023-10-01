package com.virtusa.online_grocery_store.repositories;

import com.virtusa.online_grocery_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    User findByUserName(String userName);

    User findByEmail(String email);

    List<User> findByRole(String role);
}
