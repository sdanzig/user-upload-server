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

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean uploadFile(MultipartFile file) {
        List<User> userList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean header = true;

            // TODO - Consider saving users in batches
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                String[] fields = line.split(",");
                User user = new User();
                user.setFirstName(fields[0]);
                user.setLastName(fields[1]);
                user.setEmail(fields[2]);
                user.setPhone(fields[3]);
                userList.add(user);
            }
            if (userList.isEmpty()) {
                return false;
            }
            userRepository.saveAll(userList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
