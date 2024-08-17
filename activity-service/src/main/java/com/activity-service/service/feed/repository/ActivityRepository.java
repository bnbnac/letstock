package com.mod2.service.feed.repository;

import com.mod2.service.feed.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
