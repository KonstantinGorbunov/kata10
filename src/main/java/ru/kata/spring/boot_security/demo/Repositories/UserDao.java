package ru.kata.spring.boot_security.demo.Repositories;

import ru.kata.spring.boot_security.demo.model.User;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findUserByUserName(String userName);

    User findUserById(Long id);

    List<User> findAll();

    void deleteById(Long userId);

    void save(User user) throws RoleNotFoundException;


    void merge(User user);
}
