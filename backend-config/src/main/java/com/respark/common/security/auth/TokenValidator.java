package com.respark.common.security.auth;

/**
 * Implemented by backend-user (or gateway) with the shared JWT secret / JWKS.
 */
public interface TokenValidator {

    AuthPrincipal validate(String accessToken);
}
