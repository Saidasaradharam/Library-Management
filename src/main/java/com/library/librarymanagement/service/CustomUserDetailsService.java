package com.library.librarymanagement.service;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Locates the user based on the username.
     * This is required by Spring Security for authentication.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Find the User entity in your PostgreSQL database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. Return Spring Security's UserDetails object
        // The password is the HASHED password stored in the DB.
        // Authorities/Roles are set to an empty list here, as we derive roles from JWT in the filter.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // Hashed password
                Collections.emptyList()
        );
    }
}