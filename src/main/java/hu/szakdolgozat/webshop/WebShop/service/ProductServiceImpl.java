package hu.szakdolgozat.webshop.WebShop.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hu.szakdolgozat.webshop.WebShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.szakdolgozat.webshop.WebShop.entity.Product;

import java.util.Optional;

@SpringComponent
@UIScope
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id);
    }
}
