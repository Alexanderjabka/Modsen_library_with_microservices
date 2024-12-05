package org.example.libraryservice.controllers;


import org.example.libraryservice.DTO.LibraryResponse;
import org.example.libraryservice.services.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addNewBook_ShouldReturnCreatedMessage() throws Exception {
        Long bookId = 1L;
        doNothing().when(libraryService).addNewBook(bookId);

        mockMvc.perform(post("/library/add")
                        .param("bookId", String.valueOf(bookId))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().string("Book added to library records"));

        verify(libraryService).addNewBook(bookId);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteBook_ShouldReturnNoContent() throws Exception {
        Long bookId = 1L;
        doNothing().when(libraryService).deleteLibraryRecord(bookId);

        mockMvc.perform(delete("/library/delete")
                        .param("bookId", String.valueOf(bookId))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());

        verify(libraryService).deleteLibraryRecord(bookId);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getAvailableBooks_ShouldReturnList() throws Exception {
        LibraryResponse response = new LibraryResponse(1L,1L, null, null, true);
        when(libraryService.getAvailableBooks()).thenReturn(List.of(response));

        mockMvc.perform(get("/library/available")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1L))
                .andExpect(jsonPath("$[0].available").value(true));

        verify(libraryService).getAvailableBooks();
    }
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getUnavailableBooks_ShouldReturnList() throws Exception {
        LibraryResponse response = new LibraryResponse(1L,1L, null, null, false);
        when(libraryService.getUnavailableBooks()).thenReturn(List.of(response));

        mockMvc.perform(get("/library/unavailable")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1L))
                .andExpect(jsonPath("$[0].available").value(false));

        verify(libraryService).getUnavailableBooks();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void borrowBook_ShouldReturnSuccessMessage() throws Exception {
        Long bookId = 1L;
        doNothing().when(libraryService).borrowBook(bookId);

        mockMvc.perform(post("/library/borrow/{bookId}", bookId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().string("Book with bookId: 1 borrowed successfully"));

        verify(libraryService).borrowBook(bookId);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void returnBook_ShouldReturnSuccessMessage() throws Exception {
        Long bookId = 1L;
        doNothing().when(libraryService).returnBook(bookId);

        mockMvc.perform(post("/library/return/{bookId}", bookId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().string("Book with bookId: 1 returned successfully"));

        verify(libraryService).returnBook(bookId);
    }
}
