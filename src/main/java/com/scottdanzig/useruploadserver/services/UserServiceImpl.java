package com.scottdanzig.useruploadserver.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scottdanzig.useruploadserver.entities.User;
import com.scottdanzig.useruploadserver.repositories.UserRepository;
import com.scottdanzig.useruploadserver.exceptions.InputDataException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]+$");

    @Autowired
    private UserRepository userRepository;

    public void uploadFile(MultipartFile file) throws InputDataException {
        List<User> userList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean header = true;

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

                // Validate number of fields
                if (fields.length != 4) {
                    throw new InputDataException("Each line must contain exactly 4 fields.");
                }

                // Validate each field
                validateField("First Name", fields[0], NAME_PATTERN);
                validateField("Last Name", fields[1], NAME_PATTERN);
                validateField("Email", fields[2], EMAIL_PATTERN);

                User user = new User();
                user.setFirstName(fields[0].trim());
                user.setLastName(fields[1].trim());
                user.setEmail(fields[2].trim());
                user.setPhone(fields[3].trim());

                userList.add(user);
                log.info("User: " + user.toString());
            }

            if (userList.isEmpty()) {
                throw new InputDataException("File does not contain any data.");
            }

            userRepository.saveAll(userList);
            log.info("Saved all users, returning true");
            return;

        } catch (InputDataException idve) {
            throw idve;
        } catch (IOException ioe) {
            log.error("Error reading file: " + ioe.getMessage(), ioe);
            throw new InputDataException("Error reading file: " + ioe.getMessage(), ioe);
        }
    }

    private void validateField(String fieldName, String fieldValue, Pattern pattern)
            throws InputDataException {
        if (fieldValue.trim().isEmpty()) {
            throw new InputDataException(fieldName + " cannot be empty.");
        }
        if (!pattern.matcher(fieldValue).matches()) {
            throw new InputDataException(fieldName + " is in an invalid format.");
        }
    }
}
