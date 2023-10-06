package com.scottdanzig.useruploadserver.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.scottdanzig.useruploadserver.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    log.info("Received file: " + file.getOriginalFilename());

    boolean successful = userService.uploadFile(file);

    log.info("File upload successful: " + successful);

    if (!successful) {
      return ResponseEntity.badRequest().body(Map.of("message","File upload failed"));
    }
    return ResponseEntity.ok(Map.of("message", "File uploaded"));
  }
}
