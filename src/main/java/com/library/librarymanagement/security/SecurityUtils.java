package com.library.librarymanagement.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**
     * Retrieves the username of the currently authenticated user (from the JWT).
     * @return The username (e.g., "librarian@library.com") or a system fallback.
     */
    public static String getCurrentUserLogin() {
        // 1. Get the current Authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. Check for authenticated status (ensures a token was successfully processed)
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {

            // The principal name is the username from the JWT
            return authentication.getName();
        }
        // Fallback for security events or public endpoints
        return "SYSTEM_ANONYMOUS";
    }
}