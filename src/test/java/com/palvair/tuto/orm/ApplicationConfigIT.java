package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.repository.UserRepository;
import com.palvair.tuto.orm.service.DefaultUserServiceDelegate;
import com.palvair.tuto.orm.service.UserCriteriaService;
import com.palvair.tuto.orm.service.UserService;
import jdk.nashorn.internal.ir.annotations.Ignore;
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

/**
 * Created by widdy on 09/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
@Log4j
public class ApplicationConfigIT {

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

    @Autowired
    private UserService<User> userService;

    @Autowired(required = true)
    private UserRepository<User> userRepository;

    @Before
    public void init() {
        if (isInitialized) return;
        userService.saveRandomUser(10);
        isInitialized = true;
    }

    @After
    public void clean() {
        if (!isInitialized) return;
        userService.delete(userService.findAll());
        isInitialized = false;
    }

    @Test
    public void shouldFindAllWithCriteria() {
        final List<User> results = userCriteriaService.findAll();
        assertNotNull(results);
    }

}
