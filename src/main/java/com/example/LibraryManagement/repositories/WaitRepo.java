package com.example.LibraryManagement.repositories;

import com.example.LibraryManagement.entities.Book;
import com.example.LibraryManagement.entities.User;
import com.example.LibraryManagement.entities.WaitList;
import com.example.LibraryManagement.entities.WaitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WaitRepo extends JpaRepository<WaitList,Long> {
    boolean existsByUserAndBookAndStatus(User user, Book book, WaitStatus status);

    Optional<WaitList> findFirstByBookAndStatusOrderByRequestDateAsc(Book book,WaitStatus status);

    Optional<WaitList> findFirstByUserAndBookAndStatus(User user,Book book,WaitStatus status);

    Optional<List<WaitList>> findAllByStatus(WaitStatus status);
}
