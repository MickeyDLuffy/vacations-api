package com.github.mickeydluffy.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
