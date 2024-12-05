package org.example.bookservice.controllers;

import org.example.bookservice.DTO.BookRequest;
import org.example.bookservice.DTO.BookResponse;
import org.example.bookservice.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping()
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        bookService.createBook(bookRequest);

        return ResponseEntity.status(201).body(bookService.getBookByIsbn(bookRequest.getIsbn()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok("Book updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        //204
        return ResponseEntity.noContent().build();
    }
}
