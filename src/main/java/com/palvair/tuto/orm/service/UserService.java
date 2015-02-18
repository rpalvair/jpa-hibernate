package com.palvair.tuto.orm.service;


import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.repository.UserRepository;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Log4j
public class UserService<T extends User> implements JpaService<T> {

    @PersistenceContext
    private EntityManager em;

    @Resource
    @Getter
    private UserRepository<User> userRepository;

    @Autowired
    private UserServiceDelegate delegate;

    public void log() {
        log.info("entityManager = " + em.toString());
    }

    public void saveRandomUser() {
        delegate.saveRandomUser();
    }

    public List<User> findAll() {
        final List<User> users = userRepository.findAll();
        return users;
    }

    public void saveRandomUser(int count) {
        delegate.saveRandomUser(count);
    }

    public void delete(Iterable<? extends User> entities) {
        delegate.delete(entities);
    }

    public void saveUser(final User user) {
        userRepository.save(user);
    }
}
