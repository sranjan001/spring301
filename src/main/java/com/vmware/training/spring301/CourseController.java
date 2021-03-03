package com.vmware.training.spring301;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @GetMapping("/courses")
    public void getCourses() {

    }
}
