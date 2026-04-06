package com.example.LibraryManagement.services;

import com.example.LibraryManagement.entities.Book;
import com.example.LibraryManagement.entities.WaitList;
import com.example.LibraryManagement.entities.WaitStatus;
import com.example.LibraryManagement.repositories.BookRepo;
import com.example.LibraryManagement.repositories.WaitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public class CancelBookIssue {
    @Autowired
    private WaitService waitService;
    @Autowired
    private WaitRepo waitRepo;
    @Autowired
    private BookRepo bookRepo;
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void CancellingBookIssue(){
        List<WaitList> notifiedUsers = waitRepo.findAllByStatus(WaitStatus.NOTIFIED).orElseThrow(() -> new RuntimeException("No Notified Users"));
        LocalDateTime today = LocalDateTime.now();
        for(WaitList waitList : notifiedUsers){
            if(today.isAfter(waitList.getNotificationDate().plusHours(48))){
                waitList.setStatus(WaitStatus.CANCELLED);
                waitRepo.save(waitList);

                Book book = waitList.getBook();

                boolean someoneElseIsWaiting = waitService.processNextInLine(book);

                if(!someoneElseIsWaiting){
                    book.setAvailableCopies(book.getAvailableCopies()+1);
                    bookRepo.save(book);
                }
            }
        }
    }
}
