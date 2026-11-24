package com.respark.common.config;

import com.respark.common.exception.BusinessException;
import com.respark.common.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CookieUtil {

    public static final String ACCESS_TOKEN_NAME = "accessToken";
    public static final String REFRESH_TOKEN_NAME = "refreshToken";

    private final ResparkSecurityProperties properties;

    public void addAccessTokenCookie(HttpServletResponse response, String token) {
        response.addHeader("Set-Cookie", buildCookie(
                ACCESS_TOKEN_NAME,
                token,
                Duration.ofMinutes(properties.getAccessTokenMaxAgeMinutes())
        ).toString());
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String token) {
        response.addHeader("Set-Cookie", buildCookie(
                REFRESH_TOKEN_NAME,
                token,
                Duration.ofDays(properties.getRefreshTokenMaxAgeDays())
        ).toString());
    }

    public void addAuthCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        addAccessTokenCookie(response, accessToken);
        addRefreshTokenCookie(response, refreshToken);
    }

    public String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new BusinessException(ErrorCode.MISSING_COOKIE);
        }
        return Arrays.stream(cookies)
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.MISSING_COOKIE));
    }

    public void clearCookie(HttpServletResponse response, String name) {
        response.addHeader("Set-Cookie", buildCookie(name, "", Duration.ZERO).toString());
    }

    public void clearAuthCookies(HttpServletResponse response) {
        clearCookie(response, ACCESS_TOKEN_NAME);
        clearCookie(response, REFRESH_TOKEN_NAME);
    }

    private ResponseCookie buildCookie(String name, String value, Duration maxAge) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(properties.isCookieSecure())
                .path("/")
                .sameSite(properties.getCookieSameSite())
                .maxAge(maxAge);

        String domain = properties.getCookieDomain();
        if (domain != null && !domain.isBlank()) {
            builder.domain(domain);
        }
        return builder.build();
    }
}
