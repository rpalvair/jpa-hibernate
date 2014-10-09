package com.palvair.tuto.orm;

import com.palvair.tuto.orm.constants.PropertiesConstants;
import com.palvair.tuto.orm.service.UserService;
import lombok.extern.log4j.Log4j;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.annotation.Resource;

/**
 * Created by rpalvair on 07/10/2014.
 */
@Configuration
@Log4j
@PropertySource("classpath:access.properties")
@ComponentScan(basePackages = {"com.palvair.tuto.orm"})
public class ApplicationLauncher {

    @Resource
    private Environment environment;

    @Bean
    public javax.sql.DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        log.info("username = " + environment.getProperty(PropertiesConstants.DB_USERNAME.getValue()));
        dataSource.setUsername(environment.getProperty(PropertiesConstants.DB_USERNAME.getValue()));
        dataSource.setPassword(environment.getProperty(PropertiesConstants.DB_PASSWORD.getValue()));
        dataSource.setUrl("jdbc:mysql://localhost:3306/tutoorm");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.palvair.tuto.orm.entity");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws ClassNotFoundException {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    public static void main(String[] args) {
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationLauncher.class);
        log.info("loaded");
        UserService userService = applicationContext.getBean(UserService.class);
        userService.log();
    }
}
