package org.example.libraryservice.services;


import org.example.libraryservice.DTO.LibraryResponse;
import org.example.libraryservice.exceptions.RecordNotFound;
import org.example.libraryservice.models.Library;
import org.example.libraryservice.repositories.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private org.modelmapper.ModelMapper modelMapper;

    @InjectMocks
    private LibraryService libraryService;

    LibraryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewBook_ShouldSaveBook() {
        Long bookId = 1L;
        Library library = new Library();
        library.setBookId(bookId);
        library.setAvailable(true);

        ArgumentCaptor<Library> captor = ArgumentCaptor.forClass(Library.class);

        libraryService.addNewBook(bookId);
        verify(libraryRepository).save(captor.capture());

        assertEquals(bookId, captor.getValue().getBookId());
        assertTrue(captor.getValue().isAvailable());
    }

    @Test
    void borrowBook_ShouldUpdateAvailability() {
        Long bookId = 1L;
        Library library = new Library();
        library.setBookId(bookId);
        library.setAvailable(true);

        when(libraryRepository.findByBookId(bookId)).thenReturn(Optional.of(library));

        libraryService.borrowBook(bookId);

        assertFalse(library.isAvailable());
        verify(libraryRepository).save(library);
    }

    @Test
    void returnBook_ShouldUpdateAvailability() {
        Long bookId = 1L;
        Library library = new Library();
        library.setBookId(bookId);
        library.setAvailable(false);

        when(libraryRepository.findByBookId(bookId)).thenReturn(Optional.of(library));

        libraryService.returnBook(bookId);

        assertTrue(library.isAvailable());
        assertNull(library.getBorrowedAt());
        verify(libraryRepository).save(library);
    }

    @Test
    void getAvailableBooks_ShouldReturnList() {
        Library library = new Library();
        library.setBookId(1L);
        library.setAvailable(true);

        when(libraryRepository.findByIsAvailable(true)).thenReturn(List.of(library));

        List<LibraryResponse> result = libraryService.getAvailableBooks();

        assertEquals(1, result.size());
        verify(libraryRepository).findByIsAvailable(true);
    }
    @Test
    void getUnavailableBooks_ShouldReturnList() {
        Library library = new Library();
        library.setBookId(1L);
        library.setAvailable(false);

        when(libraryRepository.findByIsAvailable(false)).thenReturn(List.of(library));

        List<LibraryResponse> result = libraryService.getUnavailableBooks();

        assertEquals(1, result.size());
        verify(libraryRepository).findByIsAvailable(false);
    }

    @Test
    void deleteLibraryRecord_ShouldRemoveRecord() {
        Long bookId = 1L;
        Library library = new Library();
        library.setBookId(bookId);

        when(libraryRepository.findByBookId(bookId)).thenReturn(Optional.of(library));

        libraryService.deleteLibraryRecord(bookId);

        verify(libraryRepository).delete(library);
    }


}
