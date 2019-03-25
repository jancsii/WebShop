package hu.szakdolgozat.webshop.WebShop.repository;

import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findById(int id);
}
