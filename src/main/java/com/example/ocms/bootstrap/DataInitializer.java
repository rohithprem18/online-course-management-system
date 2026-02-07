package com.example.ocms.bootstrap;

import com.example.ocms.model.Course;
import com.example.ocms.model.Role;
import com.example.ocms.model.User;
import com.example.ocms.service.CourseService;
import com.example.ocms.service.EnrollmentService;
import com.example.ocms.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public DataInitializer(UserService userService, CourseService courseService, EnrollmentService enrollmentService) {
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @Override
    public void run(String... args) {
        if (userService.existsByUsername("admin@ocms")) {
            return;
        }

        userService.createUser("admin@ocms", "Admin123!", "Admin User", Role.ADMIN);

        List<User> instructors = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String username = String.format("instructor%02d@ocms", i);
            instructors.add(userService.createUser(username, "Teach123!", "Instructor " + i, Role.INSTRUCTOR));
        }

        List<User> students = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            String username = String.format("student%03d@ocms", i);
            students.add(userService.createUser(username, "Learn123!", "Student " + i, Role.STUDENT));
        }

        String[] topics = {
            "Spring Boot Fundamentals", "Database Design", "REST API Design", "Java for Web",
            "Security Essentials", "Cloud Deployment", "Testing & QA", "Data Structures",
            "Microservices", "UI/UX for Web", "DevOps Basics", "Performance Tuning",
            "SQL Mastery", "ORM with JPA", "Clean Architecture", "CI/CD Pipelines",
            "System Design", "Observability", "Caching Strategies", "Distributed Systems"
        };

        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < topics.length; i++) {
            User instructor = instructors.get(i % instructors.size());
            String title = topics[i];
            String description = "Course " + (i + 1) + ": " + title;
            courses.add(courseService.save(new Course(title, description, instructor)));
        }

        Random random = new Random(42);
        for (User student : students) {
            int enrollments = 2 + random.nextInt(3);
            for (int i = 0; i < enrollments; i++) {
                Course course = courses.get(random.nextInt(courses.size()));
                enrollmentService.enroll(student, course);
            }
        }
    }
}
