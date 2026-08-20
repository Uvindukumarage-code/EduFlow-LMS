package com.eduflow.notification.service;

import com.eduflow.notification.model.Notification;
import com.eduflow.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;

    public Notification sendEmail(Notification notification) {
        notification.setType(Notification.Type.EMAIL);
        notification.setStatus(Notification.Status.SENT);
        System.out.println("EMAIL sent to " + notification.getRecipient() + ": " + notification.getSubject());
        return repository.save(notification);
    }

    public Notification sendSms(Notification notification) {
        notification.setType(Notification.Type.SMS);
        notification.setStatus(Notification.Status.SENT);
        System.out.println("SMS sent to " + notification.getRecipient() + ": " + notification.getMessage());
        return repository.save(notification);
    }

    public List<Notification> getAll() {
        return repository.findAll();
    }

    public List<Notification> getByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Notification> getHistory() {
        return repository.findAll();
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Notification not found");
        }
        repository.deleteById(id);
    }
}
