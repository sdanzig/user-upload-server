package com.scottdanzig.useruploadserver.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
