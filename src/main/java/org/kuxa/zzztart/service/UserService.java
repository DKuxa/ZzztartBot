package org.kuxa.zzztart.service;

import org.kuxa.zzztart.entity.User;
import org.kuxa.zzztart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(long chatId, String username) {
        User newUser = new User();
        newUser.setChatId(chatId);
        newUser.setUsername(username);
        newUser.setRegistrationDate(LocalDateTime.now());
        userRepository.save(newUser);
    }

    public boolean isUserRegistered(long chatId) {
        return userRepository.existsById(chatId);
    }

}