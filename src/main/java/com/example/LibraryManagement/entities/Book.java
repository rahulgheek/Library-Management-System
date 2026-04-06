package com.example.LibraryManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String description;
    private int totalCopies;
    private int availableCopies;

    @OneToMany(mappedBy = "book")
    private List<BorrowHistory> borrowHistory;

    @OneToMany(mappedBy = "book")
    private List<WaitList> waitlists;
}
