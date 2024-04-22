package org.kuxa.zzztart.repository;

import org.kuxa.zzztart.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findBySessionIdOrderByTimestampAsc(Long sessionId);
}
