package com.pooling.ledger.service.auth;

import com.pooling.ledger.dto.auth.AuthResponse;
import com.pooling.ledger.dto.auth.LoginRequest;
import com.pooling.ledger.dto.auth.RegisterRequest;
import com.pooling.ledger.entity.Resident;
import com.pooling.ledger.entity.auth.AppUser;
import com.pooling.ledger.repository.ResidentRepository;
import com.pooling.ledger.repository.auth.AppUserRepository;
import com.pooling.ledger.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Resident resident = new Resident();
        resident.setName(request.getName());
        resident.setEmail(request.getEmail());
        resident.setFlatNumber(request.getFlatNumber());
        resident.setBalance(0.0);
        Resident savedResident = residentRepository.save(resident);

        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setFlatNumber(request.getFlatNumber());
        appUserRepository.save(user);

        String token = jwtUtil.generateToken(request.getEmail());

        return new AuthResponse(token, request.getEmail(), request.getName(), request.getFlatNumber(), savedResident.getId());
    }

    public AuthResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        Resident resident = residentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Resident not found"));

        String token = jwtUtil.generateToken(request.getEmail());

        return new AuthResponse(token, user.getEmail(), user.getName(), user.getFlatNumber(), resident.getId());
    }
}