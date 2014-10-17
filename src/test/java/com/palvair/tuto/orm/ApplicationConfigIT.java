package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.service.UserCriteriaService;
import com.palvair.tuto.orm.service.UserService;
import lombok.extern.log4j.Log4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by widdy on 09/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
@Log4j
public class ApplicationConfigIT {

    private static boolean isInitialized = false;
    private static boolean isCleaned = false;

    @Configuration
    @Import(ApplicationConfig.class)
    static class ContextConfiguration {


    }

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserCriteriaService<User> userCriteriaService;

    @Autowired
    private UserService<User> userService;

    @Before
    public void init() {
        if (isInitialized) return;
        log.info("INIT DB");
        userService.saveRandomUser(10);
        isInitialized = true;
    }

    @After
    public void clean() {
        if (isCleaned) return;
        log.info("CLEAN DB");
        userService.delete(userService.findAll());
        isCleaned = true;
    }

    @Test
    public void shouldFindAllWithCriteria() {
        final List<User> results = userCriteriaService.findAll();
        assertNotNull(results);
        assertTrue(results.size() > 0);
    }

    @Test
    public void shouldFindByNameWithCriteriaAndSpecification() {
        final List<User> results = userCriteriaService.findByMaxAge("45");
        log.info("results = " + results);
        //assertNotNull(results);
        //assertTrue(results.size() > 0);
    }

    @Test
    public void shouldFindByFirstNameContainsCharacter() {
        final List<User> results = userCriteriaService.findByfirstNameContainsCharacter('a');
        log.info("results = " + results);
    }

    @Test
    public void shouldFindByMaxAgeWithHibernateSession() {
        final List<User> results = userCriteriaService.findByMaxAgeWithHibernateSession("45");
        log.info("results = " + results);
    }
}
