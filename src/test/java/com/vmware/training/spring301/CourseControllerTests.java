package com.vmware.training.spring301;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseControllerTests.class)
public class CourseControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getCourses_WillReturn_404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(status().is(404));
    }

}
