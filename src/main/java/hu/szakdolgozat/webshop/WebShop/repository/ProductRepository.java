package hu.szakdolgozat.webshop.WebShop.repository;

import hu.szakdolgozat.webshop.WebShop.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product findById(int id);
}
