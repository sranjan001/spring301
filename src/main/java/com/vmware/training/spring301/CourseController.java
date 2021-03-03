package com.vmware.training.spring301;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CourseController {

    @GetMapping ("/courses")
    public List<Course> getBooks(){

        Course book = Course.builder()
                .name("Spring Boot")
                .build();

        return Arrays.asList(book);

    }
}
