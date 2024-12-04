package org.example.libraryservice.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Entity(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "borrowed_at")
    private LocalDateTime borrowedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "due_at")
    private LocalDateTime dueAt;

    @Column(name = "is_available")
    private boolean isAvailable;
}
