package org.example.bookservice.services;

import org.example.bookservice.DTO.BookRequest;
import org.example.bookservice.DTO.BookResponse;
import org.example.bookservice.clients.LibraryServiceClient;
import org.example.bookservice.exceptions.BookNotFoundException;
import org.example.bookservice.models.Book;
import org.example.bookservice.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private LibraryServiceClient libraryServiceClient;

    @InjectMocks
    private BookService bookService;

    @Test
    void getBookById_ShouldReturnBookResponse() {
        Book mockBook = new Book(1L, "123456", "Title", "Description", "Genre", "Author");
        BookResponse mockResponse = new BookResponse(1L, "123456", "Title", "Description", "Genre", "Author");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));
        when(modelMapper.map(mockBook, BookResponse.class)).thenReturn(mockResponse);

        BookResponse result = bookService.getBookById(1L);

        assertEquals("123456", result.getIsbn());
        verify(bookRepository).findById(1L);
    }

    @Test
    void getBookById_ShouldThrowExceptionWhenNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
    }
    @Test
    void getBookByIsbn_ShouldThrowExceptionWhenNotFound() {
        when(bookRepository.findByIsbn("123456")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookByIsbn("123456"));
    }

    @Test
    void createBook_ShouldSaveBookAndCallLibraryService() {
        BookRequest bookRequest = new BookRequest("123456", "Title", "Description", "Genre", "Author");
        Book mockBook = new Book(1L, "123456", "Title", "Description", "Genre", "Author");

        when(bookRepository.findByIsbn("123456")).thenReturn(Optional.empty());
        when(modelMapper.map(bookRequest, Book.class)).thenReturn(mockBook);
        when(bookRepository.save(mockBook)).thenReturn(mockBook);

        doNothing().when(libraryServiceClient).addBookToLibrary(mockBook.getId());

        assertDoesNotThrow(() -> bookService.createBook(bookRequest));
        verify(bookRepository).save(mockBook);
        verify(libraryServiceClient).addBookToLibrary(mockBook.getId());
    }

    @Test
    void deleteBook_ShouldDeleteBookAndCallLibraryService() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(libraryServiceClient).deleteBookFromLibrary(1L);

        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
        verify(libraryServiceClient).deleteBookFromLibrary(1L);
    }

    @Test
    void deleteBook_ShouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }
}

