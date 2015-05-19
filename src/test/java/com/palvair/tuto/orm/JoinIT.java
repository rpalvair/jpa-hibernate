package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.Conference;
import com.palvair.tuto.orm.entity.Meeting;
import com.palvair.tuto.orm.entity.Meeting_;
import com.palvair.tuto.orm.entity.User;
import lombok.extern.log4j.Log4j;
import org.hibernate.criterion.CriteriaQuery;
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
import javax.persistence.criteria.Join;
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
public class JoinIT {

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

    @Before
    public void init() {
        if (!isinit) {
            final User user = new User();
            user.setFirstname("arthur");
            final Meeting meeting = new Meeting();
            meeting.setName("meeting");
            user.setMeeting(meeting);
            meeting.setUsers(Arrays.asList(user));

            final Conference conference = new Conference();
            conference.setMeetings(Arrays.asList(meeting));

            meeting.setConference(conference);

            em.persist(conference);
            //em.persist(meeting);

            em.flush();
            isinit = true;
        }

    }

    @Test
    public void outerJoinMeetingOnUser() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery criteria = criteriaBuilder.createQuery();

        Root<Meeting> meeting = criteria.from(Meeting.class);

        /** jointure externe **/
        Join<Meeting, User> join = meeting.join("users", JoinType.LEFT);
        join.on(criteriaBuilder.equal(join.get("firstname"),"billy"));

        criteria.multiselect(meeting, join);

        TypedQuery<Meeting> typedQuery = em.createQuery(criteria);
        final List<Meeting> meetings = typedQuery.getResultList();
        assertNotNull(meetings);
        assertTrue(meetings.size() > 0);
        log.info("method terminated = "+name.getMethodName());
    }

    @Test
    public void innerJoinMeetingOnUser() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery criteria = criteriaBuilder.createQuery();

        Root<Meeting> meeting = criteria.from(Meeting.class);

        /** jointure interne **/
        Join<Meeting, User> join = meeting.join("users", JoinType.INNER);
        join.on(criteriaBuilder.equal(join.get("firstname"),"billy"));

        criteria.multiselect(meeting, join);

        TypedQuery<Meeting> typedQuery = em.createQuery(criteria);
        final List<Meeting> meetings = typedQuery.getResultList();
        assertNotNull(meetings);
        assertTrue(meetings.size() == 0);
        log.info("method terminated = "+name.getMethodName());
    }

    @Test
    public void innerJoinWithWhereClauseMeetingOnUser() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery criteria = criteriaBuilder.createQuery();

        Root<User> user = criteria.from(User.class);


        /** where **/
        criteria.select(user).where(criteriaBuilder.equal(user.get("meeting").get("name"), "meeting"));

        TypedQuery<User> typedQuery = em.createQuery(criteria);
        final List<User> users = typedQuery.getResultList();
        assertNotNull(users);
        assertTrue(users.size() > 0);
        assertEquals(users.get(0).getFirstname(), "arthur");
        log.info("method terminated = "+name.getMethodName());
    }
}
