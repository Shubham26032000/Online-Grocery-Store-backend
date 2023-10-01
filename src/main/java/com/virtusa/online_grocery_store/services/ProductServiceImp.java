package com.virtusa.online_grocery_store.services;



import com.virtusa.online_grocery_store.entity.Product;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.ProductNotFoundException;
import com.virtusa.online_grocery_store.exceptions.SellerNotFoundException;
import com.virtusa.online_grocery_store.repositories.ProductRepostitory;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class ProductServiceImp implements ProductService{
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
    @Autowired
    ProductRepostitory repostitory;

    @Autowired
    UserService userService;

    @Override
    public Product addProduct(Product product) {
        AtomicBoolean isSeller = new AtomicBoolean(false);
        List<User> sellerList = userService.findUserByRole("SELLER");
        sellerList.stream().forEach(u ->{
            if (u.getId() == product.getSellerId())
                isSeller.set(true);
        });
        if(!isSeller.get())
            throw new SellerNotFoundException("Only Seller can add products");
        product.setUploadDate(new Date());
        product.setCategory(product.getCategory().toLowerCase());
        logger.info("Product saved ! +"+repostitory.save(product));
        return product;
    }

    @Override
    public Product updateProduct(Product product, Long productID) {
        Product productEntity;
            Optional<Product> optionalProduct = repostitory.findById(productID);
            if (optionalProduct.isPresent())
            {
                productEntity= optionalProduct.get();
            }else{
                throw new ProductNotFoundException("Invalid Product ID");
            }
            if(product.getName() != null)
                productEntity.setName(product.getName());
            if(product.getImageUrl() != null)
                productEntity.setImageUrl(product.getImageUrl());
            if(product.getPrice() != 0)
                productEntity.setPrice(product.getPrice());
            if(product.getStock() != 0)
                productEntity.setStock(product.getStock());
            if(product.getCategory() != null)
            {
                productEntity.setCategory(product.getCategory().toLowerCase());
            }

        logger.info("Product saved ! "+product);
        repostitory.save(productEntity);
        return productEntity;
    }

    @Override
    public List<Product> getAllProducts() {
        return repostitory.findAll();
    }

    @Override
    public Product getProduct(long id) {
        Product product;
        Optional<Product> productOptional = repostitory.findById(id);
        if (productOptional.isPresent())
        {

            product = productOptional.get();
        }else {
            throw new ProductNotFoundException("Product not found! with id : "+id);
        }
        logger.info("Product : "+product);
        return product;
    }

    @Override
    public String deleteProduct(long id)  {
        Optional<Product> productOptional = repostitory.findById(id);
        if (!productOptional.isPresent())
        {
            throw new ProductNotFoundException("Product not found! with id : "+id);
        }
        repostitory.deleteById(id);
        logger.info("Product with ID="+id+" Deleted!");
        return "Deleted Successfully";
    }

    @Override
    public List<Product> getProductsBySellerId(String jwtToken) {
        User user = userService.getUserFromToken(jwtToken);
        return repostitory.findBySellerId(user.getId());
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        category = category.toLowerCase().trim();
        return repostitory.findByCategory(category);
    }

}
