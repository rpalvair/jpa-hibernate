package com.palvair.tuto.orm.service;


import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Log4j
public class UserService implements JpaService {

    @PersistenceContext
    private EntityManager em;

    public void log() {
        log.info("entityManager = " + em.toString());
    }
}
