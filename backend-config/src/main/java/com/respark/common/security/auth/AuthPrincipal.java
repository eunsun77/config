package com.respark.common.security.auth;

import java.util.Collection;
import java.util.List;

/**
 * JWT claim snapshot used across services. Implemented details stay in backend-user.
 */
public record AuthPrincipal(
        String memberUuid,
        String email,
        String nickname,
        Collection<String> roles
) {
    public AuthPrincipal(String memberUuid, String email, String nickname) {
        this(memberUuid, email, nickname, List.of("MEMBER"));
    }

    public boolean isAdmin() {
        return roles != null && roles.stream().anyMatch(r ->
                "ADMIN".equals(r) || "SUPER_ADMIN".equals(r) || "ROLE_ADMIN".equals(r) || "ROLE_SUPER_ADMIN".equals(r)
        );
    }
}
