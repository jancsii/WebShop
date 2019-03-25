package hu.szakdolgozat.webshop.WebShop.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hu.szakdolgozat.webshop.WebShop.entity.Cart;
import hu.szakdolgozat.webshop.WebShop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SpringComponent
@UIScope
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
}
