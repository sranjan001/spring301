package com.vmware.training.spring301;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BookController {

    private Map<Integer, Book> bookRepository = new HashMap<>();

    public BookController() {
        Book book = Book.builder()
                .id(1)
                .name("Spring Boot")
                .author("Josh Long")
                .price(40.5d)
                .build();

        bookRepository.put(1, book);
    }

    @GetMapping ("/books")
    public Collection<Book> getBooks(){
        return bookRepository.values();
    }

    @PostMapping("/books")
    public ResponseEntity createBook(@RequestBody Book book) {

        if(book != null && (book.getName() == null || book.getName().trim().length() == 0))
            return ResponseEntity.badRequest().body("Book name cannot be empty");

        book.setId(bookRepository.size() + 1);
        bookRepository.put(book.getId(), book);

        return ResponseEntity
                .ok(book)
                .status(201)
                .build();

    }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable("id") Integer id, @RequestBody Book updatedBook) {

        Book book = bookRepository.get(id);

        book.setName(updatedBook.getName());
        book.setAuthor(updatedBook.getAuthor());
        book.setPrice(updatedBook.getPrice());

        bookRepository.put(book.getId(), book);
        book = bookRepository.get(id);

        return book;

    }


    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Integer id) {
        Book book = bookRepository.get(id);

        if(book != null)
            return ResponseEntity.ok(book);
        else
            return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Integer id) {

        if(bookRepository.containsKey(id)) {
            bookRepository.remove(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
