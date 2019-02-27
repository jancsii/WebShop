package hu.szakdolgozat.webshop.WebShop.service;

import hu.szakdolgozat.webshop.WebShop.entity.Orders;

public interface OrdersService {

    Iterable<Orders> getAllOrders();
    Orders getOrders(int id);
}
