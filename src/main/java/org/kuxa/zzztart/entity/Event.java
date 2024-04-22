package org.kuxa.zzztart.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

}
