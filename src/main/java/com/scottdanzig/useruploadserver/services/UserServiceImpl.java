package com.scottdanzig.useruploadserver.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scottdanzig.useruploadserver.entities.User;
import com.scottdanzig.useruploadserver.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {
    //Initialize slf4j logger
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public boolean uploadFile(MultipartFile file) {
        List<User> userList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean header = true;

            // TODO - Consider saving users in batches
            while ((line = br.readLine()) != null) {
                log.info("Line: " + line);
                if (line.isEmpty()) {
                    continue;
                }
                if (header) {
                    header = false;
                    continue;
                }
                String[] fields = line.split(",");
                // Log message with all the fields
                log.info("Fields: first name=" + fields[0] + ", last name=" + fields[1] + ", email=" + fields[2] + ", phone=" + fields[3]);
                User user = new User();
                user.setFirstName(fields[0].trim());
                user.setLastName(fields[1].trim());
                user.setEmail(fields[2].trim());
                user.setPhone(fields[3].trim());
                userList.add(user);
                log.info("User: " + user.toString());
            }
            log.info("User list size: " + userList.size());
            if (userList.isEmpty()) {
                return false;
            }
            userRepository.saveAll(userList);
            log.info("Saved all users, returning true");
            return true;
        } catch (Exception e) {
            log.error("Error uploading file: " + e.getMessage(), e);
            return false;
        }
    }
}
