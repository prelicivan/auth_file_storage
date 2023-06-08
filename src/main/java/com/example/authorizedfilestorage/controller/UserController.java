package com.example.authorizedfilestorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.authorizedfilestorage.message.ResponseMessage;
import com.example.authorizedfilestorage.service.UserStorageService;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserStorageService userStorageService;

    @PostMapping("/createUsers")
    public ResponseEntity<ResponseMessage> createUsers(@RequestParam("numOfUsers") long numOfUsers) {
        String message = "Users have been created!";
        for (int i = 0; i < numOfUsers; i++) {
            userStorageService.store();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }
}
