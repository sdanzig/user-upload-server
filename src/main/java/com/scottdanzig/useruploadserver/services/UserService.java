package com.scottdanzig.useruploadserver.services;

import org.springframework.web.multipart.MultipartFile;

import com.scottdanzig.useruploadserver.exceptions.InputDataException;

public interface UserService {
    void uploadFile(MultipartFile file) throws InputDataException;
}