package com.vmware.training.spring301;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private List<Book> bookRepository = new ArrayList<>();

    public BookController() {
        Book book = Book.builder()
                .name("Spring Boot")
                .author("Josh Long")
                .price(40.5d)
                .build();

        bookRepository.add(book);
    }

    @GetMapping ("/books")
    public List<Book> getBooks(){
        return bookRepository;
    }

    @PostMapping("/books")
    public ResponseEntity createBook(@RequestBody Book book) {

        if(book != null && (book.getName() == null || book.getName().trim().length() == 0))
            return ResponseEntity.badRequest().body("Book name cannot be empty");

        book.setId(bookRepository.size() + 1);
        bookRepository.add(book);

        return ResponseEntity
                .ok(book)
                .status(201)
                .build();

    }
}
