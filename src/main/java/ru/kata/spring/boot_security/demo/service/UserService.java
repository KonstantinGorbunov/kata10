package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserService {
    Optional<User> findUserByUserName(String name);

    User findUserById(Long id);

    List<User> getUserList();

    void deleteUser(Long userId);

    void addUser(User user);

    void updateUser(User user);
}
