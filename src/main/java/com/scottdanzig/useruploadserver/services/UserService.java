package com.scottdanzig.useruploadserver.services;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean uploadFile(MultipartFile file);
}