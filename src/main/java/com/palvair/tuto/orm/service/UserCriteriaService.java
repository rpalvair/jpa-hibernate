package com.palvair.tuto.orm.service;

import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.repository.UserRepository;
import lombok.extern.log4j.Log4j;
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
public class UserCriteriaService implements JpaCriteriaService {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserRepository userRepository;

    public List<User> findAll() {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        final Root<User> users = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(users);
        final TypedQuery<User> userTypedQuery = em.createQuery(userCriteriaQuery);
        final List<User> results = userTypedQuery.getResultList();
        for (User user : results) {
            log.info("user(criteria) = " + user);
        }
        return results;
    }
}
