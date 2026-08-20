package com.eduflow.course.service;

import com.eduflow.course.model.Course;
import com.eduflow.course.model.Lesson;
import com.eduflow.course.repository.CourseRepository;
import com.eduflow.course.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    // ========== COURSE ==========
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    public Course update(Long id, Course updated) {
        Course course = getById(id);
        course.setTitle(updated.getTitle());
        course.setDescription(updated.getDescription());
        course.setPrice(updated.getPrice());
        course.setCategory(updated.getCategory());
        course.setDurationHours(updated.getDurationHours());
        course.setPublished(updated.isPublished());
        if (updated.getInstructorEmail() != null) {
            course.setInstructorEmail(updated.getInstructorEmail());
        }
        if (updated.getInstructorName() != null) {
            course.setInstructorName(updated.getInstructorName());
        }
        return courseRepository.save(course);
    }

    @Transactional
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found");
        }
        lessonRepository.deleteByCourseId(id);
        courseRepository.deleteById(id);
    }

    public List<Course> search(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Course> getByCategory(String category) {
        return courseRepository.findByCategory(category);
    }

    // ========== LESSONS ==========
    public Lesson addLesson(Lesson lesson) {
        if (lesson.getOrderIndex() == null) {
            List<Lesson> existing = lessonRepository.findByCourseIdOrderByOrderIndexAsc(lesson.getCourseId());
            lesson.setOrderIndex(existing.size() + 1);
        }
        return lessonRepository.save(lesson);
    }

    public List<Lesson> getLessonsByCourse(Long courseId) {
        return lessonRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
    }

    public Lesson getLesson(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    public Lesson updateLesson(Long id, Lesson updated) {
        Lesson lesson = getLesson(id);
        lesson.setTitle(updated.getTitle());
        lesson.setContent(updated.getContent());
        if (updated.getOrderIndex() != null) lesson.setOrderIndex(updated.getOrderIndex());
        if (updated.getDurationMinutes() != null) lesson.setDurationMinutes(updated.getDurationMinutes());
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new RuntimeException("Lesson not found");
        }
        lessonRepository.deleteById(id);
    }
}
