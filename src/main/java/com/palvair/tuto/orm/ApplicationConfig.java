package com.palvair.tuto.orm;

import com.palvair.tuto.orm.constants.PropertiesConstants;
import com.palvair.tuto.orm.service.UserService;
import lombok.extern.log4j.Log4j;
import org.hibernate.ejb.HibernatePersistence;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
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
@ImportResource("classpath*:persistence-context.xml")
public class ApplicationConfig {

    @Resource
    private Environment environment;

    @Bean
    public javax.sql.DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.palvair.tuto.orm.entity");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws ClassNotFoundException {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    public static void main(String[] args) {
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.log();
        userService.saveRandomUser();
        userService.findAll();
    }
}
