package ru.kata.spring.boot_security.demo.Repositories;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Optional;

public interface RoleDao {
    Optional<Role> findRoleByName(String name);
}
