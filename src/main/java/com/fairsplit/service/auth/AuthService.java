package com.fairsplit.service.auth;

import com.fairsplit.dto.auth.AuthResponse;
import com.fairsplit.dto.auth.LoginRequest;
import com.fairsplit.dto.auth.RegisterRequest;
import com.fairsplit.entity.auth.AppUser;
import com.fairsplit.repository.auth.AppUserRepository;
import com.fairsplit.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        AppUser saved = appUserRepository.save(user);

        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token, saved.getEmail(), saved.getName(), saved.getId());
    }

    public AuthResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token, user.getEmail(), user.getName(), user.getId());
    }
}