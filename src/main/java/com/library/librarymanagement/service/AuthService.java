package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.AuthRequest;
import com.library.librarymanagement.security.JwtUtil;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public record TokenResponse(String accessToken, String refreshToken) {}

    public User registerNewUser(AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = new User();
        user.setUsername(request.getUsername());

        // Hashing the password using BCrypt
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    @Transactional
    public TokenResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate both tokens
        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponse refreshToken(String oldRefreshToken) {

        String username;
        try {
            username = Jwts.parserBuilder()
                    .setSigningKey(jwtUtil.getSigningKey()) // Assume you add getSigningKey() to JwtUtil
                    .build()
                    .parseClaimsJws(oldRefreshToken)
                    .getBody()
                    .getSubject();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            username = e.getClaims().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token signature or format.");
        }

        // Finding user and validating the stored token
        User user = userRepository.findByUsername(username)
                .filter(u -> oldRefreshToken.equals(u.getRefreshToken()))
                .orElseThrow(() -> new RuntimeException("Invalid refresh token or session expired."));
        // Generate new tokens
        String newAccessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole().name());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // Invalidating the old token and save the new one
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}