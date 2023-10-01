package com.virtusa.online_grocery_store.security.userServices;


import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.UserNotFoundException;
import com.virtusa.online_grocery_store.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class.getName());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if(user == null)
        {
           logger.error ("User Not Present with name =  "+username);
        }
        return new UserDetailsImpl(user);
    }

}
