package com.vmware.training.spring301;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CourseController {

    private Map<Integer, Course> courseRepository = new HashMap<>();

    public CourseController() {
        Course course = Course.builder()
                .id(1)
                .name("Spring Boot")
                .duration(20)
                .build();

        courseRepository.put(1, course);
    }

    @GetMapping ("/courses")
    public Collection<Course> getCourses(){
        return courseRepository.values();
    }

    @PostMapping("/courses")
    public ResponseEntity createCourse(@RequestBody Course course) {

        if(course != null && (course.getName() == null || course.getName().trim().length() == 0))
            return ResponseEntity.badRequest().body("Course name cannot be empty");

        course.setId(courseRepository.size() + 1);
        courseRepository.put(course.getId(), course);

        return ResponseEntity
                .ok(course)
                .status(201)
                .build();

    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@PathVariable("id") Integer id, @RequestBody Course updatedCourse) {

        Course course = courseRepository.get(id);

        course.setName(updatedCourse.getName());
        course.setDuration(updatedCourse.getDuration());

        courseRepository.put(course.getId(), course);
        course = courseRepository.get(id);

        return course;

    }


    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getBook(@PathVariable("id") Integer id) {
        Course course = courseRepository.get(id);

        if(course != null)
            return ResponseEntity.ok(course);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Integer id) {

        if(courseRepository.containsKey(id)) {
            courseRepository.remove(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
