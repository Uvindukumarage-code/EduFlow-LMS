package com.eduflow.notification.controller;

import com.eduflow.notification.model.Notification;
import com.eduflow.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Email and SMS notification endpoints")
public class NotificationController {
    private final NotificationService service;

    @PostMapping("/email")
    @Operation(summary = "Send email notification")
    public ResponseEntity<Notification> sendEmail(@RequestBody Notification notification) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.sendEmail(notification));
    }

    @PostMapping("/sms")
    @Operation(summary = "Send SMS notification")
    public ResponseEntity<Notification> sendSms(@RequestBody Notification notification) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.sendSms(notification));
    }

    @GetMapping("/history")
    @Operation(summary = "Get notification history")
    public ResponseEntity<List<Notification>> history() {
        return ResponseEntity.ok(service.getHistory());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get notifications for user")
    public ResponseEntity<List<Notification>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }

    @GetMapping
    @Operation(summary = "Get all notifications")
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(Map.of("message", "Notification deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "notification-service"));
    }
}
