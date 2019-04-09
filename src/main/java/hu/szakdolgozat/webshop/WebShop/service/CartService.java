package hu.szakdolgozat.webshop.WebShop.service;

import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import org.springframework.stereotype.Service;


public interface CartService {

    Iterable<Cart> getAllCart();
    Cart getCart(int id);
    void save(Cart cart);
    void delete(Cart cart);
}
