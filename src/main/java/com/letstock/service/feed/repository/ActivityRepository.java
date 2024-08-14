package com.letstock.service.feed.repository;

import com.letstock.service.feed.domain.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
}
