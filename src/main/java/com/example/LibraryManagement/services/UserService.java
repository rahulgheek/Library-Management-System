package com.example.LibraryManagement.services;

import com.example.LibraryManagement.dtos.UserResponseDTO;
import com.example.LibraryManagement.entities.User;
import com.example.LibraryManagement.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    public List<UserResponseDTO> display(){
        List<User> users = userRepo.findAll();

        return users.stream().map(
                user -> new UserResponseDTO(
                        user.getEmail(),
                        user.getContact(),
                        user.getAddress(),
                        user.getCity(),
                        user.getTotalFines()
                )
        ).collect(Collectors.toList());
    }

    public void addUser(User user){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepo.save(user);
    }

    public UserResponseDTO getUserProfile(Long userId){
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User with id " + userId + " Not found"));

        return new UserResponseDTO(
                user.getEmail(),
                user.getAddress(),
                user.getAddress(),
                user.getCity(),
                user.getTotalFines()
        );
    }

    public void payFine(String email,Double amount){
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("No User found"));

        if(amount < 0.0){
            throw new RuntimeException("Invalid amount");
        }
        double newAmount = user.getTotalFines() - amount;
        if (newAmount < 0) {
            newAmount = 0.0;
        }
        if(newAmount < 500){
            user.setAccountStatus("ACTIVE");
        }

        user.setTotalFines(newAmount);
        userRepo.save(user);
    }
}
