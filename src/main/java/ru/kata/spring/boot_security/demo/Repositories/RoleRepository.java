package ru.kata.spring.boot_security.demo.Repositories;


import org.hibernate.NonUniqueResultException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Repository
public class RoleRepository {
    @PersistenceContext
    EntityManager entityManager;
    public Role findRoleByName(String name) {
        try {
            TypedQuery<Role> tq = entityManager.createQuery("from Role WHERE name=:name", Role.class);
            Role result = tq.setParameter("name", name).getSingleResult();
            return  result;
        } catch(NoResultException noresult) {
            return null;
        } catch(NonUniqueResultException notUnique) {
            return null;
        }

    }
}
