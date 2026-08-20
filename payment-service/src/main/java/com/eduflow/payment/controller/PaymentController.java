package com.eduflow.payment.controller;

import com.eduflow.payment.model.Payment;
import com.eduflow.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment processing endpoints")
public class PaymentController {
    private final PaymentService service;

    @PostMapping("/process")
    @Operation(summary = "Process a payment")
    public ResponseEntity<Payment> process(@RequestBody Payment payment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.process(payment));
    }

    @GetMapping
    @Operation(summary = "Get all payments")
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/history/{studentId}")
    @Operation(summary = "Get payment history for student")
    public ResponseEntity<List<Payment>> history(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getHistory(studentId));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get payments by student")
    public ResponseEntity<List<Payment>> byStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getByStudent(studentId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a payment record")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(Map.of("message", "Payment deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "payment-service"));
    }
}
