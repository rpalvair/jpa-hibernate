package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.Conference;
import com.palvair.tuto.orm.entity.Meeting;
import com.palvair.tuto.orm.entity.User;
import lombok.extern.log4j.Log4j;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
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
public class FetchIT {

    @Configuration
    @Import(ApplicationConfig.class)
    static class ContextConfiguration {

    }

    @Autowired
    private ApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager em;

    boolean isinit = false;

    @Rule
    public TestName name= new TestName();

    private Conference conference;

    @Before
    public void init() {
        if (!isinit) {
            final User user = new User();
            user.setFirstname("arthur");
            final Meeting meeting = new Meeting();
            meeting.setName("meeting");
            user.setMeeting(meeting);
            meeting.setUsers(Arrays.asList(user));

            conference = new Conference();
            conference.setMeetings(Arrays.asList(meeting));

            meeting.setConference(conference);

            em.persist(conference);
            //em.persist(meeting);

            em.flush();
            isinit = true;
        }
    }

    @After
    public void clean() {
        em.remove(conference);
    }

    @Test
    public void getMeetingAndFetchUsers() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery criteria = criteriaBuilder.createQuery();

        Root<Meeting> meeting = criteria.from(Meeting.class);

        /** fetch **/
        meeting.fetch("users", JoinType.LEFT);

        criteria.select(meeting);

        TypedQuery<Meeting> typedQuery = em.createQuery(criteria);
        final Meeting loadedMeeting = typedQuery.getSingleResult();
        assertNotNull(loadedMeeting.getUsers());
        assertTrue(Hibernate.isInitialized(loadedMeeting.getUsers()));
        assertTrue(loadedMeeting.getUsers().size() > 0);
        assertEquals(loadedMeeting.getUsers().get(0).getFirstname(), "arthur");
        log.info("method terminated = "+name.getMethodName());
    }

}
