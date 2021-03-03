package com.vmware.training.spring301;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;


    /**
     * This test can be disabled or deleted. Shows the importance of updating the test cases as the code is developed
     * over time
     */
    @Test
    @Disabled
    public void getCourses_WillReturn_404() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().is(404));
    }

    @Test
    public void getCourses_WillReturn_200() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().is(200));
    }

    @Test
    // @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getCourses_WillReturn_CourseList() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].name", is("Spring Boot")))
                .andExpect(jsonPath("$[0].duration", is(20)));

    }

    @Test
    public void postCourse_WillReturn201() throws Exception {

        Course newCourse = Course.builder()
                .name("New Course")
                .duration(10)
                .build();

        String newCourseJson = objectMapper.writeValueAsString(newCourse);

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCourseJson))
                .andExpect(status().is(201));
    }

    @Test
    public void postCourse_willReturnBadRequest_if_NullCourseName() throws Exception {
        Course newCourse = Course.builder()
                .duration(30)
                .build();

        String newCourseJson = objectMapper.writeValueAsString(newCourse);

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCourseJson))
                .andExpect(status().is(400));
    }

    @Test
    public void postCourse_WillCreateCourse() throws Exception {

        Course newCourse = Course.builder()
                .name("New Course")
                .duration(30)
                .build();

        String newCourseJson = objectMapper.writeValueAsString(newCourse);

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCourseJson))
                .andExpect(status().is(201));

        //This is an incomplete test till this point because we are not sure if the newly created course was persiste or not
        //To verify call the get courses and ascertain that

        mockMvc.perform(get("/courses"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("New Course")))
                .andExpect(jsonPath("$[1].duration", is(30)));

    }

    @Test
    public void putCourse_WillUpdateCourse_WithReturn200() throws Exception {
        Course updatedCourse = Course.builder()
                .name("Spring Boot")
                .duration(20)
                .build();

        String newCourseJson = objectMapper.writeValueAsString(updatedCourse);

        mockMvc.perform(put("/courses/1").content(newCourseJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void putCourse_WillUpdateCourse_WithGivenId() throws Exception {
        Course updatedCourse = Course.builder()
                .name("Spring Boot")
                .duration(60)
                .build();

        String newCourseJson = objectMapper.writeValueAsString(updatedCourse);

        mockMvc.perform(put("/courses/1").content(newCourseJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Spring Boot")))
                .andExpect(jsonPath("$.duration", is(60)));


        //This is an incomplete test till this point because we are not sure if the updated course was persisted or not
        //To verify call the get courses and ascertain that

        mockMvc.perform( get("/courses") )
                .andExpect( status().is(200) )
                .andExpect( jsonPath("$[0].duration", is(60)));


    }

    //TODO - put : Check for missing id

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getCourse_WillReturnCourse_WithGivenId() throws Exception {

        //Get
        mockMvc.perform(get("/courses/1"))
                .andExpect(jsonPath("$.name", is("Spring Boot")))
                .andExpect(jsonPath("$.duration", is(20)))
                .andExpect(status().isOk());

    }

    //TODO - 404 test as exercise
    @Test
    public void getCourse_WillReturn404_IfNotFoud() throws Exception {

        mockMvc.perform(get("/courses/100"))
                .andExpect(status().isNotFound());
    }
}
