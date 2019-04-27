package hu.szakdolgozat.webshop.WebShop.service;

import hu.szakdolgozat.webshop.WebShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.szakdolgozat.webshop.WebShop.entity.Product;

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

    @Override
    public void save(Product product) {

        productRepository.saveAndFlush(product);
    }

    @Override
    public Product findByName(String name) { return productRepository.findByName(name); }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
