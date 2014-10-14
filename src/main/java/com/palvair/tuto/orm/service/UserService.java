package com.palvair.tuto.orm.service;


import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Log4j
public class UserService implements JpaService {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserRepository userRepository;

    public void log() {
        log.info("entityManager = " + em.toString());
    }

    public void saveRandomUser() {
        final String firstname = RandomStringUtils.randomAlphabetic(5);
        final String lastname = RandomStringUtils.randomAlphabetic(5);
        final String age = RandomStringUtils.randomNumeric(2);
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAge(age);
        log.info("user = " + user);
        userRepository.save(user);
    }

    public void findAll() {
        final List<User> users = userRepository.findAll();
        for (User user : users) {
            log.info("loaded user = " + user);
        }
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void saveRandomUser(int count) {
        for(int i=0;i<count;i++) {
            final String firstname = RandomStringUtils.randomAlphabetic(5);
            final String lastname = RandomStringUtils.randomAlphabetic(5);
            final String age = RandomStringUtils.randomNumeric(2);
            User user = new User();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setAge(age);
            log.info("user = " + user);
            userRepository.save(user);
        }
    }
}
