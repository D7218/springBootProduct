package org.example.controller;

import org.example.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);
    private final AppService service;

    public AppController(AppService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        String msg = service.getMessage();
        if (msg == null) {
            log.warn("Service returned null message for /hello");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(msg);
    }
}

