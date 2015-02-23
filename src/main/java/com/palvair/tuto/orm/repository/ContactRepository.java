package com.palvair.tuto.orm.repository;

import com.palvair.tuto.orm.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author rpalvair
 */
public interface ContactRepository<T extends Contact> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
