package com.example.LibraryManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    @Column(nullable = false)
    private String contact;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private double totalFines = 0.0;
    @Column(nullable = false)
    private String AccountStatus = "ACTIVE";
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime localDateTime;

    @OneToMany(mappedBy = "user")
    private List<BorrowHistory> borrowHistory;

    @OneToMany(mappedBy = "user")
    private List<WaitList> waitlists;
}
