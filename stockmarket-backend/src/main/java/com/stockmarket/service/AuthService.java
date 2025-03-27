package com.stockmarket.service;

import com.stockmarket.model.Auth;
import com.stockmarket.model.User;
import com.stockmarket.repository.AuthRepository;
import com.stockmarket.repository.UserRepository;
import com.stockmarket.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(String username, String password, double netWorth) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(username);
        user.setNetWorth(netWorth);
        userRepository.save(user);

        Auth auth = new Auth();
        auth.setUser(user);
        auth.setPasswordHash(passwordEncoder.encode(password));
        authRepository.save(auth);

        return jwtUtil.generateToken(username);
    }

    public String loginUser(String username, String password) {
        Optional<Auth> authOptional = authRepository.findByUser_Username(username);
        if (authOptional.isEmpty() || !passwordEncoder.matches(password, authOptional.get().getPasswordHash())) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtUtil.generateToken(username);
    }
}
