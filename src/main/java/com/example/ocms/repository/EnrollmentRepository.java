package com.example.ocms.repository;

import com.example.ocms.model.Course;
import com.example.ocms.model.Enrollment;
import com.example.ocms.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(User student);
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
    List<Enrollment> findByCourseInstructor(User instructor);
}
