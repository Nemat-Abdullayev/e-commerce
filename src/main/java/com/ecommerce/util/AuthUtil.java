package com.ecommerce.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class AuthUtil {

    public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(auth) ? auth.getName() : null;
    }
}
