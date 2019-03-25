package hu.szakdolgozat.webshop.WebShop.service;

import hu.szakdolgozat.webshop.WebShop.entity.Cart;

public interface CartService {

    Iterable<Cart> getAllCart();
    Cart getCart(int id);
    void save(Cart cart);
}
