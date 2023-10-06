package com.scottdanzig.useruploadserver.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.scottdanzig.useruploadserver.repositories.UserRepository;

@SpringBootTest
public class UserServiceImplTest {
    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private UserService userService;

    @Test
    public void testUploadFile() {
        String content = "FirstName,LastName,Email,Phone\n" + 
                         "Steven,Spielberg,steven.spielberg@example.com,123-456-7890\n";
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", content.getBytes());
    
        when(userRepositoryMock.saveAll(anyList())).thenReturn(new ArrayList<>());
    
        boolean result = userService.uploadFile(file);
    
        verify(userRepositoryMock).saveAll(anyList());
        assertTrue(result);
    }

    @Test
    public void testEmptyFile() {
        MultipartFile emptyFile = new MockMultipartFile("file", "test.csv", "text/csv", "".getBytes());

        boolean result = userService.uploadFile(emptyFile);

        verify(userRepositoryMock, never()).saveAll(anyList());
        assertFalse(result);
    }
}
