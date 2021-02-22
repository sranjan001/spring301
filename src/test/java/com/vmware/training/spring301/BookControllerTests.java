package com.vmware.training.spring301;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTests {

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
    public void getBooks_WillReturn_404() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().is(404));
    }

    @Test
    public void getBooks_WillReturn_200() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().is(200));
    }

    @Test
    public void getBooks_WillReturn_BookList() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].name", is("Spring Boot")))
                .andExpect(jsonPath("$[0].author", is("Josh Long")))
                .andExpect(jsonPath("$[0].price", is(40.5), Double.class));

    }

    @Test
    public void postBook_WillReturn201() throws Exception {

        Book newBook = Book.builder()
                .name("New Book")
                .price(10.0d)
                .build();

        String newBookJson = objectMapper.writeValueAsString(newBook);

        mockMvc.perform(post("/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newBookJson))
                .andExpect(status().is(201));
    }

    @Test
    public void postBook_willReturnBadRequest_if_NullBookName() throws Exception {
        Book newBook = Book.builder()
                .author("New Author")
                .price(10.0)
                .build();

        String newBookJson = objectMapper.writeValueAsString(newBook);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newBookJson))
                .andExpect(status().is(400));
    }

    @Test
    public void postBook_WillCreateBook() throws Exception {

        Book newBook = Book.builder()
                .name("New Book")
                .author("New Author")
                .price(10.0)
                .build();

        String newBookJson = objectMapper.writeValueAsString(newBook);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newBookJson))
                .andExpect(status().is(201));

        //This is an incomplete test till this point because we are not sure if the newly created book was persiste or not
        //To verify call the get books and ascertain that

        mockMvc.perform(get("/books"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("New Book")))
                .andExpect(jsonPath("$[1].author", is("New Author")))
                .andExpect(jsonPath("$[1].price", is(10.0), Double.class));

    }

    @Test
    public void putBoot_WillUpdateBook_WithReturn200() throws Exception {
        Book updatedBook = Book.builder()
                .name("Spring Boot")
                .author("Josh Long")
                .price(20.0)
                .build();

        String newBookJson = objectMapper.writeValueAsString(updatedBook);

        mockMvc.perform(put("/books/1").content(newBookJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void putBoot_WillUpdateBook_WithGivenId() throws Exception {
        Book updatedBook = Book.builder()
                .name("Spring Boot")
                .author("Josh Long")
                .price(20.0)
                .build();

        String newBookJson = objectMapper.writeValueAsString(updatedBook);

        mockMvc.perform(put("/books/1").content(newBookJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Spring Boot")))
                .andExpect(jsonPath("$.author", is("Josh Long")))
                .andExpect(jsonPath("$.price", is(20.d), Double.class));


        //This is an incomplete test till this point because we are not sure if the updated book was persisted or not
        //To verify call the get books and ascertain that

        mockMvc.perform( get("/books") )
                .andExpect( status().is(200) )
                .andExpect( jsonPath("$[0].price", is(20.0), Double.class));


    }

    @Test
    public void getBook_WillReturnBook_WithGivenId() throws Exception {

        //Get
        mockMvc.perform(get("/books/1"))
                .andExpect(jsonPath("$.name", is("Spring Boot")))
                .andExpect(jsonPath("$.author", is("Josh Long")))
                .andExpect(status().isOk());

    }

    //TODO - 404 test as exercise
    @Test
    public void getBook_WillReturn404_IfNotFoud() throws Exception {

        mockMvc.perform(get("/books/100"))
                .andExpect(status().isNotFound());
    }
}
