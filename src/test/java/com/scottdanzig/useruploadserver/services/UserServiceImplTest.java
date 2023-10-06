package com.scottdanzig.useruploadserver.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.scottdanzig.useruploadserver.exceptions.InputDataException;
import com.scottdanzig.useruploadserver.repositories.UserRepository;

@SpringBootTest
public class UserServiceImplTest {
    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private UserService userService;

    @Test
    public void testUploadFile() throws InputDataException {
        String content = "FirstName,LastName,Email,Phone\n" + 
                         "Steven,Spielberg,steven.spielberg@example.com,123-456-7890\n";
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", content.getBytes());

        when(userRepositoryMock.saveAll(anyList())).thenReturn(new ArrayList<>());

        userService.storeUsersInCSV(file);

        verify(userRepositoryMock).saveAll(anyList());
    }

    @Test
    public void testEmptyFile() {
        MultipartFile emptyFile = new MockMultipartFile("file", "test.csv", "text/csv", "".getBytes());

        assertThrows(InputDataException.class, () -> {
            userService.storeUsersInCSV(emptyFile);
        });

        verify(userRepositoryMock, never()).saveAll(anyList());
    }

    @Test
    public void testInvalidData() {
        String content = "FirstName,LastName,Email,Phone\n" + 
                         "Steven,Spielberg,invalid-email,123-456-7890\n";
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", content.getBytes());

        assertThrows(InputDataException.class, () -> {
            userService.storeUsersInCSV(file);
        });

        verify(userRepositoryMock, never()).saveAll(anyList());
    }
}
