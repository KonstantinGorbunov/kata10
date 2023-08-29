package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.Repositories.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import javax.management.relation.RoleNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUserName(String name) {
        return  userDao.findUserByUserName(name);
    }


    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return  userDao.findUserById(id);
    }




    @Transactional(readOnly = true)
    public List<User> getUserList() {
        return userDao.findAll();
    }

    @Transactional
    public void deleteUser(Long userId) {
        userDao.deleteById(userId);
    }

    @Transactional
    public void addUser(User user) {
        try {
            userDao.save(user);
        } catch (RoleNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateUser(User user) {
        userDao.merge(user);
    }
}
