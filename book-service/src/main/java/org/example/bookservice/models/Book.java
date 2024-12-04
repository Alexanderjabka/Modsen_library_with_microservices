package org.example.bookservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(unique = true, nullable = false,name = "isbn")
    @NotBlank(message = "ISBN cannot be empty")
    @Size(min=10,max = 13,message = "ISBN must be between 10 and 13 characters")
    private String isbn;

    @Column(name = "title")
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 255,message = "Title cannot exceed 100 characters")
    private String title;

    @Column(name = "genre")
    @NotBlank(message = "Genre cannot be empty")
    @Size(max = 100,message = "Genre cannot exceed 100 characters")
    private String genre;

    @Column(name = "description")
    @NotBlank(message = "Gescription cannot be empty")
    @Size(max = 500,message = "Description cannot exceed 500 characters")
    private String description;

    @Column(name = "author")
    @NotBlank(message = "Author cannot be empty")
    @Size(max = 100,message = "Author name cannot exceed 100 characters")
    private String author;



}
