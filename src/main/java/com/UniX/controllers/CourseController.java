package com.UniX.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CourseController {
    
    @GetMapping("/courses")
    public org.springframework.web.servlet.ModelAndView coursesPage() {
        return new org.springframework.web.servlet.ModelAndView("forward:/courses.html");
    }

    @GetMapping("/course")
    public org.springframework.web.servlet.ModelAndView courseDetailsPage() {
        return new org.springframework.web.servlet.ModelAndView("forward:/courseDetails.html");
    }
}
