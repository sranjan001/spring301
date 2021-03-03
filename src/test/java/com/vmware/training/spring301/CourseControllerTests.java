package com.vmware.training.spring301;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTests {

    @Autowired
    private MockMvc mockMvc;


    /**
     * This test can be disabled or deleted. Shows the importance of updating the test cases as the code is developed
     * over time
     */
    @Test
    @Disabled
    public void getCourse_WillReturn_404() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().is(404));
    }

    @Test
    public void getCourse_WillReturn_200() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().is(200));
    }

    @Test
    public void getCourse_WillReturn_BookList() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].name", is("Spring Boot")))
                .andExpect(jsonPath("$[0].durationDays", is(20)));

    }

}
