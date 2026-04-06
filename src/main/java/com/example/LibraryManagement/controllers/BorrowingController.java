package com.example.LibraryManagement.controllers;

import com.example.LibraryManagement.entities.BorrowHistory;
import com.example.LibraryManagement.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowingController {
    @Autowired
    private BorrowService borrowService;

    @PostMapping("/borrow/borrowBook")
    public ResponseEntity<String> borrowBook(@RequestParam Long bookId,@RequestParam String email){
        return ResponseEntity.status(201).body(borrowService.borrowBook(bookId,email));
    }

    @PostMapping("/borrow/returnBook")
    public ResponseEntity<String> returnBook(Long bookId,String email){
        return ResponseEntity.ok(borrowService.returnBook(bookId,email));
    }
}
