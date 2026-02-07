package com.example.ocms.controller;

import com.example.ocms.model.Course;
import com.example.ocms.model.User;
import com.example.ocms.service.CourseService;
import com.example.ocms.service.EnrollmentService;
import com.example.ocms.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final UserService userService;

    public EnrollmentController(EnrollmentService enrollmentService, CourseService courseService, UserService userService) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @PostMapping("/courses/{id}/enroll")
    public String enroll(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User student = userService.findByUsername(userDetails.getUsername());
        Course course = courseService.findById(id);
        enrollmentService.enroll(student, course);
        return "redirect:/enrollments";
    }

    @GetMapping("/enrollments")
    public String list(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        if (user.getRole().name().equals("ADMIN")) {
            model.addAttribute("enrollments", enrollmentService.findAll());
        } else if (user.getRole().name().equals("INSTRUCTOR")) {
            model.addAttribute("enrollments", enrollmentService.findByInstructor(user));
        } else {
            model.addAttribute("enrollments", enrollmentService.findByStudent(user));
        }
        return "enrollments";
    }
}
