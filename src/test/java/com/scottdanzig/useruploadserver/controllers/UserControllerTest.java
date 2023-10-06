package com.scottdanzig.useruploadserver.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.scottdanzig.useruploadserver.exceptions.InputDataException;
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

    // Test uploading a valid file
    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "content".getBytes());
        
        ResultActions perform = this.mockMvc.perform(multipart("/api/users").file(file));
        
        perform.andExpect(status().isOk());
    }

    // Test uploading an invalid file
    @Test
    public void testUploadFileInvalidData() throws Exception {
        Mockito.doThrow(InputDataException.class).when(userService).uploadFile(Mockito.any());

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "invalid_content".getBytes());
        
        ResultActions perform = this.mockMvc.perform(multipart("/api/users").file(file));
        
        perform.andExpect(status().isBadRequest());
    }
}
