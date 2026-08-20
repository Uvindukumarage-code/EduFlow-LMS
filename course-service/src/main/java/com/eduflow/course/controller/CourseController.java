package com.eduflow.course.controller;

import com.eduflow.course.model.Course;
import com.eduflow.course.model.Lesson;
import com.eduflow.course.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "Course and Lesson management endpoints")
public class CourseController {

    private final CourseService courseService;

    // ========== COURSE CRUD ==========
    @PostMapping
    @Operation(summary = "Create a new course")
    public ResponseEntity<Course> create(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(course));
    }

    @GetMapping
    @Operation(summary = "Get all courses")
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(courseService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Course course) {
        try {
            return ResponseEntity.ok(courseService.update(id, course));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            courseService.delete(id);
            return ResponseEntity.ok(Map.of("message", "Course deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search courses by title")
    public ResponseEntity<List<Course>> search(@RequestParam String q) {
        return ResponseEntity.ok(courseService.search(q));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get courses by category")
    public ResponseEntity<List<Course>> byCategory(@PathVariable String category) {
        return ResponseEntity.ok(courseService.getByCategory(category));
    }

    // ========== LESSONS ==========
    @PostMapping("/{courseId}/lessons")
    @Operation(summary = "Add a lesson to a course")
    public ResponseEntity<Lesson> addLesson(@PathVariable Long courseId, @RequestBody Lesson lesson) {
        lesson.setCourseId(courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addLesson(lesson));
    }

    @GetMapping("/{courseId}/lessons")
    @Operation(summary = "Get all lessons of a course")
    public ResponseEntity<List<Lesson>> getLessons(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getLessonsByCourse(courseId));
    }

    @GetMapping("/lessons/{lessonId}")
    @Operation(summary = "Get a single lesson")
    public ResponseEntity<?> getLesson(@PathVariable Long lessonId) {
        try {
            return ResponseEntity.ok(courseService.getLesson(lessonId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/lessons/{lessonId}")
    @Operation(summary = "Update a lesson")
    public ResponseEntity<?> updateLesson(@PathVariable Long lessonId, @RequestBody Lesson lesson) {
        try {
            return ResponseEntity.ok(courseService.updateLesson(lessonId, lesson));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/lessons/{lessonId}")
    @Operation(summary = "Delete a lesson")
    public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {
        try {
            courseService.deleteLesson(lessonId);
            return ResponseEntity.ok(Map.of("message", "Lesson deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "course-service"));
    }
}
