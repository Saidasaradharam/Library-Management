package com.library.librarymanagement.config;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.entity.UserRole;
import com.library.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creates a default LIBRARIAN user on application startup if one does not exist.
     */
    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            final String adminUsername = "librarian@library.com";

            // Check if the admin user already exists
            if (userRepository.findByUsername(adminUsername).isEmpty()) {

                User admin = new User();
                admin.setUsername(adminUsername);

                // IMPORTANT: Use a secure, known password for testing
                admin.setPassword(passwordEncoder.encode("AdminPass123"));

                // Assign the required LIBRARIAN role
                admin.setRole(UserRole.LIBRARIAN);

                // Set the audit field manually for the bootstrap user
                admin.setCreatedBy("SYSTEM_BOOTSTRAP");

                userRepository.save(admin);
                System.out.println("--- LIBRARIAN User Created: " + adminUsername + " ---");
            }
        };
    }
}