package com.example.LibraryManagement.services;

import com.example.LibraryManagement.entities.BorrowHistory;
import com.example.LibraryManagement.entities.BorrowStaus;
import com.example.LibraryManagement.entities.User;
import com.example.LibraryManagement.repositories.BorrowRepo;
import com.example.LibraryManagement.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FineCalculationService {

    @Autowired
    private BorrowRepo borrowRepo;

    @Autowired
    private UserRepo userRepo;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void fineCalculation(){

        // 1. Fetch ONLY books that are currently out of the library
        List<BorrowHistory> activeBorrows = borrowRepo.findAllByStatusIn(
                List.of(BorrowStaus.BORROWED, BorrowStaus.OVERDUE)
        );

        LocalDate today = LocalDate.now();

        for (BorrowHistory history : activeBorrows) {

            // 2. Check if the book is late
            if (today.isAfter(history.getDueDate())) {

                // 3. Update the book status
                history.setStatus(BorrowStaus.OVERDUE);
                borrowRepo.save(history); // Explicitly save the history!

                // 4. Update the user fines
                User user = history.getUser();
                user.setTotalFines(user.getTotalFines() + 20.0);

                if(user.getTotalFines() >= 200.0){
                    user.setAccountStatus("LOCKED");
                }

                userRepo.save(user);
                System.out.println("Charged ₹20 to " + user.getEmail() + " for book: " + history.getBook().getTitle());
            }
        }
    }
}