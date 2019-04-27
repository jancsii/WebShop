package hu.szakdolgozat.webshop.WebShop.service;

import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import hu.szakdolgozat.webshop.WebShop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Override
    public Iterable<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getCart(int id) {
        return cartRepository.findById(id);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.saveAndFlush(cart);
    }

    @Override
    public void delete(Cart cart) { cartRepository.delete(cart); }
}
