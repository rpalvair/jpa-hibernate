package com.palvair.tuto.orm;

import lombok.extern.log4j.Log4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rpalvair on 07/10/2014.
 */
@Configuration
@Log4j
public class ApplicationLauncher {

    @Bean
    OrmBean ormBean() {
        return new OrmBean();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationLauncher.class);
        log.info("loaded");
    }
}
