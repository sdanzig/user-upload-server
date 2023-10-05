package com.scottdanzig.useruploadserver.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    boolean uploadFile(MultipartFile file);
}