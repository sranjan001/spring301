package com.vmware.training.spring301;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseController {

    private List<Course> courseRepository = new ArrayList<>();

    public CourseController() {
        Course course = Course.builder()
                .name("Spring Boot")
                .duration(20)
                .build();

        courseRepository.add(course);
    }

    @GetMapping ("/courses")
    public List<Course> getCourses(){
        return courseRepository;
    }

    @PostMapping("/courses")
    public ResponseEntity createCourse(@RequestBody Course course) {

        if(course != null && (course.getName() == null || course.getName().trim().length() == 0))
            return ResponseEntity.badRequest().body("Course name cannot be empty");

        course.setId(courseRepository.size() + 1);
        courseRepository.add(course);

        return ResponseEntity
                .ok(course)
                .status(201)
                .build();

    }
}
