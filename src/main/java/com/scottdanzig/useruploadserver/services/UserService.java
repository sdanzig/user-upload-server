package com.scottdanzig.useruploadserver.services;

import org.springframework.web.multipart.MultipartFile;

import com.scottdanzig.useruploadserver.exceptions.InputDataException;

// Service interface for storing and retrieving user data
public interface UserService {
    /*
     * Stores user data from a CSV file in the database
     * 
     * @param file The CSV file containing user data
     * @throws InputDataException if the file is empty or contains invalid data
     */
    void storeUsersInCSV(MultipartFile file) throws InputDataException;

    /*
     * Writes all users currently in the database to the log
     */
    void logAllUsers();
}