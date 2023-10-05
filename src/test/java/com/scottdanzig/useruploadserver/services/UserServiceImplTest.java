package com.scottdanzig.useruploadserver.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class UserServiceImplTest {
    @Test
    public void testUploadFile() {
        UserService userService = new UserServiceImpl();
        
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "content".getBytes());
        
        assertTrue(userService.uploadFile(file));
    }

    //Test upload empty file
    @Test
    public void testUploadFileNoContent() {
        UserService userService = new UserServiceImpl();
        
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "".getBytes());
        
        assertFalse(userService.uploadFile(file));
    }
}
