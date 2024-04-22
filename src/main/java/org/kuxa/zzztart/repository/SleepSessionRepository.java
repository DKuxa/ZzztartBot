package org.kuxa.zzztart.repository;

import org.kuxa.zzztart.entity.SleepSession;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SleepSessionRepository extends CrudRepository<SleepSession, Long> {
    List<SleepSession> findByChatId(Long chatId);
    SleepSession findFirstByChatIdAndEndTimeIsNull(Long chatId);
    SleepSession findFirstByChatIdAndEndTimeIsNullOrderByStartTimeDesc(Long chatId);

}
