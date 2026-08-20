package com.eduflow.notification.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String recipient; // email or phone
    private String subject;
    private String message;

    @Enumerated(EnumType.STRING)
    private Type type; // EMAIL, SMS

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime sentAt;

    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }

    public enum Type { EMAIL, SMS }
    public enum Status { PENDING, SENT, FAILED }
}
