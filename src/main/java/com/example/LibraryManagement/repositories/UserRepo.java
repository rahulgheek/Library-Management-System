package com.example.LibraryManagement.repositories;

import com.example.LibraryManagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    public Optional<User> findByEmail(String email);
}
