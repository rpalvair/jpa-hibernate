package com.palvair.tuto.orm.service;

import com.palvair.tuto.orm.ApplicationConfig;
import com.palvair.tuto.orm.entity.User;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rpalvair on 20/10/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
@Log4j
public class UserCriteriaServiceIT {

    private static boolean isInitialized = false;

    @Configuration
    @Import(ApplicationConfig.class)
    static class ContextConfiguration {

    }

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DefaultUserServiceDelegate delegate;

    @Autowired
    private UserCriteriaService<User> userCriteriaService;

    @Before
    public void init() {
        if (isInitialized) return;
        userCriteriaService.saveRandomUser(10);
        isInitialized = true;
    }

    @After
    public void clean() {
        if (!isInitialized) return;
        userCriteriaService.delete(userCriteriaService.findAll());
        isInitialized = false;
    }

    @Test
    public void shouldFindAllWithCriteria() {
        final List<User> results = userCriteriaService.findAll();
        assertNotNull(results);
    }

    @Test
    public void shouldFindByNameWithCriteriaAndSpecification() {
        final List<User> results = userCriteriaService.findByMaxAge("45");
        assertNotNull(results);
    }

    @Test
    public void shouldFindByFirstNameContainsCharacter() {
        final List<User> results = userCriteriaService.findByfirstNameContainsCharacter('a');
        assertNotNull(results);
    }

    @Test
    public void shouldFindByMaxAgeWithHibernateSession() {
        final List<User> results = userCriteriaService.findByMaxAgeWithHibernateSession("45");
        assertNotNull(results);
    }

    @Test
    public void shouldFindAllWithProjection() {
        final long results = userCriteriaService.countWithProjection();
        assertNotNull(results);
        assertEquals(10L, results);
    }

    @Test
    public void shouldFindAllIds() {
        final List<Long> list = userCriteriaService.findAllIds();
        assertNotNull(list);
    }

    @Test
    public void shouldFindAllIdsWithHibernateSession() {
        final List<Long> list = userCriteriaService.findAllIdsWithHibernateSession();
        assertNotNull(list);
    }

    @Test
    public void shouldFindByMaxAgeWithHibernateSessionAndDetachedCriteria() {
        final List<User> list = userCriteriaService.findByMaxAgeWithHibernateSessionAndDetachedCriteria("45");
        assertNotNull(list);
    }

    @Test
    public void shouldFindAllIdsWithMaxAge() {
        final List<Long> list = userCriteriaService.findAllIdsWithMax(45L);
        assertNotNull(list);
    }
}
