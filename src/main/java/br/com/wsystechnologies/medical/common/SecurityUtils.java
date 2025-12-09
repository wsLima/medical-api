package br.com.wsystechnologies.medical.common;

import br.com.wsystechnologies.medical.infrastructure.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                authentication.getPrincipal() == null ||
                !(authentication.getPrincipal() instanceof UserPrincipal)) {

            throw new IllegalStateException("No authenticated user found or invalid principal type.");
        }

        return (UserPrincipal) authentication.getPrincipal();
    }

    public static UUID getCurrentAccountId() {
        return getCurrentUser().getAccountId();
    }

    public static UUID getCurrentClinicId() {
        return getCurrentUser().getClinicId();
    }

    public static String getCurrentEmail() {
        return getCurrentUser().getEmail();
    }

    public static UUID getCurrentProfileId() {
        return getCurrentUser().getProfileId();
    }
}
