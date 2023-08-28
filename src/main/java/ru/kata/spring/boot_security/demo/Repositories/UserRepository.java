package ru.kata.spring.boot_security.demo.Repositories;

import org.hibernate.NonUniqueResultException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.configs.ApplicationContextProvider;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.function.Function;

@Repository
public class UserRepository {
    @PersistenceContext
    EntityManager entityManager;

    public User findUserByUserName(String userName) {
        try {
            TypedQuery<User> tq = entityManager.createQuery("from User WHERE userName=:username", User.class);
            User result = tq.setParameter("username", userName).getSingleResult();
            return  result;
        } catch(NoResultException noresult) {
            return null;
        } catch(NonUniqueResultException notUnique) {
            return null;
        }

    }
    public User findUserById(Long id) {
        return (User) entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery(" FROM User ").getResultList();
    }

    public void deleteById(Long userId) {
        entityManager.createQuery("delete from User where id = :id").setParameter("id", userId).executeUpdate();
    }

    public void save(User user) {
        if (user.getId() == null && findUserByUserName(user.getUserName()) != null) {
            return; //user already exist
        }
        if (user.getRoles() == null) {
            RoleRepository roleRepository = ApplicationContextProvider.getApplicationContext().getBean(RoleRepository.class);
            user.setRoles(new HashSet<Role>(Arrays.asList(roleRepository.findRoleByName("USER"))));
        }
        entityManager.persist(user);
    }

    public void merge(User user) {
        entityManager.merge(user);
    }
}
