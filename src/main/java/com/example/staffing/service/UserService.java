package com.example.staffing.service;

import com.example.staffing.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


}
