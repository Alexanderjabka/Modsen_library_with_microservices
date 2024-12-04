package org.example.bookservice.services;


import org.example.bookservice.DTO.BookDTO;
import org.example.bookservice.clients.LibraryServiceClient;
import org.example.bookservice.exceptions.NotFoundException;
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

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));
    }
    public BookDTO getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new NotFoundException("Book not found with ISBN: " + isbn));
    }

    public void createBook(BookDTO BookDTO) {
        Book book = modelMapper.map(BookDTO, Book.class);
        bookRepository.save(book);

        libraryServiceClient.addBookToLibrary(book.getId());
    }

    public void deleteBook(Long id) {
        libraryServiceClient.deleteBookFromLibrary(id);
        bookRepository.deleteById(id);
    }
    public void updateBook(Long id, BookDTO BookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found with ID: " + id));

        book.setIsbn(BookDTO.getIsbn());
        book.setTitle(BookDTO.getTitle());
        book.setDescription(BookDTO.getDescription());
        book.setGenre(BookDTO.getGenre());
        book.setAuthor(BookDTO.getAuthor());

        bookRepository.save(book);
    }
}
