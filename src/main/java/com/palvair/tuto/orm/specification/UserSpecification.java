package com.palvair.tuto.orm.specification;

import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.entity.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by rpalvair on 14/10/2014.
 */
public class UserSpecification {

    public static Specification<User> byMaxAge(final String maxAge) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.lessThan(root.get(User_.age), maxAge);
            }
        };
    }
}
