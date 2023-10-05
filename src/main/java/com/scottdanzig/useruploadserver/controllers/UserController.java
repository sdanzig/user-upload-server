package com.scottdanzig.useruploadserver.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @PostMapping
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    boolean successful = true;
    // Parse CSV and validate
    // Save to database
    if(!successful) {
      return ResponseEntity.badRequest().body("File upload failed");
    }
    return ResponseEntity.ok("File uploaded");
  }
}
