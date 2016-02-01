package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.Conference;
import com.palvair.tuto.orm.entity.Meeting;
import com.palvair.tuto.orm.entity.User;
import lombok.extern.log4j.Log4j;
import org.hibernate.Hibernate;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author rpalvair
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
@Log4j
public class InitializationIT {

    @PersistenceContext
    private EntityManager em;

    private Long meetingId = 1L;

    private Long userId = 1L;

    private Long conferenceId = 1L;

    @Test
    public void getUsersByMeeting() {
        assertTrue(em.isOpen());
        assertNotNull(meetingId);
        assertNotNull(userId);
        final Meeting meeting = em.getReference(Meeting.class, meetingId);
        /** entity is not initialized because we call getReference() **/
        assertFalse(Hibernate.isInitialized(meeting));
        /** but the object is not null however **/
        assertNotNull(meeting);
        List<User> users = meeting.getUsers();
        /** once you call a method on the object the object is initialized **/
        assertFalse(Hibernate.isInitialized(users));
        assertNotNull(users);
        for (User user : users) {
            final Meeting userMeeting = user.getMeeting();
            /** entity is initialized because we use a loop **/
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
        /** here the entity is initialized because we call find()
         Hibernate first search the entity in the persistence context before hitting the database **/
        assertTrue(Hibernate.isInitialized(conference));
        List<Meeting> meetings = conference.getMeetings();
        assertFalse(Hibernate.isInitialized(meetings));
        assertNotNull(meetings);
    }

    @Test
    public void proxyExample() {
        assertTrue(em.isOpen());
        final Meeting meeting = em.getReference(Meeting.class, meetingId);
        /** entity is not initialized **/
        assertFalse(Hibernate.isInitialized(meeting));
        /** entity is a proxy **/
        assertNotEquals(meeting.getClass(), Meeting.class);
        log.debug("proxy class = "+meeting.getClass());
        final Conference conference = meeting.getConference();
        /** entity is initialized because of the call to the accessor **/
        assertFalse(Hibernate.isInitialized(conference));
    }

}
