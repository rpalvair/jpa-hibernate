package com.palvair.tuto.orm.service;

import com.palvair.tuto.orm.ApplicationConfig;
import com.palvair.tuto.orm.entity.Contact;
import com.palvair.tuto.orm.entity.User;
import lombok.extern.log4j.Log4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    private static boolean isInitialized = false;

    @Before
    public void init() {
        if (isInitialized) return;
        final User user = new User();
        user.setFirstname("Billy");
        user.setLastname("Crawford");
        user.setAge("30");
        final Contact contact = new Contact();
        contact.setName("your friend");
        user.setContact(new ArrayList<Contact>() {{
            add(contact);
        }});
        userService.saveUser(user);
        isInitialized = true;
    }

    @After
    public void clean() {
        if (!isInitialized) return;
        userService.delete(userService.findAll());
        isInitialized = false;
    }


    @Test
    public void testOnLazyRelations() {
        final User user = userService.findAll().get(0);
        Assert.assertNotNull(user);
        final List<Contact> contactList = user.getContact();
        Assert.assertNotNull(contactList);
        final Contact contact = contactList.get(0);
        Assert.assertNotNull(contact);
        final String name = contact.getName();
        log.info("contact Name = " + name);
    }
}
