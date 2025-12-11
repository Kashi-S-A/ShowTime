package com.showtime.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showtime.user_service.dto.UserDto;
import com.showtime.user_service.entity.User;
import com.showtime.user_service.exception.ApiException;
import com.showtime.user_service.repository.UserRepository;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public String register(UserDto userDto) {

        if (repository.existsByEmail(userDto.getEmail())) {
            throw new ApiException("Email already exists!");
        }

        
        if (repository.existsByPhone(userDto.getPhone())) {
            throw new ApiException("Phone already exists!");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setPassword(userDto.getPassword());

        repository.save(user);

        return "User registered successfully!";
    }
    @Override
    public String login(String email, String password) {

        User user = repository.findByEmail(email);

        if (user == null) {
            return "Invalid email";
        }

        if (!user.getPassword().equals((password))) {
            return "Invalid password";
        }

        return "Login successful";
    }

}
