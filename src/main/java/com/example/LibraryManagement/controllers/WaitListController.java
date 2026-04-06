package com.example.LibraryManagement.controllers;

import com.example.LibraryManagement.services.WaitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/waitlist")
public class WaitListController {
    @Autowired
    private WaitService waitService;

    @PostMapping("/join")
    public ResponseEntity<String> joinWaitlist(Long id,String email){
        waitService.addToWaitList(id,email);
        return ResponseEntity.status(201).body("Added to waitlist successfully");
    }

    @PostMapping("/claim")
    public ResponseEntity<String> claimNotifiedBook(@RequestParam String email,@RequestParam Long bookid){
        waitService.ClaimNotifiedBook(email,bookid);
        return ResponseEntity.ok("Book claimed");
    }
}
