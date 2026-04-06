package com.example.LibraryManagement.repositories;

import com.example.LibraryManagement.entities.Book;
import com.example.LibraryManagement.entities.BorrowHistory;
import com.example.LibraryManagement.entities.BorrowStaus;
import com.example.LibraryManagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRepo extends JpaRepository<BorrowHistory,Long> {
    Optional<BorrowHistory> findByUserAndBookAndStatus(User user, Book book, BorrowStaus status);
    List<BorrowHistory> findAllByStatusIn(List<BorrowStaus> statuses);
}
