package com.example.ocms.repository;

import com.example.ocms.model.Course;
import com.example.ocms.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByInstructor(User instructor);
}
