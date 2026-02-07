package com.example.ocms.controller;

import com.example.ocms.model.Role;
import com.example.ocms.model.User;
import com.example.ocms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "user-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "user-form";
        }
        if (userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "duplicate", "Username already exists");
            model.addAttribute("roles", Role.values());
            return "user-form";
        }
        userService.createUser(user.getUsername(), user.getPassword(), user.getFullName(), user.getRole());
        return "redirect:/admin/users";
    }
}
