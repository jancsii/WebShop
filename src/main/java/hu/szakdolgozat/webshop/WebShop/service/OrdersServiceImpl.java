package hu.szakdolgozat.webshop.WebShop.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hu.szakdolgozat.webshop.WebShop.entity.Orders;
import hu.szakdolgozat.webshop.WebShop.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SpringComponent
@UIScope
@Service
public class OrdersServiceImpl implements OrdersService{

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public Iterable<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public Orders getOrders(int id) {
        return ordersRepository.findById(id);
    }
}
