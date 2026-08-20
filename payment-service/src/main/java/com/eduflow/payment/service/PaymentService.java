package com.eduflow.payment.service;

import com.eduflow.payment.model.Payment;
import com.eduflow.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;

    public Payment process(Payment payment) {
        payment.setStatus(Payment.Status.COMPLETED);
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return repository.save(payment);
    }

    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Payment getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<Payment> getByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    public List<Payment> getHistory(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Payment not found");
        }
        repository.deleteById(id);
    }
}
