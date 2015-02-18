package com.palvair.tuto.orm.service;

import com.palvair.tuto.orm.ApplicationConfig;
import com.palvair.tuto.orm.entity.User;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rpalvair
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
@Log4j
public class UserServiceIT {

    @Configuration
    @Import(ApplicationConfig.class)
    static class ContextConfiguration {

    }

    @Autowired(required = true)
    private UserService<User> userService;


    @Test
    public void test() {
        log.info("I do nothing for the moment");
    }
}
