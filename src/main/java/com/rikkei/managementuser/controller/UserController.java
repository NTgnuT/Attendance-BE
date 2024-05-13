package com.rikkei.managementuser.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-management")
public class UserController {
    @GetMapping("/user")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("oke");
    }
}
