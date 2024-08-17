package com.mod1.service.feed.repository;

import com.mod1.service.feed.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
