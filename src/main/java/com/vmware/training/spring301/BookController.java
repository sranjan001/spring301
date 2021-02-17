package com.vmware.training.spring301;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BookController {

    @GetMapping ("/books")
    public List<Book> getBooks(){

        Book book = Book.builder()
                .name("Spring Boot")
                .author("Josh Long")
                .price(40.5d)
                .build();

        return Arrays.asList(book);

    }
}
