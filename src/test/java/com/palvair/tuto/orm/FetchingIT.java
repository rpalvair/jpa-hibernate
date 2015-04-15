package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.Meeting;
import com.palvair.tuto.orm.entity.User;
import lombok.extern.log4j.Log4j;
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

import static org.junit.Assert.assertNotNull;

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

    @Before
    public void init() {
        final User user = new User();
        final Meeting meeting = new Meeting();
        meeting.setName("meeting");
        user.setMeeting(meeting);
        meeting.setUsers(Arrays.asList(user));
        em.persist(meeting);
        meetingId = meeting.getID();
        userId = user.getID();
    }

    @Test
    public void getUsersByMeeting() {
        assertNotNull(meetingId);
        assertNotNull(userId);
        final Meeting meeting = em.find(Meeting.class, meetingId);
        assertNotNull(meeting);
        List<User> users = meeting.getUsers();
        assertNotNull(users);
        for (User user : users) {
            final Meeting userMeeting = user.getMeeting();
            assertNotNull(userMeeting);
            assertNotNull(userMeeting.getName());
        }
    }

}
