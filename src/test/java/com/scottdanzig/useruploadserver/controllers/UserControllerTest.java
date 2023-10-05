package com.scottdanzig.useruploadserver.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.scottdanzig.useruploadserver.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // Test uploading a file with content
    @Test
    public void testUploadFile() throws Exception {
        Mockito.when(userService.uploadFile(Mockito.any())).thenReturn(true);

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "content".getBytes());
        this.mockMvc.perform(multipart("/api/users").file(file))
                .andExpect(status().isOk());
    }

    // Test uploading a file with no content
    @Test
    public void testUploadFileNoContent() throws Exception {
        Mockito.when(userService.uploadFile(Mockito.any())).thenReturn(false);
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "".getBytes());
        this.mockMvc.perform(multipart("/api/users").file(file))
                .andExpect(status().isBadRequest());
    }
}