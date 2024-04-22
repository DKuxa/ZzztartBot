package org.kuxa.zzztart.service;

import org.kuxa.zzztart.entity.Event;
import org.kuxa.zzztart.entity.SleepSession;
import org.kuxa.zzztart.repository.EventRepository;
import org.kuxa.zzztart.repository.SleepSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SleepSessionService {
    private final SleepSessionRepository sleepSessionRepository;
    private final EventRepository eventRepository;

    @Autowired
    public SleepSessionService(SleepSessionRepository sleepSessionRepository, EventRepository eventRepository) {
        this.sleepSessionRepository = sleepSessionRepository;
        this.eventRepository = eventRepository;
    }

    public void startSleepSession(long chatId) {
        SleepSession newSession = new SleepSession();
        newSession.setChatId(chatId);
        newSession.setStartTime(LocalDateTime.now());
        sleepSessionRepository.save(newSession);
    }

    public int calculatePhoneUsageDurationForSession(Long sessionId) {
        List<Event> events = eventRepository.findBySessionIdOrderByTimestampAsc(sessionId);
        int totalDuration = 0;
        LocalDateTime phonePickedUpTime = null;

        for (Event event : events) {
            if ("phone_before_sleep".equals(event.getEventType())) {
                phonePickedUpTime = event.getTimestamp();
            } else if (phonePickedUpTime != null && "phone_during_sleep".equals(event.getEventType())) {
                Duration duration = Duration.between(phonePickedUpTime, event.getTimestamp());
                totalDuration += (int) duration.toMinutes();
                phonePickedUpTime = null; // reset for the next usage interval
            }
        }

        return totalDuration;
    }

    public void endSleepSession(long chatId) {
        SleepSession currentSession = sleepSessionRepository.findFirstByChatIdAndEndTimeIsNull(chatId);
        if (currentSession != null) {
            int phoneUsageDurationMinutes = calculatePhoneUsageDurationForSession(currentSession.getSessionId());
            currentSession.setPhoneUsageDuration(phoneUsageDurationMinutes);
            currentSession.setEndTime(LocalDateTime.now());
            sleepSessionRepository.save(currentSession);
        }
    }

    public List<SleepSession> getSleepSessions(long chatId) {
        return sleepSessionRepository.findByChatId(chatId);
    }

    public double calculateAverageSleepHours(long chatId) {
        List<SleepSession> sessions = getSleepSessions(chatId);
        if (sessions.isEmpty()) return 0;

        double totalSleepHours = 0;
        for (SleepSession session : sessions) {
            Duration duration = Duration.between(session.getStartTime(), session.getEndTime());
            long adjustedDurationInMinutes = duration.toMinutes() - session.getPhoneUsageDuration();
            double adjustedDurationInHours = adjustedDurationInMinutes / 60.0;
            totalSleepHours += adjustedDurationInHours;
        }
        return totalSleepHours / sessions.size();
    }
}