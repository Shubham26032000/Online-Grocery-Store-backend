package com.virtusa.online_grocery_store.controller;

import com.virtusa.online_grocery_store.dto.UserDto;
import com.virtusa.online_grocery_store.entity.Product;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.ProductNotFoundException;
import com.virtusa.online_grocery_store.pojo.ApiResponse;
import com.virtusa.online_grocery_store.pojo.JwtRequest;
import com.virtusa.online_grocery_store.pojo.JwtResponse;
import com.virtusa.online_grocery_store.pojo.Login;
import com.virtusa.online_grocery_store.security.jwtutils.JwtUtil;
import com.virtusa.online_grocery_store.security.userServices.UserDetailServiceImpl;
import com.virtusa.online_grocery_store.services.ProductService;
import com.virtusa.online_grocery_store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDto user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        logger.info("User Entity : "+user);
        User user1  = new User();
        BeanUtils.copyProperties(user,user1);
        try {
            userService.createUser(user1);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, "Email id already exist !"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Successfully SignUp !"),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> findUserByPassword(@Valid @RequestBody Login userLogin) throws Exception{
        logger.info("User Login "+userLogin);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
        } catch (HttpClientErrorException.BadRequest e)
        {
            logger.error("Bad Credential !  "+e.getMessage());
        }

        UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(userLogin.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/token")
    public ResponseEntity<Object> createTocken(@RequestBody JwtRequest request) throws Exception
    {
        logger.info("Request : "+request);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request. getEmail(), request.getPassword()));
        }catch (HttpClientErrorException.BadRequest e) {
            e.printStackTrace();
            throw new Exception("Bad Credentil");
        }

        UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        logger.info("Token : "+token);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    /***
     * All product by ID
     * @param id  (productId)
     * @return
     */
    @GetMapping("/products/get/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") Long id)
    {
        try {
            productService.getProduct(id);
        }catch (ProductNotFoundException e){
            return new ResponseEntity<>(new ApiResponse(false,"Product Not Found with ID "+id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Product productEntity = productService.getProduct(id);
        return new ResponseEntity<>(productEntity, HttpStatus.OK);
    }

    /***
     *it returns all products 
     * @return ProductList
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    
    /***
     * productCategory
     * @param category
     * @return productOfSpecific category
     */
    @GetMapping("/products/category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam("category") String category)
    {
        List<Product> productList = productService.getProductsByCategory(category);
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }
}
