package com.palvair.tuto.orm.repository;

import com.palvair.tuto.orm.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author rpalvair
 */
public interface MeetingRepository<T extends Meeting> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
