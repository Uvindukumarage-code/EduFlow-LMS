package com.eduflow.enrollment.service;

import com.eduflow.enrollment.model.Enrollment;
import com.eduflow.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository repository;

    public Enrollment enroll(Enrollment enrollment) {
        if (repository.existsByStudentIdAndCourseId(enrollment.getStudentId(), enrollment.getCourseId())) {
            throw new RuntimeException("Student already enrolled in this course");
        }
        enrollment.setStatus(Enrollment.Status.ACTIVE);
        return repository.save(enrollment);
    }

    public List<Enrollment> getAll() {
        return repository.findAll();
    }

    public Enrollment getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    public List<Enrollment> getByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    public List<Enrollment> getByCourse(Long courseId) {
        return repository.findByCourseId(courseId);
    }

    public Enrollment updateProgress(Long id, Double progress) {
        Enrollment e = getById(id);
        e.setProgress(progress);
        if (progress >= 100.0) e.setStatus(Enrollment.Status.COMPLETED);
        return repository.save(e);
    }

    public void cancel(Long id) {
        Enrollment e = getById(id);
        e.setStatus(Enrollment.Status.CANCELLED);
        repository.save(e);
    }
}
