package hu.szakdolgozat.webshop.WebShop.service;

import hu.szakdolgozat.webshop.WebShop.entity.Orders;
import hu.szakdolgozat.webshop.WebShop.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void save(Orders orders) { ordersRepository.saveAndFlush(orders); }
}
