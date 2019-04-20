package hu.szakdolgozat.webshop.WebShop.service;

import hu.szakdolgozat.webshop.WebShop.entity.User;

public interface UserService {

    void save(User user);
    void delete(User user);
    Iterable<User> getAllUsers();
    User findByFirstName(String name);
    User findByUserName(String name);
    User getUser(int id);
}
