package hu.szakdolgozat.webshop.WebShop.repository;

import hu.szakdolgozat.webshop.WebShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findById(int id);
    Product findByName(String name);
}
