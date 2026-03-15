package com.pooling.ledger.dto.auth;

public class AuthResponse {

    private String token;
    private String email;
    private String name;
    private String flatNumber;
    private Long userId;

    public AuthResponse() {}

    public AuthResponse(String token, String email, String name, String flatNumber, Long userId) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.flatNumber = flatNumber;
        this.userId = userId;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFlatNumber() { return flatNumber; }
    public void setFlatNumber(String flatNumber) { this.flatNumber = flatNumber; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}