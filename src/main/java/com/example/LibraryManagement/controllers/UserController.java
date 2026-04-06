package com.example.LibraryManagement.controllers;

import com.example.LibraryManagement.dtos.UserResponseDTO;
import com.example.LibraryManagement.entities.User;
import com.example.LibraryManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/users/add")
    public ResponseEntity<String> addUser(@RequestBody User user){
        service.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }

    @GetMapping("/users/display-all")
    public ResponseEntity<List<UserResponseDTO>> displayAll(){
        return ResponseEntity.ok(service.display());
    }

    @GetMapping("/users/displayById")
    public ResponseEntity<UserResponseDTO> displayById(@RequestParam Long id){
        return ResponseEntity.ok(service.getUserProfile(id));
    }

    @PostMapping("/users/payFine")
    public ResponseEntity<String> payFine(String email,Double amount){
        service.payFine(email,amount);

        return ResponseEntity.ok("Fine payed successfully");
    }


}
