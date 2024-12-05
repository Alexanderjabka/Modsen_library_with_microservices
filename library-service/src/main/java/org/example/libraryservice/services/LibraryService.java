package org.example.libraryservice.services;

import org.example.libraryservice.DTO.LibraryResponse;
import org.example.libraryservice.exceptions.AlreadyTakenRecord;
import org.example.libraryservice.exceptions.RecordNotFound;
import org.example.libraryservice.models.Library;
import org.example.libraryservice.repositories.LibraryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    private final ModelMapper modelMapper;

    public LibraryService(LibraryRepository libraryRepository,  ModelMapper modelMapper) {
        this.libraryRepository = libraryRepository;
        this.modelMapper = modelMapper;
    }

    public void addNewBook(Long bookId) {

        Library record = new Library();
        record.setBookId(bookId);
        record.setAvailable(true);
        libraryRepository.save(record);
    }
    public void borrowBook(Long bookId) {
        Library record = libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new RecordNotFound("Book not found in library records"));

        if (!record.isAvailable()) {
            throw new AlreadyTakenRecord("Book is already borrowed");
        }
        LocalDateTime nowMoscow = LocalDateTime.now(ZoneId.of("Europe/Moscow"));

        record.setAvailable(false);
        record.setBorrowedAt(nowMoscow);
        record.setDueAt(nowMoscow.plusDays(7));
        libraryRepository.save(record);
    }

    public void returnBook(Long bookId) {
        Library record = libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new RecordNotFound("Book not found in library records"));

        record.setAvailable(true);
        record.setBorrowedAt(null);
        record.setDueAt(null);
        libraryRepository.save(record);
    }
    public List<LibraryResponse> getAvailableBooks() {

        List<Library> availableBooks = libraryRepository.findByIsAvailable(true);
        return availableBooks.stream()
                .map(book -> modelMapper.map(book, LibraryResponse.class))
                .collect(Collectors.toList());
    }
    public List<LibraryResponse> getUnavailableBooks() {
        List<Library> availableBooks = libraryRepository.findByIsAvailable(false);
        return availableBooks.stream()
                .map(book -> modelMapper.map(book, LibraryResponse.class))
                .collect(Collectors.toList());
    }
    public void deleteLibraryRecord(Long bookId){
        Library libraryRecord = libraryRepository.findByBookId(bookId)
                .orElseThrow(() -> new RecordNotFound("No library record found for bookId: " + bookId));

        libraryRepository.delete(libraryRecord);
    }

}
