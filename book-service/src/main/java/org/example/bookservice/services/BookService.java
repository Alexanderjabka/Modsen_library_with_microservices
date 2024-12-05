package org.example.bookservice.services;



import org.example.bookservice.DTO.BookRequest;
import org.example.bookservice.DTO.BookResponse;
import org.example.bookservice.clients.LibraryServiceClient;
import org.example.bookservice.exceptions.BookNotFoundException;
import org.example.bookservice.exceptions.ExternalServiceException;
import org.example.bookservice.exceptions.InvalidBookOperationException;
import org.example.bookservice.models.Book;
import org.example.bookservice.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    private final LibraryServiceClient libraryServiceClient;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper, LibraryServiceClient libraryServiceClient) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.libraryServiceClient = libraryServiceClient;
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }

    public BookResponse getBookById(Long id) {
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookResponse.class))
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }
    public BookResponse getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(book -> modelMapper.map(book, BookResponse.class))
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
    }

    public void createBook(BookRequest bookRequest) {


        if (bookRepository.findByIsbn(bookRequest.getIsbn()).isPresent()) {
            throw new InvalidBookOperationException("A book with the same ISBN already exists: " + bookRequest.getIsbn());
        }

        Book book = modelMapper.map(bookRequest, Book.class);
        bookRepository.save(book);
        try {
            libraryServiceClient.addBookToLibrary(book.getId());
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to add book to library service", ex);
        }
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        try {
            libraryServiceClient.deleteBookFromLibrary(id);
        } catch (Exception ex) {
            throw new ExternalServiceException("Failed to delete book from library service", ex);
        }

        bookRepository.deleteById(id);
    }
    public void updateBook(Long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        bookRepository.findByIsbn(bookRequest.getIsbn()).ifPresent(existingBook -> {
            if (!existingBook.getId().equals(id)) {
                throw new InvalidBookOperationException("A book with the same ISBN already exists: " + bookRequest.getIsbn());
            }
        });

        book.setIsbn(bookRequest.getIsbn());
        book.setTitle(bookRequest.getTitle());
        book.setDescription(bookRequest.getDescription());
        book.setGenre(bookRequest.getGenre());
        book.setAuthor(bookRequest.getAuthor());

        bookRepository.save(book);
    }
}
