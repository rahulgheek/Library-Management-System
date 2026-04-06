package com.example.LibraryManagement.dtos;

public record BookResponseDTO(
        String title,
        String author,
        String description,
        int totalCopies,
        int availableCopies
) {
}
