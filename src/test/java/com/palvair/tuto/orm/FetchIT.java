package com.palvair.tuto.orm;

import com.palvair.tuto.orm.entity.Meeting;
import lombok.extern.log4j.Log4j;
import org.hibernate.Hibernate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
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

import static org.junit.Assert.*;

/**
 * @author rpalvair
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
@Log4j
public class FetchIT {

    @PersistenceContext
    private EntityManager em;

    @Rule
    public TestName name = new TestName();

    @Test
    public void getMeetingAndFetchUsers() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Meeting> criteria = criteriaBuilder.createQuery(Meeting.class);
        Root<Meeting> meeting = criteria.from(Meeting.class);

        /** fetch **/
        meeting.fetch("users", JoinType.LEFT);

        criteria.select(meeting);

        TypedQuery<Meeting> typedQuery = em.createQuery(criteria);
        final Meeting loadedMeeting = typedQuery.getSingleResult();
        assertNotNull(loadedMeeting.getUsers());
        assertTrue(Hibernate.isInitialized(loadedMeeting.getUsers()));
        assertTrue(loadedMeeting.getUsers().size() > 0);
        assertEquals(loadedMeeting.getUsers().get(0).getFirstname(), "ruddy");
        log.info("method terminated = " + name.getMethodName());
    }

    @Test
    public void getMeetingWithoutFetchingUsers() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Meeting> criteria = criteriaBuilder.createQuery(Meeting.class);
        Root<Meeting> meeting = criteria.from(Meeting.class);

        criteria.select(meeting);

        TypedQuery<Meeting> typedQuery = em.createQuery(criteria);
        final Meeting loadedMeeting = typedQuery.getSingleResult();
        assertNotNull(loadedMeeting.getUsers());
        /** users are not initialized **/
        assertFalse(Hibernate.isInitialized(loadedMeeting.getUsers()));
        assertTrue(loadedMeeting.getUsers().size() > 0);
        /** once you call the getter the objects are initialized **/
        assertTrue(Hibernate.isInitialized(loadedMeeting.getUsers()));
        assertEquals(loadedMeeting.getUsers().get(0).getFirstname(), "ruddy");
        log.info("method terminated = " + name.getMethodName());
    }
}
