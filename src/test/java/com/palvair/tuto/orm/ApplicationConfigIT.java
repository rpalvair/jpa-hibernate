package com.palvair.tuto.orm;

import com.palvair.tuto.orm.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by widdy on 09/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ApplicationConfigIT {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void shouldSaveTenUsers() {
        final UserService userService = applicationContext.getBean(UserService.class);
        userService.deleteAll();
        userService.saveRandomUser(10);
        userService.findAll();
    }
}