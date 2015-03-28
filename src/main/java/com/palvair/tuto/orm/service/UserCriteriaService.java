package com.palvair.tuto.orm.service;

import com.palvair.tuto.orm.entity.Contact;
import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.entity.User_;
import com.palvair.tuto.orm.repository.UserRepository;
import com.palvair.tuto.orm.specification.UserSpecification;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import java.util.List;

/**
 * Created by rpalvair on 14/10/2014.
 */
@Service
@Log4j
public class UserCriteriaService<T extends User> implements JpaCriteriaService<T> {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserRepository<User> userRepository;

    @Autowired
    private UserServiceDelegate delegate;


    public List<User> findAll() {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        final Root<User> userRoot = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(userRoot);
        final TypedQuery<User> userTypedQuery = em.createQuery(userCriteriaQuery);
        final List<User> results = userTypedQuery.getResultList();
        return results;
    }

    public void saveRandomUser(int count) {
        delegate.saveRandomUser(count);
    }

    public void delete(Iterable<? extends User> entities) {
        delegate.delete(entities);
    }

    public List<User> findByMaxAge(final String maxAge) {
        List<User> results = userRepository.findAll(UserSpecification.byMaxAge(maxAge));
        return results;
    }

    public List<User> findByfirstNameContainsCharacter(final char character) {
        List<User> results = userRepository.findAll(UserSpecification.firstNameContainsCharacter(character));
        return results;
    }

    public List<User> findByMaxAgeWithHibernateSession(final String age) {
        Session session = (Session) em.getDelegate();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.lt("age", age));
        @SuppressWarnings("unchecked")
        List<User> userList = criteria.list();
        return userList;
    }

    public long countWithProjection() {
        Session session = (Session) em.getDelegate();
        Criteria criteria = session.createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        @SuppressWarnings("unchecked")
        Long count = (Long) criteria.uniqueResult();
        return count;
    }

    public List<User> findAllWithFirstName() {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        final Root<User> from = criteriaQuery.from(User.class);
        criteriaQuery.multiselect(from.get("firstname"));
        final TypedQuery<User> userTypedQuery = em.createQuery(criteriaQuery);
        final List<User> results = userTypedQuery.getResultList();
        return results;
    }

    public List<User> findAllWithFirstNameAndLastName() {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        final Root<User> from = criteriaQuery.from(User.class);
        criteriaQuery.multiselect(from.get("firstname"), from.get("lastname"));
        final TypedQuery<User> userTypedQuery = em.createQuery(criteriaQuery);
        final List<User> results = userTypedQuery.getResultList();
        return results;
    }

    public List<User> findAllByNameIn(String... names) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        final Root<User> from = criteriaQuery.from(User.class);
        criteriaQuery.where(from.get("firstname").in(names));
        final TypedQuery<User> userTypedQuery = em.createQuery(criteriaQuery);
        final List<User> results = userTypedQuery.getResultList();
        return results;
    }

    public List<Long> findAllIds() {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<User> from = criteriaQuery.from(User.class);
        criteriaQuery.select(from.get(User_.ID));
        //ParameterExpression<Integer> p = criteriaBuilder.parameter(Integer.class);
        //criteriaQuery.where(criteriaBuilder.gt(p, 5));
        //criteriaQuery.where(criteriaBuilder.gt(from.get(User_.ID),5));
        final TypedQuery<Long> userTypedQuery = em.createQuery(criteriaQuery);
        final List<Long> results = userTypedQuery.getResultList();
        return results;
    }

    public List<Long> findAllIdsWithMaxAge(final String max) {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<User> from = criteriaQuery.from(User.class);
        criteriaQuery.select(from.get(User_.ID));
        ParameterExpression<String> p = criteriaBuilder.parameter(String.class, "age");
        criteriaQuery.where(criteriaBuilder.equal(from.get("age"), p));
        //criteriaQuery.where(criteriaBuilder.gt(from.get(User_.ID),5));
        final TypedQuery<Long> userTypedQuery = em.createQuery(criteriaQuery);
        userTypedQuery.setParameter("age", max);
        final List<Long> results = userTypedQuery.getResultList();
        return results;
    }

    public List<Long> findAllIdsWithHibernateSession() {
        Session session = (Session) em.getDelegate();
        Criteria criteria = session.createCriteria(User.class);
        criteria.setProjection(Projections.property("ID"));
        return (List<Long>) criteria.list();
    }

    public List<User> findByMaxAgeWithHibernateSessionAndDetachedCriteria(final String age) {
        Session session = (Session) em.getDelegate();
        Criteria criteria = session.createCriteria(User.class);
        //detached criteria for fetching contacts
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Contact.class);
        detachedCriteria.setProjection(Projections.rowCount());
        detachedCriteria.add(Restrictions.eq("name", "your friend"));
        criteria.add(Subqueries.exists(detachedCriteria));
        criteria.add(Restrictions.lt("age", age));
        @SuppressWarnings("unchecked")
        List<User> userList = criteria.list();
        return userList;
    }
}
