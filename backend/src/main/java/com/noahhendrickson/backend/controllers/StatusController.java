package com.noahhendrickson.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatusController {

    @GetMapping("/status")
    public Object getStatus() {
        return Map.of("status", 200);
    }
}
