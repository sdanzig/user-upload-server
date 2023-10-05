package com.scottdanzig.useruploadserver.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.scottdanzig.useruploadserver.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {
  
  @Autowired
  private UserService userService;
 
  @PostMapping
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    boolean successful = userService.uploadFile(file);;

    if(!successful) {
      return ResponseEntity.badRequest().body("File upload failed");
    }
    return ResponseEntity.ok("File uploaded");
  }
}
