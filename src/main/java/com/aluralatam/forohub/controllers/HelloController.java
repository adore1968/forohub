package com.aluralatam.forohub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    private ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok().body("Hello World!");
    }
}
