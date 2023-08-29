package ru.kata.spring.boot_security.demo.Repositories;


import org.hibernate.NonUniqueResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;


@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findRoleByName(String name) {
        try {
            TypedQuery<Role> tq = entityManager.createQuery("from Role WHERE name=:name", Role.class);
            return Optional.of(tq.setParameter("name", name).getSingleResult());
        } catch(NoResultException noresult) {
            return Optional.ofNullable(null);
        } catch(NonUniqueResultException notUnique) {
            return Optional.ofNullable(null);
        }

    }
}
