package com.example.ocms.controller;

import com.example.ocms.model.Course;
import com.example.ocms.model.User;
import com.example.ocms.service.CourseService;
import com.example.ocms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        return "course-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Course course, BindingResult result,
                         @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "course-form";
        }
        User instructor = userService.findByUsername(userDetails.getUsername());
        course.setInstructor(instructor);
        courseService.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        Course course = courseService.findById(id);
        User current = userService.findByUsername(userDetails.getUsername());
        if (!current.getRole().name().equals("ADMIN") && !course.getInstructor().getId().equals(current.getId())) {
            throw new AccessDeniedException("Not allowed to edit this course");
        }
        model.addAttribute("course", course);
        return "course-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Course course, BindingResult result,
                         @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "course-form";
        }
        Course existing = courseService.findById(id);
        User current = userService.findByUsername(userDetails.getUsername());
        if (!current.getRole().name().equals("ADMIN") && !existing.getInstructor().getId().equals(current.getId())) {
            throw new AccessDeniedException("Not allowed to update this course");
        }
        existing.setTitle(course.getTitle());
        existing.setDescription(course.getDescription());
        existing.setInstructor(current);
        courseService.save(existing);
        return "redirect:/courses";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Course existing = courseService.findById(id);
        User current = userService.findByUsername(userDetails.getUsername());
        if (!current.getRole().name().equals("ADMIN") && !existing.getInstructor().getId().equals(current.getId())) {
            throw new AccessDeniedException("Not allowed to delete this course");
        }
        courseService.delete(id);
        return "redirect:/courses";
    }
}
