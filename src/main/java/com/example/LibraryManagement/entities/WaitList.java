package com.example.LibraryManagement.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Table(name = "wait_list")
@Entity
@Getter
@Setter
public class WaitList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WaitStatus status = WaitStatus.PENDING;

    private LocalDateTime notificationDate;
}
