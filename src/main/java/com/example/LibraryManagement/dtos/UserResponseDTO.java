package com.example.LibraryManagement.dtos;

public record UserResponseDTO(
        String email,
        String contact,
        String address,
        String city,
        Double totalFines
) {
}
