package com.respark.common.config;

import java.util.List;

/**
 * Shared path patterns for Gateway and backend-user.
 * Align with API 정의서 when routes are finalized.
 */
public final class SecurityPathConstants {

    private SecurityPathConstants() {
    }

    /** Auth & static — always public */
    public static final List<String> AUTH_PUBLIC_PATHS = List.of(
            "/api/v1/auth/signup/**",
            "/api/v1/auth/signin/**",
            "/api/v1/auth/social/**",
            "/api/v1/auth/email/**",
            "/api/v1/auth/password/**",
            "/api/v1/auth/refresh",
            "/actuator/health",
            "/error",
            "/favicon.ico"
    );

    /** Read-only catalog / terms — GET only in SecurityFilterChain */
    public static final List<String> PUBLIC_GET_PATHS = List.of(
            "/api/v1/terms/**",
            "/api/v1/products/**",
            "/api/v1/categories/**",
            "/api/v1/main/**"
    );

    public static final String ADMIN_API_PREFIX = "/api/v1/admin/**";
}
