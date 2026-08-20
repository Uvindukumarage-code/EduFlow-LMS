package com.eduflow.payment.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long courseId;
    private String courseTitle;
    private BigDecimal amount;
    private String currency = "LKR";

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private String transactionId;
    private String paymentMethod;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (transactionId == null) {
            transactionId = "TXN-" + System.currentTimeMillis();
        }
    }

    public enum Status {
        PENDING, COMPLETED, FAILED, REFUNDED
    }
}
