package com.letstock.service.feed.repository;

import com.letstock.service.feed.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
