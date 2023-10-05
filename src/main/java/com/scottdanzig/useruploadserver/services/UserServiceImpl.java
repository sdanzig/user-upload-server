package com.scottdanzig.useruploadserver.services;

import org.springframework.web.multipart.MultipartFile;

public class UserServiceImpl implements UserService {
    public boolean uploadFile(MultipartFile file) {
        return true;
    }
}
