package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.repository.UserRepository;
import com.palvair.tuto.orm.service.DefaultUserServiceDelegate;
import com.palvair.tuto.orm.service.UserCriteriaService;
import com.palvair.tuto.orm.service.UserService;
import lombok.extern.log4j.Log4j;
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
import static org.junit.Assert.assertTrue;

/**
 * Created by widdy on 09/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
@Log4j
public class ApplicationConfigIT {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DefaultUserServiceDelegate delegate;

    @Autowired
    private UserCriteriaService<User> userCriteriaService;

    @Autowired
    private UserService<User> userService;

    @Autowired()
    private UserRepository<User> userRepository;


    @Test
    public void shouldFindAllWithCriteria() {
        final List<User> results = userCriteriaService.findAll();
        log.debug("size = " + results.size());
        assertNotNull(results);
        assertTrue(results.size() == 1);
    }

}
