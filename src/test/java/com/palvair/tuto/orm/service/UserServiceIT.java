package com.palvair.tuto.orm.service;

import com.palvair.tuto.orm.ApplicationConfig;
import com.palvair.tuto.orm.entity.Contact;
import com.palvair.tuto.orm.entity.Meeting;
import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.repository.ContactRepository;
import com.palvair.tuto.orm.repository.MeetingRepository;
import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author rpalvair
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
@Log4j
@Ignore
public class UserServiceIT {

    @Configuration
    @Import(ApplicationConfig.class)
    static class ContextConfiguration {

    }

    @Autowired(required = true)
    private UserService<User> userService;

    @Autowired(required = true)
    private MeetingRepository meetingRepository;

    @Autowired(required = true)
    private ContactRepository contactRepository;

    @PersistenceContext
    private EntityManager em;

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
        final Meeting meeting = new Meeting();
        meeting.setName("test");
        //avoid transient exception
        meetingRepository.save(meeting);
        user.setMeeting(meeting);
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
    public void testOnLazyRelationsByDetaching() {
        final User user = userService.findAll().get(0);
        assertNotNull(user);
        //detach object
        em.detach(user);
        final String oldFirstName = user.getFirstname();
        user.setFirstname("Johnny");
        //lazy should occurred here
        final List<Contact> contacts = user.getContact();
        final Contact contact = contacts.get(0);
        Assert.assertEquals(oldFirstName, userService.getUserRepository().findOne(user.getID()).getFirstname());
    }
}
