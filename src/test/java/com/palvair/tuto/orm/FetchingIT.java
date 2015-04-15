package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.Conference;
import com.palvair.tuto.orm.entity.Meeting;
import com.palvair.tuto.orm.entity.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.log4j.Log4j;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author rpalvair
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
@Log4j
public class FetchingIT {

    @Configuration
    @Import(ApplicationConfig.class)
    static class ContextConfiguration {

    }

    @Autowired
    private ApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager em;

    private Long meetingId;

    private Long userId;

    private Long conferenceId;

    boolean isinit = false;

    @Before
    public void init() {
        if(!isinit) {
            final User user = new User();
            final Meeting meeting = new Meeting();
            meeting.setName("meeting");
            user.setMeeting(meeting);
            meeting.setUsers(Arrays.asList(user));

            final Conference conference = new Conference();
            conference.setMeetings(Arrays.asList(meeting));

            meeting.setConference(conference);

            em.persist(conference);
            //em.persist(meeting);

            meetingId = meeting.getID();
            userId = user.getID();
            conferenceId = conference.getID();
            em.flush();
            //to have proxies!!
            em.clear();
            isinit = true;
        }

    }

    @Test
    public void getUsersByMeeting() {
        assertTrue(em.isOpen());
        assertNotNull(meetingId);
        assertNotNull(userId);
        final Meeting meeting = em.getReference(Meeting.class, meetingId);
        //not initialized
        assertFalse(Hibernate.isInitialized(meeting));
        assertNotNull(meeting);
        List<User> users = meeting.getUsers();
        assertFalse(Hibernate.isInitialized(users));
        assertNotNull(users);
        for (User user : users) {
            final Meeting userMeeting = user.getMeeting();
            //initialized by Hibernate
            assertTrue(Hibernate.isInitialized(userMeeting));
            assertNotNull(userMeeting);
            assertNotNull(userMeeting.getName());
        }
    }

    @Test
    public void getMeetingsByConference() {
        assertTrue(em.isOpen());
        assertNotNull(conferenceId);
        final Conference conference = em.find(Conference.class, conferenceId);
        //initialized thanks to the find
        assertTrue(Hibernate.isInitialized(conference));
        List<Meeting> meetings = conference.getMeetings();
        assertFalse(Hibernate.isInitialized(meetings));
        assertNotNull(meetings);
    }

    @Test
    public void proxyExample() {
        assertTrue(em.isOpen());
        final Meeting meeting = em.getReference(Meeting.class, meetingId);
        //not initialized
        assertFalse(Hibernate.isInitialized(meeting));
        //it's a proxy!!
        assertNotEquals(meeting.getClass(), Meeting.class);
        final Conference conference = meeting.getConference();
        assertFalse(Hibernate.isInitialized(conference));
    }

}
