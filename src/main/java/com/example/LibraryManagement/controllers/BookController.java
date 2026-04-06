package com.example.LibraryManagement.controllers;

import com.example.LibraryManagement.dtos.BookResponseDTO;
import com.example.LibraryManagement.entities.Book;
import com.example.LibraryManagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books/displayByKeyword")
    public ResponseEntity<List<BookResponseDTO>> displayBookByKeywords(@RequestParam String keyword){
        return ResponseEntity.ok(bookService.findBookByKeyword(keyword));
    }

    @PostMapping("/books/addBook")
    public ResponseEntity<String> addBook(@RequestBody Book book){
        bookService.addBook(book);
        return ResponseEntity.status(201).body("Book is added");
    }

    @PostMapping("/books/add-more-copies")
    private ResponseEntity<String> addMoreCopies(String title,int copies){
        bookService.addMoreCopies(title,copies);
        return ResponseEntity.ok("More copies added successfully");
    }

    @PostMapping("/books/report-damaged-copies")
    private ResponseEntity<String> reportDamagedCopies(String title,int copies){
        bookService.reportDamagedBook(title,copies);
        return ResponseEntity.ok("Damaged copies reported successfully");
    }
}
