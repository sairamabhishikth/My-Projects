package com.stockmarket.service;

import com.stockmarket.model.User;
import com.stockmarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create User
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Read All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Read User by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Update User
    public User updateUser(Long userId, User updatedUser) {
        User existingUser = getUserById(userId);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setNetWorth(updatedUser.getNetWorth());
        return userRepository.save(existingUser);
    }

    // Delete User
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
