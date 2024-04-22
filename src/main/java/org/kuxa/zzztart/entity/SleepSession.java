package org.kuxa.zzztart.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SleepSessions")
public class SleepSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "phone_usage_duration")
    private Integer phoneUsageDuration = 0;
}
