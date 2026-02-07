package com.example.ocms.service;

import com.example.ocms.model.Course;
import com.example.ocms.model.Enrollment;
import com.example.ocms.model.User;
import com.example.ocms.repository.EnrollmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> findByStudent(User student) {
        return enrollmentRepository.findByStudent(student);
    }

    public List<Enrollment> findByInstructor(User instructor) {
        return enrollmentRepository.findByCourseInstructor(instructor);
    }

    public Enrollment enroll(User student, Course course) {
        return enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseGet(() -> enrollmentRepository.save(new Enrollment(student, course)));
    }
}
