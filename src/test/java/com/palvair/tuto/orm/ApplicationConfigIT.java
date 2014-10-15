package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.service.UserCriteriaService;
import com.palvair.tuto.orm.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by widdy on 09/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class ApplicationConfigIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserCriteriaService<User> userCriteriaService;

    @Autowired
    private UserService<User> userService;

    @Test
    public void shouldSaveTenUsers() {
        userService.deleteAll();
        userService.saveRandomUser(10);
        userService.findAll();
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
        assertNotNull(results);
        assertTrue(results.size() > 0);
    }

    @Test
    public void shouldFindByFirstNameContainsCharacter() {
        final List<User> results = userCriteriaService.findByfirstNameContainsCharacter('a');
        assertNotNull(results);
        assertTrue(results.size() > 0);
    }

    @Test
    public void shouldFindByfirstNameContainsCharacterWithHibernateSession() {
        final List<User> results = userCriteriaService.findByfirstNameContainsCharacterWithHibernateSession('a');
        assertNotNull(results);
        assertTrue(results.size() > 0);
    }

}
