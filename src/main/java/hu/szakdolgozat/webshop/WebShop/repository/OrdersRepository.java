package hu.szakdolgozat.webshop.WebShop.repository;

import hu.szakdolgozat.webshop.WebShop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Orders findById(int id);
}
