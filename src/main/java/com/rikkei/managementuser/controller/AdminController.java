package com.rikkei.managementuser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-management")
@RequiredArgsConstructor
public class AdminController {
    @GetMapping("/user")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("ok");

    }
}
