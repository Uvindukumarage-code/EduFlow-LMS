package com.eduflow.course.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private String title;

    @Column(length = 10000)
    private String content;  // Text content of the lesson

    private Integer orderIndex = 0;  // For ordering lessons

    private Integer durationMinutes = 10;
}
