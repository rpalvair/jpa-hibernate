package com.palvair.tuto.orm.repository;

import com.palvair.tuto.orm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleRepository extends JpaRepository<User,Long>{
}
