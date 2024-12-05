package org.example.libraryservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryResponse {
    private Long id;
    private Long bookId;
    private LocalDateTime borrowedAt;
    private LocalDateTime dueAt;
    private boolean isAvailable;


}
