package com.example.LibraryManagement.repositories;

import com.example.LibraryManagement.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book,Long> {
    public List<Book> findAllByAuthor(String author);
    public Optional<Book> findByTitle(String title);
    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    public Optional<Book> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    public List<Book> findByKeyword(@Param("keyword") String keyword);
}
