package org.example.bookservice.controllers;

import org.example.bookservice.DTO.BookRequest;
import org.example.bookservice.DTO.BookResponse;
import org.example.bookservice.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        List<BookResponse> mockBooks = List.of(
                new BookResponse(1L, "123456", "Title", "Description", "Genre", "Author")
        );

        when(bookService.getAllBooks()).thenReturn(mockBooks);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockBooks.size()));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getBookById_ShouldReturnBook() throws Exception {
        BookResponse mockBook = new BookResponse(1L, "123456", "Title", "Description", "Genre", "Author");

        when(bookService.getBookById(1L)).thenReturn(mockBook);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("123456"));
    }
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void getBookByIsbn_ShouldReturnBook() throws Exception {
        BookResponse mockBook = new BookResponse(1L, "123456", "Title", "Description", "Genre", "Author");

        when(bookService.getBookByIsbn("123456")).thenReturn(mockBook);

        mockMvc.perform(get("/books/isbn/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("123456"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void addBook_ShouldReturnCreatedBook() throws Exception {
        BookRequest bookRequest = new BookRequest("123456", "Title", "Description", "Genre", "Author");
        BookResponse mockBook = new BookResponse(1L, "123456", "Title", "Description", "Genre", "Author");

        doNothing().when(bookService).createBook(bookRequest);
        when(bookService.getBookByIsbn("123456")).thenReturn(mockBook);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "isbn": "123456",
                                "title": "Title",
                                "description": "Description",
                                "genre": "Genre",
                                "author": "Author"
                            }
                        """)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("123456"));
    }
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void updateBook_ShouldReturnSuccessMessage() throws Exception {
        Long bookId = 1L;

        doNothing().when(bookService).updateBook(eq(bookId), any(BookRequest.class));

        mockMvc.perform(put("/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "isbn": "123456",
                            "title": "Updated Title",
                            "description": "Updated Description",
                            "genre": "Updated Genre",
                            "author": "Updated Author"
                        }
                    """)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Book updated successfully"));

        verify(bookService).updateBook(eq(bookId), any(BookRequest.class));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void deleteBook_ShouldReturnNoContent() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }
}


