package org.kuxa.zzztart.service;

import org.kuxa.zzztart.entity.Event;
import org.kuxa.zzztart.entity.SleepSession;
import org.kuxa.zzztart.repository.EventRepository;
import org.kuxa.zzztart.repository.SleepSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SleepSessionRepository sleepSessionRepository;

    @Autowired
    public EventService(EventRepository eventRepository, SleepSessionRepository sleepSessionRepository) {
        this.eventRepository = eventRepository;
        this.sleepSessionRepository = sleepSessionRepository;
    }

    public void createEventForCurrentSession(long chatId, String eventType) {
        SleepSession currentSession = sleepSessionRepository.findFirstByChatIdAndEndTimeIsNullOrderByStartTimeDesc(chatId);
        if (currentSession != null) {
            createEvent(currentSession.getSessionId(), eventType);
        } else {
            // Handle the case where there is no current session.
            // Could involve logging an error or starting a new session if business logic allows.
            // Throw an exception or return null depending on how you want to handle this case.
            throw new IllegalStateException("No active session found for chatId: " + chatId);
        }
    }

    private void createEvent(long sessionId, String eventType) {
        Event newEvent = new Event();
        newEvent.setSessionId(sessionId);
        newEvent.setEventType(eventType);
        newEvent.setTimestamp(LocalDateTime.now());
        eventRepository.save(newEvent);
    }
}