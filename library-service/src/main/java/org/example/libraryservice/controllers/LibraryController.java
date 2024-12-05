package org.example.libraryservice.controllers;


import org.example.libraryservice.DTO.LibraryResponse;
import org.example.libraryservice.services.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewBook(@RequestParam Long bookId) {
        libraryService.addNewBook(bookId);
        return ResponseEntity.status(201).body("Book added to library records");
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBook(@RequestParam Long bookId) {
            libraryService.deleteLibraryRecord(bookId);
            return ResponseEntity.noContent().build();
    }
    @GetMapping("/available")
    public ResponseEntity<List<LibraryResponse>> getAvailableBooks() {
        return ResponseEntity.ok(libraryService.getAvailableBooks());
    }
    @GetMapping("/unavailable")
    public ResponseEntity<List<LibraryResponse>> getUnavailableBooks() {
        return ResponseEntity.ok(libraryService.getUnavailableBooks());
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long bookId) {
        libraryService.borrowBook(bookId);
        return ResponseEntity.status(201).body("Book with bookId" + ": " + bookId + " borrowed successfully");
    }

    @PostMapping("/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId) {
        libraryService.returnBook(bookId);
        return ResponseEntity.status(201).body("Book with bookId" + ": " + bookId + " returned successfully");
    }

}
