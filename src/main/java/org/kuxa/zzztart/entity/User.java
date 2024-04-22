package org.kuxa.zzztart.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    private String username;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

}
