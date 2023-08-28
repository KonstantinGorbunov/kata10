package ru.kata.spring.boot_security.demo.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.Repositories.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repo;


    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User findUserByUserName(String name) {
        return  repo.findUserByUserName(name);
    }



    public User findUserById(Long id) {
        return  repo.findUserById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                authorities);
    }


    @Transactional
    public List<User> getUserList() {
        return repo.findAll();
    }

    @Transactional
    public void deleteUser(Long userId) {
        repo.deleteById(userId);
    }

    @Transactional
    public void addUser(User user) {
        repo.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        repo.merge(user);
    }
}
