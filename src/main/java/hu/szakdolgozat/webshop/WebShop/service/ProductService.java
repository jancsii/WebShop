package hu.szakdolgozat.webshop.WebShop.service;

import hu.szakdolgozat.webshop.WebShop.entity.Product;

public interface ProductService {

    Iterable<Product> getAllProducts();

    Product getProduct(int id);

    void save(Product product);

    Product findByName(String name);

    void delete(Product product);

}
