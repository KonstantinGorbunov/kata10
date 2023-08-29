package ru.kata.spring.boot_security.demo.Repositories;

import org.hibernate.NonUniqueResultException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.configs.ApplicationContextProvider;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.management.relation.RoleNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    EntityManager entityManager;

    public Optional<User> findUserByUserName(String userName) {
        try {
            TypedQuery<User> tq = entityManager.createQuery("from User WHERE userName=:username", User.class);
            User result = tq.setParameter("username", userName).getSingleResult();
            return  Optional.of(result);
        } catch(NoResultException noresult) {
            return Optional.ofNullable(null);
        } catch(NonUniqueResultException notUnique) {
            return Optional.ofNullable(null);
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

    public void save(User user) throws RoleNotFoundException {
        if (user.getId() == null && findUserByUserName(user.getUserName()).isPresent()) {
            return; //user already exist
        }
        if (user.getRoles() == null) {
            RoleService roleService = ApplicationContextProvider.getApplicationContext().getBean(RoleService.class);
            user.setRoles(new HashSet<Role>(Arrays.asList(roleService.findRoleByName("USER").orElseThrow(() -> new RoleNotFoundException("USER")))));
        }
        entityManager.persist(user);
    }

    public void merge(User user) {
        entityManager.merge(user);
    }
}
