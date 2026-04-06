package com.example.LibraryManagement.services;

import com.example.LibraryManagement.entities.*;
import com.example.LibraryManagement.repositories.BookRepo;
import com.example.LibraryManagement.repositories.BorrowRepo;
import com.example.LibraryManagement.repositories.UserRepo;
import com.example.LibraryManagement.repositories.WaitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WaitService {
    @Autowired
    private WaitRepo waitRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private MailService mailService;

    @Autowired
    private BorrowRepo borrowRepo;


    public void addToWaitList(Long id,String email){
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if("LOCKED".equals(user.getAccountStatus())){
            throw new RuntimeException("Account is locked");
        }
        if(book.getAvailableCopies() > 0){
            throw new RuntimeException("Book is available");
        }

        boolean alreadyWaiting = waitRepo.existsByUserAndBookAndStatus(user, book, WaitStatus.PENDING);
        if (alreadyWaiting) {
            throw new RuntimeException("You are already on the waitlist for this book!");
        }

        WaitList list = new WaitList();
        list.setBook(book);
        list.setUser(user);
        list.setStatus(WaitStatus.PENDING);
        list.setRequestDate(LocalDateTime.now());

        waitRepo.save(list);
    }

    public boolean processNextInLine(Book book){
        Optional<WaitList> waitList = waitRepo.findFirstByBookAndStatusOrderByRequestDateAsc(book,WaitStatus.PENDING);

        if(waitList.isPresent()){
            WaitList list = waitList.get();
            list.setNotificationDate(LocalDateTime.now());
            list.setStatus(WaitStatus.NOTIFIED);
            waitRepo.save(list);

            String subject = "Your Waitlist Book is Available!";
            String body = "Great news! '" + book.getTitle() + "' has been returned. " +
                    "We are holding it at the front desk for you for 48 hours.";

            mailService.sendMail(list.getUser().getEmail(), subject, body);
            return true;
        }
        return false;
    }

    public void ClaimNotifiedBook(String email,Long bookId){
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        WaitList list = waitRepo.findFirstByUserAndBookAndStatus(user,book,WaitStatus.NOTIFIED).orElseThrow(() -> new RuntimeException("No notified user"));

        BorrowHistory history = new BorrowHistory();
        history.setBook(list.getBook());
        history.setUser(list.getUser());
        history.setStatus(BorrowStaus.BORROWED);
        history.setBorrowDate(LocalDate.now());
        history.setDueDate(LocalDate.now().plusDays(14));
        list.setStatus(WaitStatus.FULFILLED);
        borrowRepo.save(history);
        waitRepo.save(list);
    }

}
