package com.example.ocms.exception;

import java.util.NoSuchElementException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied() {
        return "access-denied";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(Model model) {
        model.addAttribute("message", "The requested resource was not found.");
        return "error-404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Model model) {
        model.addAttribute("message", "Something went wrong. Please try again.");
        return "error-500";
    }
}
