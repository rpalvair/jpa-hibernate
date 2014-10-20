package com.palvair.tuto.orm.service;

import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.repository.UserRepository;
import com.palvair.tuto.orm.specification.UserSpecification;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
        final Root<User> users = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(users);
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

}
