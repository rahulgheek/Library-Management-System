package com.example.LibraryManagement.services;

import com.example.LibraryManagement.entities.Book;
import com.example.LibraryManagement.entities.BorrowHistory;
import com.example.LibraryManagement.entities.BorrowStaus;
import com.example.LibraryManagement.entities.User;
import com.example.LibraryManagement.repositories.BookRepo;
import com.example.LibraryManagement.repositories.BorrowRepo;
import com.example.LibraryManagement.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepo borrowRepo;
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WaitService waitService;

    @Transactional
    public String borrowBook(Long id,String email){
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if("LOCKED".equals(user.getAccountStatus())){
            throw new RuntimeException("Account is Locked");
        }

        if(book.getAvailableCopies() < 1){
            throw new RuntimeException("This book is out of stock. Please join the waitlist.");
        }

        BorrowHistory history = new BorrowHistory();
        history.setUser(user);
        history.setBook(book);
        history.setBorrowDate(LocalDate.now());
        history.setStatus(BorrowStaus.BORROWED);
        history.setDueDate(LocalDate.now().plusDays(14));

        book.setAvailableCopies(book.getAvailableCopies()-1);
        bookRepo.save(book);
        borrowRepo.save(history);
        return history.getUser().getEmail() + " borrowed " + history.getBook().getTitle();
    }

    @Transactional
    public String returnBook(Long bookId, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BorrowHistory history = borrowRepo.findByUserAndBookAndStatus(user, book, BorrowStaus.BORROWED)
                .orElseThrow(() -> new RuntimeException("No active borrow record found for this user and book."));

        history.setStatus(BorrowStaus.RETURNED);
        history.setReturnDate(LocalDate.now());
        borrowRepo.save(history);

        boolean wasHeldForWaitlist = waitService.processNextInLine(book);

        if (!wasHeldForWaitlist) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepo.save(book);
        }
        return history.getUser().getEmail() + " returned the book " + history.getBook().getTitle() + " on " + history.getReturnDate().toString();
    }
}
