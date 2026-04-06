package com.example.LibraryManagement.dtos;

import java.time.LocalDateTime;

// Using a Record makes this clean and immutable!
public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {}