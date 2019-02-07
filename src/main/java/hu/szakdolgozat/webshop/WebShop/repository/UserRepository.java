package hu.szakdolgozat.webshop.WebShop.repository;

import hu.szakdolgozat.webshop.WebShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //@Query("select u from User u where u.first_name = :firstName")
    User findByFirstName(String firstname);//(@Param("first_name") String firstName);
    User findByUserName(String username);

}
