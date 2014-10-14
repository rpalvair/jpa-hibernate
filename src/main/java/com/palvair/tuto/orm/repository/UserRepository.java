package com.palvair.tuto.orm.repository;

import com.palvair.tuto.orm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository<T extends User> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
