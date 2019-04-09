package hu.szakdolgozat.webshop.WebShop.service;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hu.szakdolgozat.webshop.WebShop.entity.User;
import hu.szakdolgozat.webshop.WebShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{


    @Autowired
    UserRepository userRepository;

    @Override
    public void save(User user) {

        userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(User user) {

        userRepository.delete(user);
    }

    @Override
    public Iterable<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User findByFirstName(String name) {
        return userRepository.findByFirstName(name);
    }

    @Override
    public User findByUserName(String name) {
        return userRepository.findByUserName(name);
    }
}
