package com.eduflow.enrollment.controller;

import com.eduflow.enrollment.model.Enrollment;
import com.eduflow.enrollment.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollments", description = "Student course enrollment endpoints")
public class EnrollmentController {
    private final EnrollmentService service;

    @PostMapping
    @Operation(summary = "Enroll student in a course")
    public ResponseEntity<?> enroll(@RequestBody Enrollment enrollment) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.enroll(enrollment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all enrollments")
    public ResponseEntity<List<Enrollment>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get enrollment by ID")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get enrollments by student")
    public ResponseEntity<List<Enrollment>> byStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getByStudent(studentId));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get enrollments by course")
    public ResponseEntity<List<Enrollment>> byCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(service.getByCourse(courseId));
    }

    @PatchMapping("/{id}/progress")
    @Operation(summary = "Update progress")
    public ResponseEntity<?> updateProgress(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        try {
            return ResponseEntity.ok(service.updateProgress(id, body.get("progress")));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel enrollment")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        try {
            service.cancel(id);
            return ResponseEntity.ok(Map.of("message", "Enrollment cancelled"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "enrollment-service"));
    }
}
