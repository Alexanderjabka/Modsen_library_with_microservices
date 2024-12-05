package org.example.bookservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String isbn;
    private String title;
    private String genre;
    private String description;
    private String author;
}
